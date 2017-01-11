package com.angelmusic.student.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.angelmusic.student.R;

import java.util.List;

/**
 * Created by fei on 2017/1/9.
 */

public class SeatAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public SeatAdapter(Context context, List<String> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();// item总数
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);// 返回下标为position的item的数据
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 定义一个ViewHolder类，封装数据
        ViewHolder holder;
        // 判断convertView是否为空,如果为空就创建一个不为空就重复使用
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.seat_layout_item, null);
            holder = new ViewHolder();
            holder.tvSeat = (TextView) convertView.findViewById(R.id.tv_seat);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvSeat.setText(list.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView tvSeat;
    }
}
