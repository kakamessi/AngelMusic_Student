package com.angelmusic.stu.server.socket.tcp;

import com.angelmusic.stu.server.receiver.Receiver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by DELL on 2016/11/1.
 */

public class Ssocket {

    private int MAX_SIZE = 1024 * 3;

    public Socket mSocket;
    private OutputStream out;
    private InputStream in;

    public Ssocket(Socket ss) throws Exception {

        mSocket = ss;
        mSocket.setTcpNoDelay(true);

        out = mSocket.getOutputStream();
        in = mSocket.getInputStream();


    }


    public void disconnect() {
        if (mSocket == null) {
            return;
        }
        if (!mSocket.isInputShutdown()) {
            try {
                mSocket.shutdownInput();
            } catch (Exception e) {
            }
        }
        if (!mSocket.isOutputShutdown()) {
            try {
                mSocket.shutdownOutput();
            } catch (Exception e) {
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (Exception e) {
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (Exception e) {
            }
        }
        if (mSocket.isConnected() || !mSocket.isClosed()) {
            try {
                mSocket.close();
            } catch (Exception e) {
            }
        }
        out = null;
        in = null;
        mSocket = null;
    }

    public void read(Receiver receiver) throws Exception {
        if (in != null) {
            byte[] buffer = new byte[MAX_SIZE];
            byte[] tmpBuffer;
            int len;
            while ((len = in.read(buffer)) > 0) {
                tmpBuffer = new byte[len];
                System.arraycopy(buffer, 0, tmpBuffer, 0, len);
                receiver.receive(tmpBuffer);
            }
        }
    }

    public void write(byte[] buffer) throws Exception {

            out.write(buffer);
            out.flush();

    }



}
