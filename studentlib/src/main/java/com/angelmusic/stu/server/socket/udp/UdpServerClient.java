package com.angelmusic.stu.server.socket.udp;

import com.angelmusic.stu.server.socket.constant.U3dType;
import com.unity3d.player.UnityPlayer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * Created by DELL on 2017/1/16.
 */

public class UdpServerClient {

    public void testUdp(){

        UnityPlayer.UnitySendMessage(U3dType.TYPE_Main_Camera,U3dType.METHOD_ON_MSG, "hi guancheng");

    }

    public void sendBroadCast(){

        final String hostIP = "255.255.255.255";
        final int port = 8000;

        DatagramSocket socket = null;
        try {
            // 1、创建套接字
            socket = new DatagramSocket();

            // 2、创建host的地址包装实例
            SocketAddress socketAddr = new InetSocketAddress(hostIP, port);

            // 3、创建数据报。包含要发送的数据、与目标主机地址
            byte[] data = "Hi angle".getBytes(Charset.forName("UTF-8"));
            DatagramPacket packet = new DatagramPacket(data, data.length, socketAddr);

            // 4、发送数据
            for(int i=0; i<5; i++) {
                Thread.sleep(10);
                socket.send(packet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                socket.close();
            }
        }

    }


}
