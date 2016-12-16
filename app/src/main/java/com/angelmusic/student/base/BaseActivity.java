package com.angelmusic.student.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.angelmusic.student.core.ActionDispatcher;
import com.angelmusic.student.utils.LogUtil;
import com.angelmusic.student.utils.NetworkUtil;

import butterknife.ButterKnife;

/**
 * Activity的基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = "BaseActivity";
    private Handler actionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleMsg(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreen();
        setContentView(setContentViewId());
        ButterKnife.bind(this);
        setTAG();
        ActionDispatcher.getInstance().register(TAG, actionHandler);
        LogUtil.setTAG(TAG);//给Log工具设置默认的TAG
        if (!NetworkUtil.checkedNetWork(this)) {
            showDialog("没有网络", "请设置网络", "设置", "取消");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        //控制底部导航栏的显示
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    //----设置页面布局
    protected abstract int setContentViewId();

    //****设置页面标签
    protected abstract void setTAG();

    //****处理通信命令
    protected void handleMsg(Message msg) {
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActionDispatcher.getInstance().remove(TAG);
    }

    /**
     * 设置全屏，不包括底部导航栏
     */
    private void setScreen() {
        // --去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // --去掉状态栏--全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    //显示dialog
    private void showDialog(String title, String message, String positive, String neutral) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(title).setMessage(message);
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }).setNeutralButton(neutral, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        }).create();
        builder.show();
    }
}






