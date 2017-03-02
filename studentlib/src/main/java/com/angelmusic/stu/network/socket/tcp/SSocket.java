package com.angelmusic.stu.network.socket.tcp;


import com.angelmusic.stu.utils.Log;

import com.angelmusic.stu.network.socket.AbsReceiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


public class SSocket {

    private Socket mSocket;
    private OutputStream out;
    private InputStream in;

    private DataInputStream dins = null;
    private DataOutputStream dos = null;

    private int MAX_SIZE = 1024 * 3;

    public SSocket(){
    }

    public void connect(String host, int port) throws Exception {
        mSocket = new Socket();
//        mSocket.setTcpNoDelay(true);
//        mSocket.setReceiveBufferSize(MAX_SIZE);
//        mSocket.setSendBufferSize(MAX_SIZE);

        mSocket.connect(new InetSocketAddress(host, port));
        if (mSocket.isConnected()) {
            out = mSocket.getOutputStream();
            in = mSocket.getInputStream();

            dins = new DataInputStream(in);
            dos = new DataOutputStream(out);
        }
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
        dins = null;
        dos = null;
    }

    public void read(AbsReceiver receiver) throws Exception {
//        if (in!= null) {
//            byte[] buffer = new byte[MAX_SIZE];
//            byte[] tmpBuffer;
//            int len;
//            while ((len = in.read(buffer)) > 0) {
//                tmpBuffer = new byte[len];
//                System.arraycopy(buffer, 0, tmpBuffer, 0, len);
//                receiver.receive(tmpBuffer);
//            }
//        }

        while(true) {
            byte bb = dins.readByte();
            int totalLen = dins.readInt();

            byte[] data = new byte[totalLen - 4 - 1];
            dins.readFully(data);
            String msg = new String(data);
            receiver.receive(data);
            Log.e("SSocket","read______________________" + msg.length() + ": " + msg);
        }

    }

    public void write(byte[] buffer) throws Exception {

//      out.write(buffer);
//      out.flush();

        int totalLen = 1 + 4 + buffer.length;
        dos.writeByte(1);
        dos.writeInt(totalLen);
        dos.write(buffer);
        dos.flush();

    }

    public Socket getmSocket() {
        return mSocket;
    }
}
