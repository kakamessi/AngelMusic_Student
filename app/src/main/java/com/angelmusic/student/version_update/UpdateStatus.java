package com.angelmusic.student.version_update;

/**
 * Created by fei on 2016/12/13.
 * 检测版本的状态类
 */
public interface UpdateStatus {
    /**
     * 没有新版本
     */
    public static int NO = 1;

    /**
     * 有新版本
     */
    public static int YES = 2;

    /**
     * 链接超时
     */
    public static int TIMEOUT = 3;

    /**
     * 没有wifi
     */
    public static int NO_WIFI = 4;

    /**
     * 数据解析出错
     */
    public static int PARSE_ERROR = -1;
}
