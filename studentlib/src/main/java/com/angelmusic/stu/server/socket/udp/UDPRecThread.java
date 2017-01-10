package com.angelmusic.stu.server.socket.udp;


import com.angelmusic.stu.server.receiver.DefaultReceiver;
import com.angelmusic.stu.server.socket.constant.NetParams;


import java.net.DatagramPacket;


/**
 * Created by DELL on 2016/10/28.
 */

public class UDPRecThread extends Thread {

    private volatile boolean mOpenFlag;

    private UDPSocket mSocket;

    /**
     * 打开
     * 即可以上线
     */
    public boolean open() {
        // 线程只能start()一次，重启必须重新new。因此这里也只能open()一次
        try {

            this.mSocket = new UDPSocket(new DefaultReceiver(), NetParams.PORT);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.getState() != Thread.State.NEW) {
            return false;
        }

        mOpenFlag = true;
        this.start();
        return true;
    }

    @Override
    public void run() {

        if (mSocket == null) {
            return;
        }

        while (mOpenFlag) {
            try {
                // waiting for search from host
                mSocket.receive();
                // verify the data


            } catch (Exception e) {
            }
        }

        mSocket.close();

    }

    /**
     * 关闭
     */
    public void close() {
        mOpenFlag = false;
    }


    /**
     * 数据校对
     * @param pack
     * @return
     */
    private boolean verifySearchData(DatagramPacket pack) {

        return true;
    }


}
