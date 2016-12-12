package com.angelmusic.stu.network.socket.udp;

import com.angelmusic.stu.network.socket.AbsReceiver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

public class UDPSocket {

    private int dataLength = 1024 * 3;
    private DatagramSocket mSocket;
    private DatagramPacket mPacket;
    private AbsReceiver receiver;

    public UDPSocket(AbsReceiver receiver) throws SocketException {
        byte[] receiveData = new byte[dataLength];
        mPacket = new DatagramPacket(receiveData, dataLength);
        mSocket = new DatagramSocket();
        this.receiver = receiver;
    }

    public UDPSocket(AbsReceiver receiver, int port) throws SocketException {
        byte[] receiveData = new byte[dataLength];
        mPacket = new DatagramPacket(receiveData, dataLength);
        mSocket = new DatagramSocket(port);
        this.receiver = receiver;
    }

    public void receive() throws Exception {
        mSocket.receive(mPacket);
        if (receiver != null) {
            int len = mPacket.getLength();
            byte[] temp = new byte[len];
            System.arraycopy(mPacket.getData(), 0, temp, 0, len);
            receiver.receive(temp);
        }
    }

    public void send(SocketAddress address, byte[] buffer) throws Exception {
        if (buffer.length < dataLength) {
            DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, address);
            mSocket.send(sendPacket);
        } else {
            System.out.println("UDP发送数据包大于限定长度");
        }
    }

    public void close() {
        if (mSocket != null) {
            if (!mSocket.isClosed()) {
                mSocket.close();
            }
            if (mSocket.isConnected()) {
                mSocket.disconnect();
            }
        }
        mSocket = null;
    }

}
