package com.angelmusic.stu.network.socket;

import com.angelmusic.stu.network.model.ActionType;
import com.angelmusic.stu.network.socket.tcp.SSocket;
import com.angelmusic.stu.network.socket.tcp.TCPWrite;
import com.angelmusic.stu.network.u3d.AndroidDispatcher;

/**
 * Created by DELL on 2016/11/4.
 *
 * 断线重连线程
 *
 */

public class ReConnectThread extends Thread{

    private TCPWrite write;
    private SSocket ss;

    public volatile boolean flag = true;

    public ReConnectThread() {
        //this.write = write;

    }

    @Override
    public void run() {

        while (flag){

            try {

                Thread.sleep(6000);
                AndroidDispatcher.getInstance().sendMsg(ActionType.CONSTANT_TRUE_HEARTBEAT);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void quit(){

        flag = false;
        this.interrupt();

    }


}
