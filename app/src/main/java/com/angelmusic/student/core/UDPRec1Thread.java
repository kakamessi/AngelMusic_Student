package com.angelmusic.student.core;

import android.os.Handler;

import com.angelmusic.stu.network.u3d.AndroidDispatcher;
import com.angelmusic.student.constant.Constant;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by DELL on 2017/1/16.
 */

public class UDPRec1Thread extends Thread {


    private Handler handler;
    private int port = 8000;

    public UDPRec1Thread() {

    }

    @Override
    public void run() {

        DatagramSocket socket = null;
        try {
            // 1、创建套接字
            socket = new DatagramSocket(port);

            // 2、创建数据报
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            // 3、一直监听端口，接收数据包
            socket.receive(packet);

            String quest_ip = packet.getAddress().toString().substring(1);

            Constant.HOST = quest_ip;

            AndroidDispatcher.getInstance().quit();
            AndroidDispatcher.getInstance().init(Constant.HOST, Constant.PORT, new MsgReceiver());

//            Message msg = new Message();
//            msg.what = 4;
//            msg.obj = quest_ip;
//            handler.sendMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
