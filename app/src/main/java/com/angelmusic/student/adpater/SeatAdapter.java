package com.angelmusic.student.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.angelmusic.student.R;
import com.angelmusic.student.infobean.SeatDataInfo;

import java.util.List;

import static android.R.id.list;

/**
 * Created by fei on 2017/1/9.
 */

public class SeatAdapter extends BaseAdapter {
    private SeatDataInfo seatDataInfo;
    private Context context;

    public SeatAdapter(Context context) {
        this.context = context;
    }

    public SeatAdapter(Context context, SeatDataInfo seatDataInfo) {
        super();
        this.context = context;
        this.seatDataInfo = seatDataInfo;
    }

    @Override
    public int getCount() {
        return 33;// item总数
    }

    @Override
    public Object getItem(int position) {
        return null;// 返回下标为position的item的数据
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
        holder.tvSeat.setText("第" + position + "号座位");
        return convertView;
    }

    static class ViewHolder {
        TextView tvSeat;
    }
}
