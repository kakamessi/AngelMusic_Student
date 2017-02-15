package com.angelmusic.stu;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheLevel;
import com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheType;
import com.angelmusic.stu.u3ddownload.okhttp.cookie.PersistentCookieJar;
import com.angelmusic.stu.u3ddownload.okhttp.cookie.cache.SetCookieCache;
import com.angelmusic.stu.u3ddownload.okhttp.cookie.persistence.SharedPrefsCookiePersistor;
import com.angelmusic.stu.u3ddownload.HttpInterceptor;
import com.angelmusic.stu.utils.MyCrashHandler;

import java.io.File;

public class MyApplication extends Application {
    public static OkHttpUtil.Builder init;

    /**
     * 整个(app)程序初始化之前被调用
     *
     * @author Administrator
     */
    // public NoteEntry entry;
    @Override
    public void onCreate() {
        super.onCreate();
        dele();
        // 初始化捕捉异常的类
        MyCrashHandler handler = MyCrashHandler.getInstance();
        handler.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(handler);
        initOkHttp();
    }

    private void initOkHttp() {
        init = OkHttpUtil.init(this);
        init.setConnectTimeout(30)//连接超时时间
                .setWriteTimeout(30)//写超时时间
                .setReadTimeout(30)//读超时时间
                .setMaxCacheSize(10 * 1024 * 1024)//缓存空间大小
                .setCacheLevel(CacheLevel.FIRST_LEVEL)//缓存等级
                .setCacheType(CacheType.NETWORK_THEN_CACHE)//缓存类型
                .setShowHttpLog(true)//显示请求日志
                .setShowLifecycleLog(true)//显示Activity销毁日志
                .setRetryOnConnectionFailure(false)//失败后不自动重连
                .addResultInterceptor(HttpInterceptor.ResultInterceptor)//请求结果拦截器
                .addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this)))//持久化cookie
                .build();
    }

    String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/wifiLog";
    String fileName = "Usb_connect_Log.txt";

    public void dele() {
        File file = new File(sdDir + fileName);

        if (file.exists()) {
            // file.mkdir();
            boolean isDele = file.delete();
            Log.e("SaveData", "删除文件-->" + isDele);
        }
    }
}
