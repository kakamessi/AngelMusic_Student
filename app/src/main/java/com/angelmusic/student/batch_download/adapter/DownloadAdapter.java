package com.angelmusic.student.batch_download.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
 */

public class DownloadAdapter extends BaseAdapter {
    private Context mContext;
    private List<List<FileInfo>> fileInfoList;

    public DownloadAdapter(Context mContext, List<List<FileInfo>> fileInfoList) {
        this.mContext = mContext;
        this.fileInfoList = fileInfoList;
    }

    @Override
    public int getCount() {
        return fileInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dload_lv_item_layout, null);
            holder.tvCourseName = (TextView) convertView.findViewById(R.id.tv_courseName);
            holder.circleProgress = (CustomCircleProgress) convertView.findViewById(circleProgress);
            holder.tvProgress = (TextView) convertView.findViewById(R.id.tv_progress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //设置显示第几课
        holder.tvCourseName.setText(fileInfoList.get(position).get(position).getCourseName());

        //设置初始进入状态课程下载的百分比
        int downloadNum = 0;//当前课程已经下载的文件数量
        for (FileInfo fileInfo : fileInfoList.get(position)) {
            boolean isDownload = DAOImpl.getInstance(mContext).queryDownloadState(fileInfo.getFileName());//查询当前文件是否下载完
            if (isDownload) {
                ++downloadNum;
            }
        }
        //计算下载进度百分比
        int progress = (int) (((float) downloadNum / (float) fileInfoList.get(position).size()) * 100);
        //设置显示的百分比
        holder.tvProgress.setText(progress + "%");
        //设置按钮的显示样式
        if (progress == 0) {
            holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);
        } else if (progress == 100) {
            holder.circleProgress.setStatus(CustomCircleProgress.Status.End);
        } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Loading) {
            holder.circleProgress.setProgress(progress);
        } else {
            holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);
            holder.circleProgress.setProgress(progress);
        }

        //设置点击监听事件
        holder.circleProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Start) {//初始下载状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);//点击则变成下载状态
                    //下载，查询数据库当前文件是否下载完毕，
                    for (FileInfo fileInfo : fileInfoList.get(position)) {
                        if (!DAOImpl.getInstance(mContext).queryDownloadState(fileInfo.getFileName())) {
                            //向数据库插入一条下载信息(file_name为主键，若数据库已经存在则不插入（失败）)
                            DAOImpl.getInstance(mContext).insertFile(fileInfo);
                            //下载此文件
                            downloadFiles(fileInfo);
                        } else {
                            //获取数据库中当前文件名的quoteCount值
                            final int quoteCount = DAOImpl.getInstance(mContext).queryQuoteCount(fileInfo.getFileName());
                            //更新数据库中当前文件信息的quote_count字段(+1)
                            DAOImpl.getInstance(mContext).updateQuoteCount(fileInfo.getFileName(), quoteCount + 1);
                        }
                    }
                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Loading) {//下载状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);//点击则变成暂停状态
                    //文件若没下载完则执行暂停操作
                    for (FileInfo fileInfo : fileInfoList.get(position)) {
                        if (!DAOImpl.getInstance(mContext).queryDownloadState(fileInfo.getFileName())) {
                            OkHttpUtil.getDefault().cancelRequest(fileInfo.getFileName());
                        }
                    }
                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {//下载暂停状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);//点击则变成下载状态
                    //继续下载，同下载
                    for (FileInfo fileInfo : fileInfoList.get(position)) {
                        if (!DAOImpl.getInstance(mContext).queryDownloadState(fileInfo.getFileName())) {
                            //向数据库插入一条下载信息(file_name为主键，若数据库已经存在则不插入（失败）)
                            DAOImpl.getInstance(mContext).insertFile(fileInfo);
                            //下载此文件
                            downloadFiles(fileInfo);
                        } else {
                            //获取数据库中当前文件名的quoteCount值
                            final int quoteCount = DAOImpl.getInstance(mContext).queryQuoteCount(fileInfo.getFileName());
                            //更新数据库中当前文件信息的quote_count字段(+1)
                            DAOImpl.getInstance(mContext).updateQuoteCount(fileInfo.getFileName(), quoteCount + 1);
                        }
                    }
                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.End) {//下载全部完成状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);//点击变成初始下载状态
                    //删除操作
                    //查询文件是否被多个课程使用，若是则把quoteCount数减1，否则直接删除本地文件
                    for (FileInfo fileInfo : fileInfoList.get(position)) {
                        int quoteCount = DAOImpl.getInstance(mContext).queryQuoteCount(fileInfo.getFileName());
                        if (quoteCount == 1) {
                            //直接删除该文件并删除当前文件的数据库信息
                            boolean deleteFile = FileUtil.deleteFile(fileInfo.getFileParentPath() + fileInfo.getFileName());
                            if (deleteFile) {
                                Toast.makeText(mContext, fileInfo.getCourseName() + "删除成功", Toast.LENGTH_SHORT).show();
                                DAOImpl.getInstance(mContext).deleteFile(fileInfo.getFileName());
                                holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);
                                holder.tvProgress.setText(0 + "%");
                            }
                        } else {
                            //更新当前文件数据库quoteCount值
                            DAOImpl.getInstance(mContext).updateQuoteCount(fileInfo.getFileName(), --quoteCount);
                        }
                    }
                }
            }
        });
        return convertView;
    }

    // 数据封装
    static class ViewHolder {
        private TextView tvCourseName, tvProgress;
        private CustomCircleProgress circleProgress;
    }

    /**
     * 文件下载
     */
    private void downloadFiles(final FileInfo fileInfo) {
        final String fileParentPath = fileInfo.getFileParentPath();//文件的存储路径
        final String fileName = fileInfo.getFileName();//文件名，带后缀
        final String fileNameCutSuffix = fileName.substring(0, fileName.lastIndexOf("."));//文件名，不带后缀（因为使用的网络框架下载完文件后会自动添加后缀名）
        App.init.setDownloadFileDir(fileParentPath);//设置文件的下载路径
        final HttpInfo info = HttpInfo.Builder()
                .addDownloadFile(fileInfo.getFileUrl(), fileNameCutSuffix, new ProgressCallback() {
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                        if (done) {
                            LogUtil.e("====" + fileName, "下载进度：" + percent);
                            //每下载完一个文件更新一下界面显示的下载进度
                            //更新当前文件的下载状态
                            DAOImpl.getInstance(mContext).updateDownloadState(fileInfo.getFileName(), 1);
                            //查询数据库中当前文件的最大课程引用计数
                            int quoteCount = DAOImpl.getInstance(mContext).queryQuoteCount(fileName);
                            //更新当前文件的最大课程引用计数
                            String courseName = fileInfo.getCourseName();//课程名
                            //通知界面刷新

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
}
