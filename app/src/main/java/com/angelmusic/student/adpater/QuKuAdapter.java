package com.angelmusic.student.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angelmusic.student.R;

import java.util.List;

/**
 * Created by DELL on 2017/6/23.
 */

public class QuKuAdapter extends RecyclerView.Adapter<QuKuAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mDatas;

    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    public void setOnItemClickLitener(QuKuAdapter.OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public QuKuAdapter(Context mtx, List<String> data) {
        mContext = mtx;
        mDatas = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_quku, parent, false));


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tv.setText("七里香");

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.mView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

        }

    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {

        View mView;
        TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
            mView = view;
        }
    }




}
