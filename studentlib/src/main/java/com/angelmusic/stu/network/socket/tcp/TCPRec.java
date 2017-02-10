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
            socket.read(receiver);

        } catch (Exception e) {

            try {
                Log.e("kaka", "connect || read异常_____" + e.getMessage().toString() + host + ":" + port);
            }catch (Exception ex){
            }

        }

    }

}
