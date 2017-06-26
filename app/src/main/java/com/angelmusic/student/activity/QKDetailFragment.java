package com.angelmusic.student.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angelmusic.student.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 */
public class QKDetailFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private List<Integer> mDatas;

    private String mId;

    public QKDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_qk_detail, container, false);
        initDatas();
        //得到控件
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recy_list);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(mAdapter);

        Toast.makeText(getActivity(),getmId()+"-------",0).show();

        return v;
    }

    private void initDatas()
    {
        mDatas = new ArrayList<>(Arrays.asList(R.mipmap.kaka_qlx,
                R.mipmap.kaka_qlx, R.mipmap.kaka_qlx, R.mipmap.kaka_qlx, R.mipmap.kaka_qlx,
                R.mipmap.kaka_qlx, R.mipmap.kaka_qlx, R.mipmap.kaka_qlx, R.mipmap.kaka_qlx));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public class GalleryAdapter extends
            RecyclerView.Adapter<GalleryAdapter.ViewHolder>
    {
        private LayoutInflater mInflater;
        private List<Integer> mDatas;

        public GalleryAdapter(Context context, List<Integer> datats)
        {
            mInflater = LayoutInflater.from(context);
            mDatas = datats;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public ViewHolder(View arg0)
            {
                super(arg0);
            }

            ImageView mImg;
            TextView mTxt;
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
        {
            View view = mInflater.inflate(R.layout.item_quku_list,
                    viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.mImg = (ImageView) view
                    .findViewById(R.id.id_index_gallery_item_image);
            return viewHolder;
        }

        /**
         * 设置值
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i)
        {
            viewHolder.mImg.setImageResource(mDatas.get(i));
        }

    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }
}
