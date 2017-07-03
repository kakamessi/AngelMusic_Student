package com.angelmusic.student.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtilInterface;
import com.angelmusic.stu.u3ddownload.okhttp.callback.CallbackOk;
import com.angelmusic.stu.u3ddownload.okhttp.callback.ProgressCallback;
import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.R;
import com.angelmusic.student.course_download.infobean.DetailBean;
import com.angelmusic.student.course_download.infobean.QukuDetail;
import com.angelmusic.student.utils.GsonUtil;
import com.angelmusic.student.utils.Utils;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import static com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheLevel.FIRST_LEVEL;
import static com.angelmusic.student.R.id.imageView;

/**
 *
 *
 */
public class QKDetailFragment extends Fragment{

    private ImageView iv_back;
    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private QukuDetail mDatas;

    private String mId;

    private Handler myHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case 1:
                    setView();
                    break;

                case 2:

                    break;

                case 3:

                    break;
            }

        }
    };

    public QKDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(),"-------  "+((ViewGroup)getActivity().getWindow().getDecorView().findViewById(R.id.layout_main_01)).getChildCount(),0).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_qk_detail, container, false);
        iv_back = (ImageView) v.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).popFragment();
            }
        });


        getPic();
        //得到控件
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recy_list);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    /**
     *
     * 获取曲库列表
     */
    private void getPic() {

        String domainNameRequest = getResources().getString(R.string.domain_name_request);
        String courseInfoJson = getResources().getString(R.string.get_pic_quku);

        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(FIRST_LEVEL)
                .setConnectTimeout(25).build(this);
        okHttpUtil.doGetAsync(
                HttpInfo.Builder().setUrl(domainNameRequest + courseInfoJson).addParam
                        ("reperId", getmId()).build(),
                new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {

                        final String jsonResult = info.getRetDetail();
                        if (info.isSuccessful()) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    mDatas = GsonUtil.jsonToObject(jsonResult, QukuDetail.class);//Gson解析
                                    Message msg = Message.obtain();
                                    msg.what =1;
                                    myHandler.sendMessage(msg);

                                }
                            }).start();
                        }
                    }
                });
    }

    private void setView(){

        if(mDatas.getCode()==200) {

            mAdapter = new GalleryAdapter(getActivity(), mDatas.getDetail());
            mRecyclerView.setAdapter(mAdapter);

        }

    }




    public class GalleryAdapter extends
            RecyclerView.Adapter<GalleryAdapter.ViewHolder>
    {
        private LayoutInflater mInflater;
        private List<DetailBean> mDatas;

        public GalleryAdapter(Context context, List<DetailBean> datats)
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

            View view = mInflater.inflate(R.layout.item_quku_list, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.mImg = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);

            return viewHolder;
        }

        /**
         * 设置值
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i)
        {

            Log.e("GalleryAdapter","onBindViewHolder  " + i);

            Glide
                    .with(QKDetailFragment.this.getActivity())
                    .load(getResources().getString(R.string.apk_download) + mDatas.get(i).getPath())
                    .into(viewHolder.mImg);

        }

    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }


}
