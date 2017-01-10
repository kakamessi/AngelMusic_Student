package com.angelmusic.stu.server.receiver;

import android.os.Handler;
import android.os.Message;

/**
 * Created by DELL on 2016/11/3.
 */

public class AndroidReceiver extends Receiver{

    private Handler handler;

    public AndroidReceiver(Handler handler) {

        this.handler = handler;

    }

    /**
     * 当获取网络数据回调接口
     *
     * @param buffer 字节数据
     */
    public synchronized void receive(byte[] buffer){

        Message msg = new Message();
        msg.what = 2;
        msg.obj = new String(buffer);
        handler.sendMessage(msg);

    }


}
