package com.angelmusic.stu.u3ddownload.okhttp.bean;

import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.callback.ProgressCallback;

/**
 * 上传响应回调信息体
 */
public class UploadMessage extends OkMessage {

    public String filePath;
    public HttpInfo info;
    public ProgressCallback progressCallback;

    public UploadMessage(int what, String filePath, HttpInfo info, ProgressCallback progressCallback) {
        this.what = what;
        this.filePath = filePath;
        this.info = info;
        this.progressCallback = progressCallback;
    }
}