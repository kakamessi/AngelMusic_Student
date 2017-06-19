package com.angelmusic.stu.server.socket.udp;

import android.os.Handler;
import com.angelmusic.stu.utils.Log;

import com.angelmusic.stu.server.socket.constant.NetParams;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by DELL on 2017/1/20.
 */

public class UDPServerRecThread extends Thread{


    private Handler handler;

    public UDPServerRecThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        Log.e("kaka","Teacher Step 2 :  init udp loop");
        DatagramSocket socket = null;
        try {
            // 1、创建套接字
            socket = new DatagramSocket(NetParams.TEACHER_UDP_PORT);

            // 2、创建数据报
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            UdpServerClient ucc = new UdpServerClient();

            // 3、一直监听端口，接收数据包
            while (true) {

                socket.receive(packet);

                String quest_ip = packet.getAddress().toString().substring(1);

                ucc.sendSycMsg();
                Log.e("kaka","Teacher Step 2 :  receive upd ip ==" + quest_ip);

            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("kaka","Teacher Step 2 :  init udp loop break --------------error-------------");
        }

    }


}
