package com.angelmusic.student.batch_download.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.R;
import com.angelmusic.student.base.App;
import com.angelmusic.student.batch_download.db.DAO2Impl;
import com.angelmusic.student.batch_download.db.DAOImpl;
import com.angelmusic.student.batch_download.infobean.FileInfo;
import com.angelmusic.student.customview.CustomCircleProgress;
import com.angelmusic.student.utils.FileUtil;
import com.angelmusic.student.utils.LogUtil;
import com.angelmusic.stu.okhttp.HttpInfo;
import com.angelmusic.stu.okhttp.OkHttpUtil;
import com.angelmusic.stu.okhttp.callback.ProgressCallback;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.angelmusic.student.R.id.circleProgress;

/**
 * Created by fei on 2017/1/11.
 * 注意:传入的courseDataList的第一条数据需要设置为null,用于显示全部下载的item
 */

public class DownloadAdapter extends BaseAdapter {
    private Context mContext;
    private List<List<FileInfo>> courseDataList;
    private LayoutInflater mInflater;

    public DownloadAdapter(Context mContext) {
        this.mContext = mContext;
        courseDataList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    //可以先添加适配器然后在添加数据
    public void setData(List<List<FileInfo>> courseDataList) {
        this.courseDataList = courseDataList;
        notifyDataSetChanged();
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
            boolean allDloadOk = isAllDLoadOk(courseDataList);//获取是否全部下载完了
            if (allDloadOk) {
                firstHolder.btnDloadAll.setClickable(true);
                firstHolder.btnDloadAll.setTag("clickable");
                firstHolder.btnDloadAll.setText("全部删除");
                firstHolder.btnDloadAll.setBackgroundResource(R.drawable.delete_all_btn_selector);
            } else {
                boolean isHasPause = false;//是否有没下载完的课程且处于暂停状态
                boolean isNotExist = false;//是否有没存储在数据库中的课程信息,如果有则说明有没下载的课
                for (int i = 1; i < courseDataList.size(); i++) {
                    int progress = getItemProgress(courseDataList.get(i));//查询某节课的下载进度
                    if (progress != 100) {
                        if (DAO2Impl.getInstance(mContext).queryIsExist(courseDataList.get(i).get(1).getCourseName())) {//查询某节课是否存在数据库中
                            if (!DAO2Impl.getInstance(mContext).queryIsLoading(courseDataList.get(i).get(1).getCourseName())) {
                                isHasPause = true;
                            }
                        } else {
                            isNotExist = true;//有没下载的课
                        }
                    }
                }
                if (isNotExist || isHasPause) {
                    firstHolder.btnDloadAll.setClickable(true);
                    firstHolder.btnDloadAll.setTag("clickable");
                    firstHolder.btnDloadAll.setText("全部下载");
                    firstHolder.btnDloadAll.setBackgroundResource(R.drawable.download_all_btn_selector);
                } else {
                    firstHolder.btnDloadAll.setClickable(false);
                    firstHolder.btnDloadAll.setTag("unclickable");
                    firstHolder.btnDloadAll.setText("全部下载");
                    firstHolder.btnDloadAll.setBackgroundResource(R.drawable.download_all_btn_bg_gray);
                }
            }
            firstHolder.btnDloadAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshProgress();
                    CharSequence text = firstHolder.btnDloadAll.getText();
                    String tag = (String) firstHolder.btnDloadAll.getTag();
                    if (text.equals("全部下载") && "clickable".equals(tag)) {
                        for (int i = 1; i < courseDataList.size(); i++) {
                            startDownload(courseDataList.get(i));
                        }
                    } else if (text.equals("全部删除")) {
                        firstHolder.btnDloadAll.setText("全部下载");
                        for (int i = 1; i < courseDataList.size(); i++) {
                            deleteFiles(courseDataList.get(i));
                        }
                    }
                }
            });
        } else {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.dload_lv_item_layout, null);
                holder.tvCourseName = (TextView) convertView.findViewById(R.id.tv_courseName);
                holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_progress);
                holder.circleProgress = (CustomCircleProgress) convertView.findViewById(circleProgress);
                holder.tvProgress = (TextView) convertView.findViewById(R.id.tv_progress);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            /*初始进入状态的显示*/
            //设置显示第几课
            final String courseName = courseDataList.get(position).get(position).getCourseName();
            holder.tvCourseName.setText(courseName);
            //设置初始进入状态课程下载的百分比
            int progress = getItemProgress(courseDataList.get(position));
            //设置显示的百分比
            holder.tvProgress.setTextColor(Color.parseColor("#8ab609"));
            holder.tvProgress.setVisibility(View.VISIBLE);
            holder.linearLayout.setPadding(0, 10, 80, 10);
            holder.tvProgress.setText(progress + "%");

            //设置按钮的显示样式
            if (progress == 0 && !DAOImpl.getInstance(mContext).isCourseNameExist(courseDataList.get(position).get(position).getCourseName())) {
                holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);
                holder.tvProgress.setVisibility(View.GONE);
                holder.linearLayout.setPadding(0, 10, 130, 10);
            } else if (progress == 100) {
                holder.circleProgress.setStatus(CustomCircleProgress.Status.End);
                holder.tvProgress.setTextColor(Color.parseColor("#888888"));
            } else if (progress > 0 && progress < 100) {
                if (DAO2Impl.getInstance(mContext).queryIsExist(courseName)) {
                    if (DAO2Impl.getInstance(mContext).queryIsLoading(courseName)) {
                        holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);
                    } else {
                        holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);
                    }
                    holder.circleProgress.setProgress(progress);
                }
            }

            //设置点击监听事件
            holder.circleProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshProgress();
                    if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Start) {//初始下载状态
                        holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);//点击则变成下载状态
                        //下载
                        startDownload(courseDataList.get(position));
                    } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Loading) {//下载状态
                        holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);//点击则变成暂停状态
                        //暂停
                        pauseDownload(courseDataList.get(position));
                    } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {//暂停状态
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
        private LinearLayout linearLayout;
        private TextView tvCourseName, tvProgress;
        private CustomCircleProgress circleProgress;
    }

    /**
     * 是否全部下载完了
     * 注意fileInfoLists的第一条数据是null的
     */
    private boolean isAllDLoadOk(List<List<FileInfo>> courseDataList) {
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
        refreshProgress();
    }

    /**
     * 删除
     *
     * @param fileInfoList 一节课的所有文件信息
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
        //刷新界面
        refreshProgress();
    }

    /**
     * 单个文件的下载
     */
    private void downloadFile(final FileInfo fileInfo) {
        refreshProgress();
        final String fileParentPath = fileInfo.getFileParentPath();//文件的存储路径
        final String fileName = fileInfo.getFileName();//文件名，带后缀
        final String fileNameCutSuffix = fileName.substring(0, fileName.lastIndexOf("."));//文件名，不带后缀（因为使用的网络框架下载完文件后会自动添加后缀名）
        App.init.setDownloadFileDir(fileParentPath);//设置文件的下载路径
        Log.e("=======url======", fileInfo.getFileUrl());
        final HttpInfo info = HttpInfo.Builder()
                .addDownloadFile(fileInfo.getFileUrl(), fileNameCutSuffix, new ProgressCallback() {
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                        Log.e("----percent----", "=" + percent);
                        if (done) {
                            //更新所有文件名为fileName的条目的下载状态
                            DAOImpl.getInstance(mContext).updateDownloadState(fileName, "1");
                            //刷新界面
                            refreshProgress();
                        }
                    }

                    @Override
                    public void onResponseMain(String fileUrl, HttpInfo info) {
                        LogUtil.e("==getRetDetail==" + fileName, "下载结果：" + info.getRetDetail());
                        String result = info.getRetDetail();
                        if ("网络地址错误".equals(result)) {
                            Toast.makeText(mContext, "请求地址错误:" + fileUrl, Toast.LENGTH_SHORT).show();
                        } else if ("网络中断".equals(result)) {
                            Toast.makeText(mContext, "网络中断", Toast.LENGTH_SHORT).show();
                        } else if ("服务器内部错误".equals(result)) {
                            Toast.makeText(mContext, "服务器内部错误", Toast.LENGTH_SHORT).show();
                        } else if ("请检查网络连接是否正常".equals(result)) {
                            Toast.makeText(mContext, "请检查网络连接是否正常", Toast.LENGTH_SHORT).show();
                        }
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
