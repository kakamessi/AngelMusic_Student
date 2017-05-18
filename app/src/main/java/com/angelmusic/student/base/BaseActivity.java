package com.angelmusic.student.base;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.angelmusic.student.R;
import com.angelmusic.student.constant.Constant;
import com.angelmusic.student.core.ActionDispatcher;
import com.angelmusic.student.login.LoginManager;
import com.angelmusic.student.login.StuInfo;
import com.angelmusic.student.utils.LogUtil;
import com.angelmusic.student.utils.SharedPreferencesUtil;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;

/**
 * Activity的基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = "BaseActivity";
    private final String APATCH_NAME = "myfix.apatch"; // 补丁文件名
    private ViewGroup mViewGroup;
    private View loadingView;//加载圈圈的布局
    private Animation rotateAnimation;//加载圈圈的动画
    private Handler actionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleMsg(msg);
        }
    };
    private ImageView ivLoading;
    protected App mBaseApp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreen();
        setContentView(setContentViewId());
        mBaseApp = App.getApplication();
        ButterKnife.bind(this);
        setTAG();
        ActionDispatcher.getInstance().register(TAG, actionHandler);
        LogUtil.setTAG(TAG);//给Log工具设置默认的TAG
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

    protected void login(String classId){

        //String classId = SharedPreferencesUtil.getString(Constant.CACHE_CLASS_ID,"");
        LoginManager.login(this,classId, new LoginManager.IsLoginSucceed() {
            @Override
            public void isSucceed(StuInfo stuInfo) {
                //保存学生id
                SharedPreferencesUtil.setString(Constant.CACHE_STUDENT_ID,stuInfo.getDetail().getStuInfo().getId()+"");
                SharedPreferencesUtil.setString(Constant.CACHE_CLASS_ID, stuInfo.getDetail().getStuInfo().getClassId() + "");
                SharedPreferencesUtil.setString(Constant.CACHE_SCHOOL_ID, stuInfo.getDetail().getStuInfo().getSchoolId() + "");
                Toast.makeText(App.getApplication(),"登录成功",0).show();
            }

            @Override
            public void isFailed() {

            }
        });

    }

    //****向教师端发送命令
    protected void sendMsg(String str) {
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

    /**
     * 热修复更新
     */
    public void update() {
        String patchFileStr = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APATCH_NAME;
        try {
            App.mPatchManager.addPatch(patchFileStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载过程中显示旋转圈
     */
    public void showLoadingDialog() {
        loadingView = LayoutInflater.from(this).inflate(R.layout.loading_layout, null);
        ivLoading = (ImageView) loadingView.findViewById(R.id.iv_loading);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        mViewGroup = (ViewGroup) this.findViewById(android.R.id.content);
        mViewGroup.addView(loadingView);
        ivLoading.setVisibility(View.VISIBLE);
        ivLoading.startAnimation(rotateAnimation);//开始动画
    }

    /**
     * 停止旋转圈
     */
    public void hideLoadingDialog() {
        if(ivLoading!=null){
            ivLoading.setVisibility(View.GONE);
            ivLoading.clearAnimation();
        }
    }
}






