package com.angelmusic.stu.network.socket;

public abstract class AbsReceiver {

    /**
     * 当建立连接的回调
     */
    public void connected(){

    }

    /**
     * 当获取网络数据回调接口
     *
     * @param buffer 字节数据
     */
    public void receive(byte[] buffer){

    }

    /**
     * 当断开连接的回调
     */
    public void disconnect(){

    }
}
