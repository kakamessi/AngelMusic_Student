package com.angelmusic.student.version_update;

import android.icu.util.VersionInfo;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;

/**
 * Created by fei on 2016/12/13.
 * app版本升级的核心管理类
 */

public class AppUpdateManager {
    private final static String url = "";//服务器地址
    public UpdateListener updateListener;

    //接口回调
    public interface UpdateListener {
        void onUpdateReturned(int updateStatus, VersionInfo versionInfo);

    }

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    /**
     * 请求网络检查是否需要更新
     */
    public static void checkVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpInfo info = HttpInfo.Builder()
                        .setUrl(url)
                        .addHead("head", "test")
                        .build();
                OkHttpUtil.getDefault(this)
                        .doGetSync(info);
                if (info.isSuccessful()) {
                    final String result = info.getRetDetail();
                    //进行Json解析

                }
            }
        }).start();
    }
}
