package com.angelmusic.stu.server.socket.udp;


import java.net.SocketAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by DELL on 2016/10/28.
 *
 * udp发送消息类
 *
 */

public class UDPWrite {


    private UDPSocket udps;
    private SocketAddress address;

    public UDPWrite(SocketAddress address){

        try {

            this.address = address;
            this.udps = new UDPSocket(null);

        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public void sendMsg(final String msg){

        singleThreadExecutor.submit(new Runnable() {
            @Override
            public void run() {

                try {

                    udps.send(address,msg.getBytes());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void sendMsg(final byte[] bytes){

        singleThreadExecutor.submit(new Runnable() {
            @Override
            public void run() {

                try {

                    udps.send(address,bytes);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void quit(){

        singleThreadExecutor.shutdownNow();

    }

//    private static class WriteThread implements Runnable{
//
//        @Override
//        public void run() {
//
//
//        }
//    }




}
