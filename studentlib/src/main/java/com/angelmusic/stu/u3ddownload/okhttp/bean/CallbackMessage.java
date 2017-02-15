package com.angelmusic.stu.u3ddownload.okhttp.bean;


import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.callback.CallbackOk;

/**
 * 响应回调信息体
 */
public class CallbackMessage extends OkMessage{

    public CallbackOk callback;
    public HttpInfo info;

    public CallbackMessage(int what, CallbackOk callback, HttpInfo info) {
        this.what = what;
        this.callback = callback;
        this.info = info;
    }


}