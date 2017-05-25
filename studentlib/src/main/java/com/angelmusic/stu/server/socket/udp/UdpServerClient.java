package com.angelmusic.stu.server.socket.udp;

import com.angelmusic.stu.server.socket.constant.NetParams;
import com.angelmusic.stu.u3dInterface.U3dType;
import com.unity3d.player.UnityPlayer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

/**
 * Created by DELL on 2017/1/16.
 */

public class UdpServerClient {

    DatagramSocket socket = null;
    SocketAddress socketAddr = null;

    public void testUdp(){

        UnityPlayer.UnitySendMessage(U3dType.TYPE_Communication,U3dType.METHOD_ON_MSG, "1|aewf45g45@192.168.1.25");

    }

    public UdpServerClient() {

        init();

    }

    private void init() {

        try {

            // 1、创建套接字
            socket = new DatagramSocket();
            // 2、创建host的地址包装实例
            socketAddr = new InetSocketAddress(NetParams.UDP_HOST, NetParams.UDP_PORT);

        } catch (SocketException e) {

            e.printStackTrace();
        }

    }

    public void sendSycMsg(){

        try {

            // 3、创建数据报。包含要发送的数据、与目标主机地址
            byte[] data = "Hi angle".getBytes(Charset.forName("UTF-8"));
            DatagramPacket packet = new DatagramPacket(data, data.length, socketAddr);

            // 4、发送数据
            for(int i=0; i<5; i++) {
                Thread.sleep(100);
                socket.send(packet);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
//        finally {
//            if (null != socket) {
//                socket.close();
//            }
//        }


    }

    public void close(){

        if (null != socket) {
            socket.close();
        }

    }


}
