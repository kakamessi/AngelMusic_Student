package com.angelmusic.stu.server.socket.u3d;

/**
 * Created by DELL on 2016/10/28.
 */

public interface IDispatcher {

    /**
     * 初始化
     */
    public abstract void init(String host, int port);


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
