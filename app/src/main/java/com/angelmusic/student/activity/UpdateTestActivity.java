package com.angelmusic.student.activity;

import android.os.Bundle;
import android.widget.Button;

import com.angelmusic.student.R;
import com.angelmusic.student.base.BaseActivity;
import com.angelmusic.student.version_update.ApkManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateTestActivity extends BaseActivity {

    @BindView(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_update_test;
    }

    @Override
    protected void setTAG() {
        TAG = "==UpdateTestActivity==";
    }

    @OnClick(R.id.button4)
    public void onClick() {
        ApkManager.getInstance(UpdateTestActivity.this).checkVersionInfo();
    }
}
