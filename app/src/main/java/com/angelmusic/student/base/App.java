package com.angelmusic.student.base;

import android.app.Application;
import android.content.Intent;

import com.angelmusic.stu.utils.MyCrashHandler;
import com.angelmusic.student.service.StudentService;
import com.angelmusic.student.utils.LogUtil;


/**
 * Created by DELL on 2016/12/6.
 */

public class App extends Application {

    /**
     * 整个(app)程序初始化之前被调用
     */
    // public NoteEntry entry;
    @Override
    public void onCreate() {
        super.onCreate();
        //initCrash();
        initService();
        LogUtil.isDebug = true;//设置是否打印Log日志
    }

    private void initService() {
        Intent startIntent = new Intent(this, StudentService.class);
        startService(startIntent);
    }

    private void initCrash() {
        // 初始化捕捉异常的类
        MyCrashHandler handler = MyCrashHandler.getInstance();
        handler.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }


}
