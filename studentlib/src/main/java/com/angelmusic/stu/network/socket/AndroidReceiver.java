package com.angelmusic.stu.network.socket;

import android.os.Handler;
import android.os.Message;

import com.angelmusic.stu.network.model.ActionType;
import com.angelmusic.stu.network.utils.FileUtil;

/**
 * Created by DELL on 2016/10/24.
 */

public class AndroidReceiver extends AbsReceiver {

    private Handler handler;

    public AndroidReceiver(Handler handler){

        this.handler = handler;
    }

    /**
     * 当建立连接是的回调
     */
    public void connected(){

        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

    }

    /**
     * 当获取网络数据回调接口
     *
     * @param buffer 字节数据
     */
    public void receive(byte[] buffer){

        String[] sourceStrArray = checkData(buffer);

        for (int i = 0; i < sourceStrArray.length; i++) {

            Message msg = new Message();
            msg.what = 2;
            msg.obj = sourceStrArray[i];
            handler.sendMessage(msg);

            if("deletecache".equals(sourceStrArray[i])){
                FileUtil.deleteLogFile();
            }

        }



//            Message msg = new Message();
//            msg.what = 2;
//            msg.obj = new String(buffer);
//            handler.sendMessage(msg);
//            Log.e("kaka", "receive(byte[] buffer) :  "  + new String(buffer));

    }

    private String[] checkData(byte[] buffer) {

        String[] sourceStrArray = new String(buffer).split(ActionType.CONSTANT_HEARTBEAT);

        return sourceStrArray;

    }

    /**
     * 当断开连接的回调
     */
    public void disconnect(){

        Message msg = new Message();
        msg.what = 3;
        handler.sendMessage(msg);

    }


}
