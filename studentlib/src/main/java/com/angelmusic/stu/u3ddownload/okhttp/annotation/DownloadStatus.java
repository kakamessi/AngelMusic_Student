package com.angelmusic.stu.u3ddownload.okhttp.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 下载状态
 */
@StringDef({DownloadStatus.INIT, DownloadStatus.DOWNLOADING, DownloadStatus.PAUSE, DownloadStatus.COMPLETED})
@Retention(RetentionPolicy.SOURCE)
public @interface DownloadStatus {

    String INIT = "INIT";//初始化状态
    String DOWNLOADING = "DOWNLOADING";//正在下载状态
    String PAUSE = "PAUSE";//暂停状态
    String COMPLETED = "COMPLETED";//下载完成状态

}