package com.angelmusic.stu.server.socket.tcp;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;

import com.angelmusic.stu.server.receiver.AndroidReceiver;
import com.angelmusic.stu.server.receiver.DefaultReceiver;
import com.angelmusic.stu.server.socket.constant.Constant;
import com.angelmusic.stu.server.socket.udp.UDPServerRecThread;

import java.net.ServerSocket;

/**
 * Created by DELL on 2016/10/25.
 */

public class TCPServerClient{

    /**
     * 服务端socket
     */
    private ServerSocket serverSocket = null;

    /**
     *
     * 监听socket线程
     */
    public AcpRuner ar ;

    /**
     *
     * 发送消息handler
     */
    private SendHandler mSendHander;



    /**
     * 初始化开启服务端
     */
    public void initServer(){

        mSendHander = new SendHandler();

        ar = new AcpRuner(new DefaultReceiver(),serverSocket);
        Thread th = new Thread(ar);
        th.setName("AcpThread_kaka");
        th.start();

        //启动udp监听
        new UDPServerRecThread(null).start();

    }


    /**
     *
     * 关闭服务端
     */
    public void close(){

        mSendHander.quit();

        ar.quit();

    }

    /**
     *  ___发送消息
     */
    public void sendMsg(String str){

        for (Ssocket s : ar.getSocketList()) {

            mSendHander.sendMsg(s,(str+ Constant.CONSTANT_HEARTBEAT).getBytes());

        }

    }

    /**
     *  ___发送消息
     */
    public void sendMsg(byte[] bytes){

            for (Ssocket s : ar.getSocketList()) {

                 mSendHander.sendMsg(s,bytes);

            }

    }

    /**
     * ___给个人发送消息
     */
    public void sendPersonalMsg(String ip, String str){

        for (Ssocket s : ar.getSocketList()) {

            if(ip.equals(s.mSocket.getInetAddress().getHostAddress())) {
                mSendHander.sendMsg(s, (str+Constant.CONSTANT_HEARTBEAT).getBytes());
                break;
            }
        }

    }

    /**
     * ___给个人发送消息
     */
    public void sendPersonalMsg(String ip, byte[] bytes){

        for (Ssocket s : ar.getSocketList()) {

            if(ip.equals(s.mSocket.getInetAddress().getHostAddress())) {
                mSendHander.sendMsg(s, bytes);
                break;
            }
        }

    }







    //----------------------------------------------------------------------------------------------------------------


    public void initAndroidServer(Handler handler, Context context){

        mSendHander = new SendHandler();

        ar = new AcpRuner(new AndroidReceiver(handler),serverSocket);
        Thread th = new Thread(ar);
        th.setName("AcpThread___________kaka");
        th.start();

    }

    public void testSendMsg() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                boolean isF = true;
                while(true){

                    try {
                        if(!isF) {
                            Thread.sleep(80);
                        }else{
                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    sendMsg("022|dsdf");

                    isF = false;
                }

            }
        }).start();

    }

    /**
     * 获取本机ip
     * @param con
     * @return
     */
    public String getLocalIp(Context con){
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) con.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        return intToIp(ipAddress);
    }

    private String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    /**
     * 跳转网页
     *
     */
    public void openWebView(Context con, String url){

        Intent intent = new Intent();
        intent.putExtra("URL",url);
        con.startActivity(intent);

    }



}
