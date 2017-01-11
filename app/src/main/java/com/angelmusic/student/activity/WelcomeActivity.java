package com.angelmusic.student.activity;

import android.content.Intent;
import android.os.Bundle;

import com.angelmusic.student.R;
import com.angelmusic.student.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {
    private Timer timer = new Timer();// 启动页面的显示时间计时器
    private TimerTask task = new TimerTask() {
        // 计时时间结束后跳转页面
        @Override
        public void run() {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();// 不再退回本界面
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.schedule(task, 1 * 1000);// 设置计时器时间
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void setTAG() {
        TAG = "==WelcomeActivity==";
    }
}
