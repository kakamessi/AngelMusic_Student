package com.angelmusic.stu.server.receiver;

import com.angelmusic.stu.server.socket.constant.Constant;
import com.angelmusic.stu.server.socket.constant.NetParams;
import com.angelmusic.stu.server.socket.constant.U3dType;
import com.angelmusic.stu.utils.Log;
import com.unity3d.player.UnityPlayer;

/**
 * Created by DELL on 2016/10/24.
 */

public class DefaultReceiver extends Receiver {

    int a = 0;

    /**
     * 当获取网络数据回调接口
     *
     * @param buffer 字节数据
     */
    public synchronized void receive(byte[] buffer){

        String action = new String(buffer);
        String[] sourceStrArray = checkData(action);

        for (int i = 0; i < sourceStrArray.length; i++) {

            //通信消息检测
            if(innerAction(sourceStrArray[i])){
                continue;
            }
            UnityPlayer.UnitySendMessage(U3dType.TYPE_Communication,U3dType.METHOD_ON_MSG, sourceStrArray[i]);

        }


    }


    private String[] checkData(String str) {

        String[] sourceStrArray = str.split(Constant.CONSTANT_HEARTBEAT);

        return sourceStrArray;

    }

    /**
     * 处理内部命令
     * @return
     */
    private boolean innerAction(String action){

        //接受学生端退出信息  或者  心跳消息
        if(action.equals(NetParams.ACTION_TYPE_OUT) || action.equals(Constant.CONSTANT_TRUE_HEARTBEAT)){

            return true;
        }

        return false;
    }

}
