package com.angelmusic.stu.server.socket.u3d;

import com.angelmusic.stu.network.model.NetParams;
import com.angelmusic.stu.server.socket.udp.UDPRecThread;
import com.angelmusic.stu.server.socket.udp.UDPWrite;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by DELL on 2016/10/28.
 */

public class UDPDispatcher implements IDispatcher{


    private UDPRecThread udpThread;
    private UDPWrite udpWrite;


    /**
     *
     * @param host    教师端ip地址   或者  广播地址
     * @param port    监听端口号
     */
    @Override
    public void init(String host,int port) {

        udpThread = new UDPRecThread();
        udpThread.open();

        if("".equals(host)){
            host = NetParams.BC_HOST;
        }

        SocketAddress adds = new InetSocketAddress(host, NetParams.PORT);
        udpWrite = new UDPWrite(adds);

    }

    @Override
    public void quit() {

        udpThread.close();
        udpWrite.quit();

    }

    @Override
    public void sendMsg(byte[] bytes) {

        udpWrite.sendMsg(bytes);

    }



}
