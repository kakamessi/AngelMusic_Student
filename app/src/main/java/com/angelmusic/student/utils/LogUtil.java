package com.angelmusic.student.utils;

import android.util.Log;

/**
 * Created by fei on 2016/12/2.
 * Log的工具类
 */

public class LogUtil {
    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static String tag;//传入每个类的类名作为tag

    public static void setTAG(String logTag) {
        tag = logTag;
    }

    // 下面四个是默认TAG的函数,TAG是类名
    public static void i(String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }
}
