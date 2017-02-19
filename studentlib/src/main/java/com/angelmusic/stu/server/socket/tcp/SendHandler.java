package com.angelmusic.stu.server.socket.tcp;

import com.angelmusic.stu.utils.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by DELL on 2016/10/25.
 * 发消息统一接口
 *
 */

public class SendHandler {

    private int a = 1;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);


    public void sendMsg(final Ssocket socket, final byte[] bytes){

        fixedThreadPool.submit(new Runnable() {
            @Override
            public void run() {

                try {

                    socket.write(bytes);

                } catch (Exception e) {

                }

            }
        });

    }

    public void quit(){

        fixedThreadPool.shutdownNow();

    }


}
