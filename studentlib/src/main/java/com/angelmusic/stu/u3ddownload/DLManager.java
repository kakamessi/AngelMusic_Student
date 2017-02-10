package com.angelmusic.stu.u3ddownload;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.angelmusic.stu.MyApplication;
import com.angelmusic.stu.okhttp.HttpInfo;
import com.angelmusic.stu.okhttp.OkHttpUtil;
import com.angelmusic.stu.okhttp.callback.ProgressCallback;
import com.angelmusic.stu.u3ddownload.db.DAO2Impl;
import com.angelmusic.stu.u3ddownload.db.DAOImpl;
import com.angelmusic.stu.u3ddownload.infobean.FileInfo;
import com.angelmusic.stu.u3ddownload.infobean.GetDataInfo;
import com.angelmusic.stu.u3ddownload.infobean.SendDataInfo;
import com.angelmusic.stu.u3ddownload.utils.FileUtil;
import com.angelmusic.stu.u3ddownload.utils.GsonUtil;
import com.angelmusic.stu.u3ddownload.utils.SDCardUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fei on 2017/2/7.
 */

public class DLManager {
    private Context mContext;
    private static DLManager dlManager;
    private String courseParentPath;

    public DLManager(Context mContext) {
        this.mContext = mContext;
        courseParentPath = SDCardUtil.getAppFilePath(mContext) + "CourseFile" + File.separator;
    }

    public static DLManager getInstance(Context mContext) {
        if (dlManager == null) {
            dlManager = new DLManager(mContext);
        }
        return dlManager;
    }

    /**
     * 获取课程下载的百分比
     */
    public String getProgressJson(String json) {
        List<GetDataInfo> getDataInfoList = GsonUtil.jsonToList(json, GetDataInfo.class);//解析json
        List<SendDataInfo> sendDataInfoList = new ArrayList<>();
        for (GetDataInfo getDataInfo : getDataInfoList) {
            String courseName = getDataInfo.getCourse_name();
            int downloadNum = 0;//当前课程已经下载的文件数量
            List<String> urls = getDataInfo.getUrls();
            for (String url : urls) {
                String fileName = url.substring(1);//带后缀的文件名
                boolean downloadOk = DAOImpl.getInstance(mContext).isDownloadOk(fileName, courseName);
                if (downloadOk) {
                    downloadNum++;
                }
            }
            int progress = (int) (((float) downloadNum / (float) urls.size()) * 100);
            sendDataInfoList.add(new SendDataInfo(courseName, progress + ""));
        }
        String progressJson = GsonUtil.objectToJsonString(sendDataInfoList);
        return progressJson;
    }

    /**
     * 封装数据
     */
    private List<List<FileInfo>> packageData(List<GetDataInfo> getDataInfos) {
        List<List<FileInfo>> courseList = new ArrayList();//所有的课程
        for (GetDataInfo data : getDataInfos) {
            List<FileInfo> fileInfoList = new ArrayList<>();//某一节课
            String courseName = data.getCourse_name();
            List<String> urlList = data.getUrls();
            for (String url : urlList) {
                String fileName = url.substring(1);//带后缀的文件名
                String fileUrl = "http://192.168.1.179" + url;
                FileInfo fileInfo = new FileInfo(courseName, fileName, fileUrl, courseParentPath, 0, 0);
                fileInfoList.add(fileInfo);
            }
            courseList.add(fileInfoList);
        }
        return courseList;
    }

    /**
     * 是否全部下载完了
     * 注意fileInfoLists的第一条数据是null的
     */
    private boolean isAllDLoadOk(List<List<FileInfo>> courseDataList) {
        boolean isAllOk = true;
        for (int i = 1; i < courseDataList.size(); i++) {
            int progress = getCourseProgress(courseDataList.get(i));
            if (progress != 100) {//只要有一节课没下完就说明没有全部下载完
                isAllOk = false;
            }
        }
        return isAllOk;
    }

    /**
     * 计算初始进入时每节课下载的百分比
     */
    private int getCourseProgress(List<FileInfo> fileInfoList) {
        int downloadNum = 0;//当前课程已经下载的文件数量
        for (FileInfo fileInfo : fileInfoList) {
            boolean isDownloadOk = DAOImpl.getInstance(mContext).isDownloadOk(fileInfo.getFileName(), fileInfo.getCourseName());
            if (isDownloadOk) {
                ++downloadNum;
            }
        }
        //计算下载进度百分比
        return (int) (((float) downloadNum / (float) fileInfoList.size()) * 100);
    }

    /**
     * 下载
     *
     * @param fileInfoList 一节课的所有文件信息
     */
    private void startDownload(List<FileInfo> fileInfoList) {
        String courseName = fileInfoList.get(1).getCourseName();
        //查询本节课存不存在数据表tb_state中
        if (DAO2Impl.getInstance(mContext).queryIsExist(courseName)) {
            DAO2Impl.getInstance(mContext).updateLoadingState(courseName, "0");
        } else {
            DAO2Impl.getInstance(mContext).insertFile(courseName, "0");
        }
        for (FileInfo fileInfo : fileInfoList) {
            if (DAOImpl.getInstance(mContext).isFileNameExist(fileInfo.getFileName())) {//查询数据库是否有当前文件名的记录
                if (DAOImpl.getInstance(mContext).isDownloadOk(fileInfo.getFileName())) {//查询当前文件名的下载状态
                    if (!DAOImpl.getInstance(mContext).isCourseNameExist(fileInfo.getFileName(), fileInfo.getCourseName())) {//查询当前文件名下是否有当前课程名
                        DAOImpl.getInstance(mContext).insertFile(fileInfo.getFileName(), fileInfo.getCourseName(), "1");
                    }
                } else {
                    if (DAOImpl.getInstance(mContext).isCourseNameExist(fileInfo.getFileName(), fileInfo.getCourseName())) {
                        //执行下载
                        downloadFile(fileInfo);
                    } else {
                        DAOImpl.getInstance(mContext).insertFile(fileInfo.getFileName(), fileInfo.getCourseName(), "0");
                    }
                }
            } else {
                DAOImpl.getInstance(mContext).insertFile(fileInfo.getFileName(), fileInfo.getCourseName(), "0");
                //执行下载
                downloadFile(fileInfo);
            }
        }
    }

    /**
     * 暂停
     *
     * @param fileInfoList 一节课的所有文件信息
     */
    private void pauseDownload(List<FileInfo> fileInfoList) {
        //本节课的下载状态设置成暂停
        String courseName = fileInfoList.get(1).getCourseName();
        DAO2Impl.getInstance(mContext).updateLoadingState(courseName, "1");
        for (FileInfo fileInfo : fileInfoList) {
            OkHttpUtil.getDefault().cancelRequest(fileInfo.getFileName());
        }
    }

    /**
     * 删除
     *
     * @param fileInfoList 一节课的 文件信息
     */
    private void deleteFiles(List<FileInfo> fileInfoList) {
        //将本节课的下载状态删除
        String courseName = fileInfoList.get(1).getCourseName();
        DAO2Impl.getInstance(mContext).deleteFile(courseName);
        for (FileInfo fileInfo : fileInfoList) {
            boolean canDeleteLocalFile = DAOImpl.getInstance(mContext).isCanDeleteLocalFile(fileInfo.getFileName());
            if (canDeleteLocalFile) {
                FileUtil.deleteFile(fileInfo.getFileParentPath() + fileInfo.getFileName());//删除本地文件
            }
            DAOImpl.getInstance(mContext).deleteFile(fileInfo.getFileName(), fileInfo.getCourseName());//删除数据库中的信息
        }
    }

    /**
     * 单个文件的下载
     */
    private void downloadFile(final FileInfo fileInfo) {
        final String fileParentPath = fileInfo.getFileParentPath();//文件的存储路径
        final String fileName = fileInfo.getFileName();//文件名，带后缀
        final String fileNameCutSuffix = fileName.substring(0, fileName.lastIndexOf("."));//文件名，不带后缀（因为使用的网络框架下载完文件后会自动添加后缀名）
        MyApplication.init.setDownloadFileDir(fileParentPath);//设置文件的下载路径
        final HttpInfo info = HttpInfo.Builder()
                .addDownloadFile(fileInfo.getFileUrl(), fileNameCutSuffix, new ProgressCallback() {
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                        Log.e("----percent----", "=" + percent);
                        if (done) {
                            //更新所有文件名为fileName的条目的下载状态
                            DAOImpl.getInstance(mContext).updateDownloadState(fileName, "1");
                        }
                    }

                    @Override
                    public void onResponseMain(String filePath, HttpInfo info) {
                        Log.e("====" + fileName, "下载结果：" + info.getRetDetail());
                    }
                })
                .build();
        OkHttpUtil.Builder()
                .setReadTimeout(120)
                .build(fileName)//绑定请求标识
                .doDownloadFileAsync(info);
    }

}
