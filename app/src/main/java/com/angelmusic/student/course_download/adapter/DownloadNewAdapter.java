package com.angelmusic.student.course_download.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.callback.ProgressCallback;
import com.angelmusic.student.R;
import com.angelmusic.student.activity.DownloadActivity;
import com.angelmusic.student.course_download.infobean.CourseItemInfo;
import com.angelmusic.student.customview.CustomCircleProgress;
import com.angelmusic.student.utils.LogUtil;
import com.angelmusic.student.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.angelmusic.student.R.id.circleProgress;

/**
 * Created by fei on 2017/1/11.
 * 注意:传入的courseDataList的第一条数据需要设置为null,用于显示全部下载的item
 */

public class DownloadNewAdapter extends BaseAdapter {
    private Context mContext;
    private List<CourseItemInfo> courseDataList;
    private LayoutInflater mInflater;
    private DownloadActivity context;

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

    public void bindAty(DownloadActivity context){
        this.context = context;
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

        holder.tvCourseName.setText(ci.getCourse_name());
        holder.tvProgress.setText((int)((ci.getDone_num()/ci.getAll_num())*100) + "%");
        holder.circleProgress.setProgress((int)((ci.getDone_num()/ci.getAll_num())*100));

        if(ci.getIsActive()==1){
            holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);//点击则变成准备下载状态
        }else if(ci.getIsActive()==2){
            holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);//点击则变成下载状态
        }else if(ci.getIsActive()==3){
            holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);//点击则变成暂停状态
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
                    startDownLoadTask(ci);

                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Loading) {//下载状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);//点击则变成暂停状态
                    //暂停
                    ci.setIsActive(3);
                    for (Map.Entry<String, String> entry : ci.getResUrl().entrySet()) {
                        OkHttpUtil.Builder().build().cancelRequest(entry.getKey());
                    }

                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {//暂停状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);//点击则变成下载状态
                    //继续下载，同下载
                    ci.setIsActive(2);
                    startDownLoadTask(ci);

                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.End) {//下载全部完成状态

//                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);//点击变成初始下载状态
//                    //删除
//                    ci.setIsActive(1);

                }
            }
        });

        return convertView;
    }

    /*
    *   批量下载
    *   1，检测是否停止下载
    *   2，检测文件是存在否
    * */
    private void startDownLoadTask(final CourseItemInfo ci) {
                Map<String, String> map = ci.getResUrl();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    if(ci.getIsActive()!=3 && !Utils.isFileExist(entry.getKey()) ){
                        //!Utils.isFileExist(entry.getKey())
                        downloadFile(entry.getValue(), entry.getKey(), ci);
                    }
                }
    }

    /**
     * 单个文件的下载
     */
    private void downloadFile(String url, String fileName, final CourseItemInfo ci) {

        context.refreashAdapter();
        final String fileNameCutSuffix = fileName.substring(0, fileName.lastIndexOf("."));//文件名，不带后缀（因为使用的网络框架下载完文件后会自动添加后缀名）
        final HttpInfo info = HttpInfo.Builder()
                .addDownloadFile(url, fileNameCutSuffix, new ProgressCallback() {
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
//                        LogUtil.e("----percent----", "=" + percent);
                        if (done) {
                            //刷新界面
                            ci.setDone_num(ci.getDone_num() + 1);
                            LogUtil.e("kaka_down", "ci.getDone_num()=" + ci.getDone_num());
                            if(ci.getAll_num()==ci.getDone_num()) {
                                ci.setIsActive(4);
                            }

                            context.refreashAdapter();
                            setHeadButton();
                        }
                    }

                    @Override
                    public void onResponseMain(String fileUrl, HttpInfo info) {
                        LogUtil.e("kaka_down", "下载结果：" + info.getRetDetail() + "   " + fileUrl);

                        //下载异常中断，刷新UI界面状态
                        if(info.getRetCode()!=HttpInfo.SUCCESS){
                            ci.setIsActive(3);
                            refreshProgress();
                        }

                    }
                })
                .build();
        OkHttpUtil.Builder()
                .setReadTimeout(120)
                .build(fileName)//绑定请求标识
                .doDownloadFileAsync(info);
    }

    /**
     *  设置按钮状态
     */
    private void setHeadButton() {

        float downNum = 0;
        float allNum = 0;

        for(CourseItemInfo cif : courseDataList){
            downNum = downNum + cif.getDone_num();
            allNum = allNum + cif.getAll_num();
        }

        if(downNum == allNum){
            context.setHeadViewType(3);
        }

    }

    // 其他Item数据封装
    static class ViewHolder {
        private LinearLayout linearLayout;
        private TextView tvCourseName, tvProgress;
        private CustomCircleProgress circleProgress;
    }

    public void setEmptyData(){
        for(CourseItemInfo cii : courseDataList){
                cii.setDone_num(0);
                cii.setIsActive(1);
        }
        refreshProgress();
    }

    //可以随时更改数据后更新适配器
    public void refreshProgress() {
        notifyDataSetChanged();
    }

    //全部下载
    public void downloadAll(final boolean flag){

        new Thread(new Runnable() {
            @Override
            public void run() {

                for(CourseItemInfo cii : courseDataList){

                    if(flag){
                        if(cii.getIsActive()!=4){
                            cii.setIsActive(2);
                        }
                        startDownLoadTask(cii);
                    }else{
                        if(cii.getIsActive()!=4) {
                            cii.setIsActive(3);
                            for (Map.Entry<String, String> entry : cii.getResUrl().entrySet()) {
                                OkHttpUtil.Builder().build().cancelRequest(entry.getKey());
                            }
                        }
                    }
                }
                context.refreashAdapter();

            }
        }).start();

    }


}
