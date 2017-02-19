package com.angelmusic.stu.server.socket.tcp;

import com.angelmusic.stu.server.receiver.Receiver;
import com.angelmusic.stu.utils.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by DELL on 2016/10/25.
 *
 *
 */

public class RecRunner implements Runnable {

    private Receiver handler;

    static volatile boolean isOn = true;

    private InputStream in = null;
    private OutputStream out = null;
    private Ssocket ss;


    public RecRunner(Socket socket) throws Exception {

            ss = new Ssocket(socket);

    }

    public RecRunner(Receiver handler, Ssocket socket) throws Exception{

        this.handler = handler;
        this.ss = socket;

    }

    @Override
    public void run() {

        try {

            ss.read(handler);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }




}
