package com.angelmusic.stu.network.socket.tcp;

import com.angelmusic.stu.network.socket.AbsReceiver;
import com.angelmusic.stu.utils.Log;


/**
 *
 * Created by DELL on 2016/10/24.
 * 初始化连接，能接受消息
 * 可以发消息
 * 关闭连接
 *
 */

public class TCPRec implements Runnable{

    private SSocket socket;
    private volatile boolean isConnect = false;

    private String host;
    private int port;

    private AbsReceiver receiver;

    public TCPRec(SSocket socket, String host, int port, AbsReceiver receiver){

        this.host = host;
        this.port = port;
        this.receiver = receiver;
        this.socket = socket;

    }

    @Override
    public void run() {

        try {

            socket.connect(host,port);
            Log.e("kaka","Connection200       connect OK");
            socket.read(receiver);


        } catch (Exception e) {

            //e可能为null 教师端删除进程 学生端崩溃
            e.printStackTrace();
            Log.e("kaka","Connection404       connect or read Exception");

        }

    }

}
