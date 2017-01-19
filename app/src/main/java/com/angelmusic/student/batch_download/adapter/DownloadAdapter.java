package com.angelmusic.student.batch_download.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.R;
import com.angelmusic.student.base.App;
import com.angelmusic.student.batch_download.db.DAOImpl;
import com.angelmusic.student.batch_download.infobean.FileInfo;
import com.angelmusic.student.customview.CustomCircleProgress;
import com.angelmusic.student.utils.FileUtil;
import com.angelmusic.student.utils.LogUtil;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.ProgressCallback;

import java.util.List;

import static com.angelmusic.student.R.id.circleProgress;

/**
 * Created by fei on 2017/1/11.
 * 注意:传入的courseDataList的第一条数据需要设置为null
 */

public class DownloadAdapter extends BaseAdapter {
    private Context mContext;
    private List<List<FileInfo>> courseDataList;
    private LayoutInflater mInflater;
    private ListView lvCourse;

    public DownloadAdapter(Context mContext, List<List<FileInfo>> courseDataList, ListView lvCourse) {
        this.mContext = mContext;
        this.courseDataList = courseDataList;
        this.lvCourse = lvCourse;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return courseDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final FirstViewHolder firstHolder;
        if (position == 0) {
            if (convertView == null) {
                firstHolder = new FirstViewHolder();
                convertView = mInflater.inflate(R.layout.dload_first_item, null);
                firstHolder.btnDloadAll = (Button) convertView.findViewById(R.id.btn_dload_all);
                convertView.setTag(firstHolder);
            } else {
                firstHolder = (FirstViewHolder) convertView.getTag();
            }
            /*初始进入状态的显示*/
            boolean allDloadOk = isAllDloadOk(courseDataList);//获取是否全部下载完了
            if (isLoading(courseDataList)) {
                firstHolder.btnDloadAll.setText("全部暂停");
                firstHolder.btnDloadAll.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.down_all_btn_clicked, 0, 0, 0);
            } else {
                if (allDloadOk) {
                    firstHolder.btnDloadAll.setText("全部删除");
                    firstHolder.btnDloadAll.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.delete_icon, 0, 0, 0);
                } else {
                    firstHolder.btnDloadAll.setText("全部下载");
                    firstHolder.btnDloadAll.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.down_all_btn_normal, 0, 0, 0);
                }
            }

            firstHolder.btnDloadAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence text = firstHolder.btnDloadAll.getText();
                    if (text.equals("全部下载")) {
                        firstHolder.btnDloadAll.setText("全部暂停");
                        firstHolder.btnDloadAll.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.down_all_btn_clicked, 0, 0, 0);
                        for (int i = 1; i < courseDataList.size(); i++) {
                            startDownload(courseDataList.get(i));
                        }
                    } else if (text.equals("全部删除")) {
                        firstHolder.btnDloadAll.setText("全部下载");
                        firstHolder.btnDloadAll.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.down_all_btn_normal, 0, 0, 0);
                        for (int i = 1; i < courseDataList.size(); i++) {
                            deleteFiles(courseDataList.get(i));
                        }
                    } else if (text.equals("全部暂停")) {
                        firstHolder.btnDloadAll.setText("全部下载");
                        firstHolder.btnDloadAll.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.down_all_btn_normal, 0, 0, 0);
                        for (List<FileInfo> fileInfoList : courseDataList) {
                            pauseDownload(fileInfoList);
                        }
                    }
                }
            });
        } else {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.dload_lv_item_layout, null);
                holder.tvCourseName = (TextView) convertView.findViewById(R.id.tv_courseName);
                holder.circleProgress = (CustomCircleProgress) convertView.findViewById(circleProgress);
                holder.tvProgress = (TextView) convertView.findViewById(R.id.tv_progress);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //设置显示第几课
            holder.tvCourseName.setText(courseDataList.get(position).get(position).getCourseName());
            //设置初始进入状态课程下载的百分比
            int progress = getItemProgress(courseDataList.get(position));
            //设置显示的百分比
            holder.tvProgress.setText(progress + "%");
            //设置按钮的显示样式
            if (progress == 0 && !DAOImpl.getInstance(mContext).isCourseNameExist(courseDataList.get(position).get(position).getCourseName())) {
                holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);
            } else if (progress == 100) {
                holder.circleProgress.setStatus(CustomCircleProgress.Status.End);
            } else {
                if (!(holder.circleProgress.getStatus() == CustomCircleProgress.Status.Loading)) {
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);
                }
                holder.circleProgress.setProgress(progress);
            }
            //设置点击监听事件
            holder.circleProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Start) {//初始下载状态
                        holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);//点击则变成下载状态
                        //下载
                        startDownload(courseDataList.get(position));
                    } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Loading) {//下载状态
                        holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);//点击则变成暂停状态
                        //暂停
                        pauseDownload(courseDataList.get(position));
                    } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {//下载暂停状态
                        holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);//点击则变成下载状态
                        //继续下载，同下载
                        startDownload(courseDataList.get(position));
                    } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.End) {//下载全部完成状态
                        holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);//点击变成初始下载状态
                        //删除
                        deleteFiles(courseDataList.get(position));
                    }
                }
            });
        }
        return convertView;
    }

    // 第一条Item数据封装
    static class FirstViewHolder {
        private Button btnDloadAll;
    }

    // 其他Item数据封装
    static class ViewHolder {
        private TextView tvCourseName, tvProgress;
        private CustomCircleProgress circleProgress;
    }

    /**
     * 查询是否有正在下载的课程
     */
    private boolean isLoading(List<List<FileInfo>> courseDataList) {
        boolean isLoading = false;
        for (int i = 1; i < courseDataList.size(); i++) {

        }
        return isLoading;
    }

    /**
     * 是否全部下载完了
     * 注意fileInfoLists的第一条数据是null的
     */
    private boolean isAllDloadOk(List<List<FileInfo>> courseDataList) {
        boolean isAllOk = true;
        for (int i = 1; i < courseDataList.size(); i++) {
            int progress = getItemProgress(courseDataList.get(i));
            if (progress != 100) {//只要有一节课没下完就说明没有全部下载完
                isAllOk = false;
            }
        }
        return isAllOk;
    }

    /**
     * 计算初始进入时每节课下载的百分比
     */
    private int getItemProgress(List<FileInfo> fileInfoList) {
        int downloadNum = 0;//当前课程已经下载的文件数量
        Log.e("---fileInfoList---", fileInfoList.size() + "");
        for (FileInfo fileInfo : fileInfoList) {
            LogUtil.e("===", fileInfo.getFileName() + "--" + fileInfo.getCourseName());
            boolean isDownloadOk = DAOImpl.getInstance(mContext).isDownloadOk(fileInfo.getFileName(), fileInfo.getCourseName());
            Log.e("=isDownloadOk=", isDownloadOk + "");
            if (isDownloadOk) {
                ++downloadNum;
            }
        }
        //计算下载进度百分比
        return (int) (((float) downloadNum / (float) fileInfoList.size()) * 100);
    }

    /**
     * 下载
     */
    private void startDownload(List<FileInfo> fileInfoList) {
        for (FileInfo fileInfo : fileInfoList) {
            if (DAOImpl.getInstance(mContext).isFileNameExist(fileInfo.getFileName())) {//查询数据库是否有当前文件名的记录
                if (DAOImpl.getInstance(mContext).isDownloadOk(fileInfo.getFileName())) {//查询当前文件名的下载状态
                    if (!DAOImpl.getInstance(mContext).isCourseNameExist(fileInfo.getFileName(), fileInfo.getCourseName())) {//查询当前文件名下是否有当前课程名
                        DAOImpl.getInstance(mContext).insertFile(fileInfo.getFileName(), fileInfo.getCourseName(), 1);
                    }
                } else {
                    if (DAOImpl.getInstance(mContext).isCourseNameExist(fileInfo.getFileName(), fileInfo.getCourseName())) {
                        //执行下载
                        downloadFile(fileInfo);
                    } else {
                        DAOImpl.getInstance(mContext).insertFile(fileInfo.getFileName(), fileInfo.getCourseName(), 0);
                    }
                }

            } else {
                DAOImpl.getInstance(mContext).insertFile(fileInfo.getFileName(), fileInfo.getCourseName(), 0);
                //执行下载
                downloadFile(fileInfo);
            }
        }
    }

    /**
     * 暂停
     */
    private void pauseDownload(List<FileInfo> fileInfoList) {
        for (FileInfo fileInfo : fileInfoList) {
            OkHttpUtil.getDefault().cancelRequest(fileInfo.getFileName());
        }
    }

    /**
     * 删除
     */
    private void deleteFiles(List<FileInfo> fileInfoList) {
        for (FileInfo fileInfo : fileInfoList) {
            boolean canDeleteLocalFile = DAOImpl.getInstance(mContext).isCanDeleteLocalFile(fileInfo.getFileName());
            if (canDeleteLocalFile) {
                FileUtil.deleteFile(fileInfo.getFileParentPath() + fileInfo.getFileName());//删除本地文件
            }
            DAOImpl.getInstance(mContext).deleteFile(fileInfo.getFileName(), fileInfo.getCourseName());//删除数据库中的信息
        }
        //刷新界面
        refreshProgress();
    }

    /**
     * 文件下载
     */
    private void downloadFile(final FileInfo fileInfo) {
        final String fileParentPath = fileInfo.getFileParentPath();//文件的存储路径
        final String fileName = fileInfo.getFileName();//文件名，带后缀
        final String fileNameCutSuffix = fileName.substring(0, fileName.lastIndexOf("."));//文件名，不带后缀（因为使用的网络框架下载完文件后会自动添加后缀名）
        App.init.setDownloadFileDir(fileParentPath);//设置文件的下载路径
        final HttpInfo info = HttpInfo.Builder()
                .addDownloadFile(fileInfo.getFileUrl(), fileNameCutSuffix, new ProgressCallback() {
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                        if (done) {
                            Log.e("---------------", "---------------100---------------");
                            //更新所有文件名为fileName的条目的下载状态
                            DAOImpl.getInstance(mContext).updateDownloadState(fileName, 1);
                            //刷新界面
                            refreshProgress();
                        }
                    }

                    @Override
                    public void onResponseMain(String filePath, HttpInfo info) {
                        LogUtil.e("====" + fileName, "下载结果：" + info.getRetDetail());
                    }
                })
                .build();
        OkHttpUtil.Builder()
                .setReadTimeout(120)
                .build(fileName)//绑定请求标识
                .doDownloadFileAsync(info);
    }

    //可以随时更改数据后更新适配器
    public void refreshProgress() {
        notifyDataSetInvalidated();
    }
}
