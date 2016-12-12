package com.angelmusic.stu.network.u3d;

import com.angelmusic.stu.network.socket.AbsReceiver;

/**
 * Created by DELL on 2016/10/27.
 *
 * 与u3d代码交互工具
 *
 */

public interface IDispatcher {

    /**
     * 初始化
     */
    public abstract void init(String host, int port, AbsReceiver receiver);


    /**
     * 关闭
     */
    public abstract void quit();


    /**
     * 发送数据
     *
     */
    public abstract void sendMsg(byte[] bytes);


}
