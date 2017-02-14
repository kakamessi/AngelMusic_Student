package com.angelmusic.student.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.R;
import com.angelmusic.student.base.BaseActivity;

public class IdleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_idle;
    }

    @Override
    protected void setTAG() {
        TAG = "==IdleActivity==";
    }


}
