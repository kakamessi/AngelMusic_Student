package com.angelmusic.student.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.angelmusic.student.R;
import com.angelmusic.student.adpater.QuKuAdapter;
import com.angelmusic.student.adpater.QuKuAdapter.OnItemClickLitener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class QuKuFragment extends Fragment{

    private List<String> mDatas;
    private QuKuAdapter qkAdapter;

    private RecyclerView mRecyclerView;


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
        init();
        return v;
    }

    private void init() {

        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        qkAdapter = new QuKuAdapter(getActivity(),mDatas);
        qkAdapter.setOnItemClickLitener(new QuKuAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                QKDetailFragment qkF = new QKDetailFragment();
                qkF.setmId(position + "haha");
                fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).add(R.id.layout_main_01,qkF).addToBackStack(null).commit();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(qkAdapter);

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


}
