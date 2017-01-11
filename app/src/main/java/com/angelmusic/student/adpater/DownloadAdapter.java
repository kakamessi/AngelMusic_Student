package com.angelmusic.student.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angelmusic.student.R;
import com.angelmusic.student.customview.CustomCircleProgress;

/**
 * Created by fei on 2017/1/11.
 */

public class DownloadAdapter extends BaseAdapter {
    private Context mContext;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dload_lv_item_layout, null);
            holder.tvCourseName = (TextView) convertView.findViewById(R.id.tv_courseName);
            holder.tvDelete = (TextView) convertView.findViewById(R.id.tv_delete_icon);
            holder.tvDownload = (TextView) convertView.findViewById(R.id.tv_dload_icon);
            holder.llProgress = (LinearLayout) convertView.findViewById(R.id.ll_progress);
            holder.circleProgress = (CustomCircleProgress) convertView.findViewById(R.id.circleProgress);
            holder.tvProgress = (TextView) convertView.findViewById(R.id.tv_progress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    // 封装数据
    static class ViewHolder {
        private TextView tvCourseName, tvDownload, tvDelete, tvProgress;
        private LinearLayout llProgress;
        private CustomCircleProgress circleProgress;
    }
}
