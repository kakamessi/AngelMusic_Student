package com.angelmusic.student.adpater;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.angelmusic.student.R;
import com.angelmusic.student.infobean.SeatDataInfo;

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
        if (seatDataInfo == null) {
            return 0;
        }
        return seatDataInfo.getSeatList().size();// item总数
    }

    @Override
    public Object getItem(int position) {
        return seatDataInfo.getSeatList().get(position);// 返回下标为position的item的数据
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
        String state = seatDataInfo.getSeatList().get(position).getState();//1能座，2无，3搜索不到
        String seatIndexDescription = seatDataInfo.getSeatList().get(position).getSeatIndexDescription();
        String seatNo = seatDataInfo.getSeatNo();
        if ("无".equals(seatIndexDescription)) {
            holder.tvSeat.setText("无");
            holder.tvSeat.setTextColor(Color.parseColor("#888888"));
            holder.tvSeat.setBackgroundResource(R.drawable.seat_text_bg_white);
        } else if (seatNo.equals(seatIndexDescription)) {//当前pad号
            holder.tvSeat.setTextColor(Color.parseColor("#ffffff"));
            holder.tvSeat.setText(seatIndexDescription + "号座位");
            holder.tvSeat.setBackgroundResource(R.drawable.seat_text_bg_blue);
        } else if ("3".equals(state)) {//搜索不到
            holder.tvSeat.setTextColor(Color.parseColor("#575757"));
            holder.tvSeat.setText(seatIndexDescription + "号座位");
            holder.tvSeat.setBackgroundResource(R.drawable.seat_text_bg_dashed);
        } else if ("1".equals(state)) {
            holder.tvSeat.setTextColor(Color.parseColor("#ffffff"));
            holder.tvSeat.setText(seatIndexDescription + "号座位");
            holder.tvSeat.setBackgroundResource(R.drawable.seat_text_bg_green);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvSeat;
    }
}
