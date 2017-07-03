package com.angelmusic.student.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtilInterface;
import com.angelmusic.stu.u3ddownload.okhttp.callback.CallbackOk;
import com.angelmusic.student.R;
import com.angelmusic.student.adpater.QuKuAdapter;
import com.angelmusic.student.course_download.infobean.NewCourseInfo;
import com.angelmusic.student.course_download.infobean.QukuListInfo;
import com.angelmusic.student.utils.GsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheLevel.FIRST_LEVEL;

/**
 *
 *
 */
public class QuKuFragment extends Fragment{

    private QuKuAdapter qkAdapter;

    private ImageView iv_back;
    private RecyclerView mRecyclerView;
    private QukuListInfo qList;

    private Handler myHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case 1:
                    setList();
                    break;

                case 2:

                    break;

                case 3:

                    break;
            }

        }
    };


    public QuKuFragment() {
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
        View v = inflater.inflate(R.layout.fragment_qu_ku, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recy_list);
        iv_back = (ImageView) v.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).popFragment();
            }
        });
        initQuku();

        return v;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     *
     * 获取曲库列表
     */
    private void initQuku() {

        String domainNameRequest = getResources().getString(R.string.domain_name_request);
        String courseInfoJson = getResources().getString(R.string.get_list_quku);

        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(FIRST_LEVEL)
                .setConnectTimeout(25).build(this);
        okHttpUtil.doGetAsync(
                HttpInfo.Builder().setUrl(domainNameRequest + courseInfoJson).build(),
                new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {

                        final String jsonResult = info.getRetDetail();
                        if (info.isSuccessful()) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    qList = GsonUtil.jsonToObject(jsonResult, QukuListInfo.class);//Gson解析
                                    Message msg = Message.obtain();
                                    msg.what =1;
                                    myHandler.sendMessage(msg);

                                }
                            }).start();
                        }
                    }
                });
    }


    private void setList(){

        if (qList.getCode() == 200) {
            //封装数据

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            qkAdapter = new QuKuAdapter(getActivity(),qList);
            qkAdapter.setOnItemClickLitener(new QuKuAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    QKDetailFragment qkF = new QKDetailFragment();
                    qkF.setmId(qList.getDetail().get(position).getId());
                    fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).add(R.id.layout_main_01,qkF).addToBackStack(null).commit();

                }
                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            mRecyclerView.setAdapter(qkAdapter);

        }

    }
}
