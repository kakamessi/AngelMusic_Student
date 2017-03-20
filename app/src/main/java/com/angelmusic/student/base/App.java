package com.angelmusic.student.base;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;
import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheLevel;
import com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheType;
import com.angelmusic.stu.u3ddownload.okhttp.callback.ProgressCallback;
import com.angelmusic.stu.u3ddownload.okhttp.cookie.PersistentCookieJar;
import com.angelmusic.stu.u3ddownload.okhttp.cookie.cache.SetCookieCache;
import com.angelmusic.stu.u3ddownload.okhttp.cookie.persistence.SharedPrefsCookiePersistor;
import com.angelmusic.stu.utils.MyCrashHandler;
import com.angelmusic.student.infobean.CourseData;
import com.angelmusic.student.service.StudentService;
import com.angelmusic.student.utils.LogUtil;
import com.angelmusic.student.utils.SharedPreferencesUtil;

import java.io.File;
import java.io.IOException;


/**
 * Created by DELL on 2016/12/6.
 */

public class App extends Application {

    private CourseData cd = new CourseData();
    private static App myApplication = null;
    public static PatchManager mPatchManager;
    private final String PATCH_URL = "";//下载补丁的地址
    private String PATCH_PATH;//补丁的本地存储地址
    private final String PATCH_NAME = "hotfix.apatch";//补丁的命名
    public static OkHttpUtil.Builder init;
    private Handler VideoHandler = null;

    public static App getApplication(){
        if (myApplication == null){
            myApplication = new App();
        }
        return myApplication;
    }

    /**
     * 整个(app)程序初始化之前被调用
     */
    // public NoteEntry entry;
    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
        initCrash();
        initService();
        LogUtil.isDebug = true;//设置是否打印Log日志
        initOkHttp();//初始化网络框架
        SharedPreferencesUtil.setContextAndInit(this, "ANGEL_MUSIC", MODE_PRIVATE);//初始化

        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/avva/";
        File filePath = new File(sdDir);
        File file = new File(sdDir);
        // if file doesnt exists, then create it
        if (!filePath.exists()) {
            boolean s = filePath.mkdir();
            Log.e("App", "创建目录-->" + s);
        }

//        initHotfix();//热修复的初始化
//        downAndSetPatch();//下载补丁并安装补丁

    }

    //初始化网络框架
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
//                .setDownloadFileDir(String downloadFileDir)//文件下载保存目录(根据实际需求设置App.init.set...)
                .addResultInterceptor(HttpInterceptor.ResultInterceptor)//请求结果拦截器
                .addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this)))//持久化cookie
                .build();
    }

    //热修复初始化
    private void initHotfix() {
        mPatchManager = new PatchManager(this);// 初始化patch管理类
        String versionName = null;
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mPatchManager.init(versionName);// 初始化patch版本
        mPatchManager.loadPatch();// 加载已经添加到PatchManager中的patch
    }

    //下载补丁文件
    private void downAndSetPatch() {
        PATCH_PATH = getExternalFilesDir(null).getAbsolutePath() + File.separator + "patch";
        OkHttpUtil.init(this).setDownloadFileDir(PATCH_PATH);//设置文件下载的保存目录
        downloadFile();
    }

    /**
     * 补丁文件下载
     */
    private void downloadFile() {
        final HttpInfo info = HttpInfo.Builder()
                .addDownloadFile(PATCH_URL, "hotfix", new ProgressCallback() {
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                        if (done) {
                            try {
                                mPatchManager.addPatch(PATCH_PATH + PATCH_NAME);//下载完成，安装下载的补丁
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onResponseMain(String filePath, HttpInfo info) {
                        if (info.getRetCode() == HttpInfo.CheckNet) {
                            //网络不可用弹框请求设置网络
                        }
                    }
                }).build();
        OkHttpUtil.Builder()
                .setReadTimeout(120)
                .build()//绑定请求标识
                .doDownloadFileAsync(info);

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

    public CourseData getCd() {
        return cd;
    }

    public void setCd(CourseData cd) {
        this.cd = cd;
    }

    public Handler getVideoHandler() {
        return VideoHandler;
    }
    public void setVideoHandler(Handler videoHandler) {
        VideoHandler = videoHandler;
    }

}
