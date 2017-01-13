package com.angelmusic.student.adpater;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angelmusic.student.R;
import com.angelmusic.student.activity.MainActivity;
import com.angelmusic.student.customview.CustomCircleProgress;

import java.util.List;

import static com.angelmusic.student.R.id.circleProgress;
import static com.angelmusic.student.R.id.tabMode;

/**
 * Created by fei on 2017/1/11.
 */

public class DownloadAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> list;

    public DownloadAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
        holder.tvCourseName.setText("第"+position+"节课");
        holder.circleProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
                if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Start) {//初始下载状态
                    //点击则变成下载状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);
                    //开始下载

                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Loading) {//下载状态
                    //点击则变成暂停状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Pause);


                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {//下载暂停状态
                    //点击则变成下载状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Loading);

                } else if (holder.circleProgress.getStatus() == CustomCircleProgress.Status.End) {//下载结束状态
                    //点击变成初始下载状态
                    holder.circleProgress.setStatus(CustomCircleProgress.Status.Start);
                }
            }
        });
        return convertView;
    }

    // 封装数据
    static class ViewHolder {
        private TextView tvCourseName, tvProgress;
        private CustomCircleProgress circleProgress;
    }
}
