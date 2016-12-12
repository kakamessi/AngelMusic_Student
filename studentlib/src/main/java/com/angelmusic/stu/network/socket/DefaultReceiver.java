package com.angelmusic.stu.network.socket;


import com.angelmusic.stu.network.model.ActionType;


/**
 * Created by DELL on 2016/10/24.
 * 消息接受处理类
 *
 */
public class DefaultReceiver extends AbsReceiver {

    public DefaultReceiver(){
    }

    /**
     * 当获取网络数据回调接口
     *
     * @param buffer 字节数据
     */
    public void receive(byte[] buffer){

        String[] sourceStrArray = checkData(buffer);
        for (int i = 0; i < sourceStrArray.length; i++){


        }

    }

    private String[] checkData(byte[] buffer) {

        String[] sourceStrArray = new String(buffer).split(ActionType.CONSTANT_HEARTBEAT);

        return sourceStrArray;

    }


    /**
     * Android端  数据接收
     */
    public static interface OnVedioActionLisner {

        boolean onAyncMsg(String action);

    }



}




