package com.angelmusic.student.course_download.adapter;

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

import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.callback.ProgressCallback;
import com.angelmusic.student.R;
import com.angelmusic.student.base.App;
import com.angelmusic.student.course_download.db.DAO2Impl;
import com.angelmusic.student.course_download.db.DAOImpl;
import com.angelmusic.student.course_download.infobean.CourseItemInfo;
import com.angelmusic.student.course_download.infobean.FileInfo;
import com.angelmusic.student.customview.CustomCircleProgress;
import com.angelmusic.student.utils.FileUtil;
import com.angelmusic.student.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.angelmusic.student.R.id.circleProgress;

/**
 * Created by fei on 2017/1/11.
 * 注意:传入的courseDataList的第一条数据需要设置为null,用于显示全部下载的item
 */

public class DownloadNewAdapter extends BaseAdapter {
    private Context mContext;
    private List<CourseItemInfo> courseDataList;
    private LayoutInflater mInflater;

    public DownloadNewAdapter(Context mContext) {
        this.mContext = mContext;
        courseDataList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    //可以先添加适配器然后在添加数据
    public void setData(List<CourseItemInfo> courseDataList) {
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

        final CourseItemInfo ci = courseDataList.get(position);

        final ViewHolder holder;
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

        holder.tvCourseName.setText("第一节课");
        holder.tvProgress.setText(ci.getProcess()*100 + "%");
        if(ci.getIsActive()==1){
            holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);//点击则变成下载状态
        }else if(ci.getIsActive()==2){
            holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);//点击则变成下载状态
        }else if(ci.getIsActive()==3){
            holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);//点击则变成下载状态
        }else if(ci.getIsActive()==4){
            holder.circleProgress.setStatus(CustomCircleProgress.Status.End);//点击则变成下载状态
        }


        //设置点击监听事件
        holder.circleProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Start) {//初始下载状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);//点击则变成下载状态
                    //下载
                    ci.setIsActive(2);

                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Loading) {//下载状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);//点击则变成暂停状态
                    //暂停
                    ci.setIsActive(3);

                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {//暂停状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);//点击则变成下载状态
                    //继续下载，同下载
                    ci.setIsActive(2);

                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.End) {//下载全部完成状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);//点击变成初始下载状态
                    //删除
                    ci.setIsActive(1);

                }
            }
        });

        return convertView;
    }


    // 其他Item数据封装
    static class ViewHolder {
        private LinearLayout linearLayout;
        private TextView tvCourseName, tvProgress;
        private CustomCircleProgress circleProgress;
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
        final HttpInfo info = HttpInfo.Builder()
                .addDownloadFile(fileInfo.getFileUrl(), fileNameCutSuffix, new ProgressCallback() {
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
//                        LogUtil.e("----percent----", "=" + percent);
                        if (done) {
                            //更新所有文件名为fileName的条目的下载状态
                            DAOImpl.getInstance(mContext).updateDownloadState(fileName, "1");
                            //刷新界面
                            refreshProgress();
                        }
                    }

                    @Override
                    public void onResponseMain(String fileUrl, HttpInfo info) {
//                        LogUtil.e("==getRetDetail==" + fileName, "下载结果：" + info.getRetDetail());
                        String result = info.getRetDetail();
                        if ("网络地址错误".equals(result)) {
                            Toast.makeText(mContext, "请求地址错误:" + fileUrl, Toast.LENGTH_SHORT).show();
                        } else if ("网络中断".equals(result)) {
                            Toast.makeText(mContext, "网络中断", Toast.LENGTH_SHORT).show();
                        } else if ("请检查网络连接是否正常".equals(result)) {
                            Toast.makeText(mContext, "请检查网络连接是否正常" + fileUrl, Toast.LENGTH_SHORT).show();
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
