package com.angelmusic.stu.network.socket.tcp;


import com.angelmusic.stu.network.u3d.AndroidDispatcher;
import com.angelmusic.stu.utils.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by DELL on 2016/10/24.
 */

public class TCPWrite {

    private SSocket ss;

    public TCPWrite(SSocket ss){
        this.ss = ss;

    }

    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public void sendMsg(final String msg){

        singleThreadExecutor.submit(new Runnable() {
            @Override
            public void run() {

                try {

                    ss.write(msg.getBytes());

                } catch (Exception e) {

                }

            }
        });

    }

    public void sendMsg(final byte[] bytes){

        singleThreadExecutor.submit(new Runnable() {
            @Override
            public void run() {

                try {

                    ss.write(bytes);

                } catch (Exception e) {

//                    try {
//                        AndroidDispatcher.getInstance().reConnect(true);
//                    } catch (Exception e1) {
//
//                    }

                }

            }
        });

    }

    public void quit(){

        singleThreadExecutor.shutdownNow();

    }

    




}











