package com.angelmusic.stu.network.u3d;

import android.content.Context;

import com.angelmusic.stu.network.model.ActionType;
import com.angelmusic.stu.network.socket.AbsReceiver;
import com.angelmusic.stu.network.socket.ReConnectThread;
import com.angelmusic.stu.network.socket.tcp.SSocket;
import com.angelmusic.stu.network.socket.tcp.TCPRec;
import com.angelmusic.stu.network.socket.tcp.TCPWrite;
import com.angelmusic.stu.utils.Log;

/**
 *
 * Created by DELL on 2016/10/24.
 *
 * u3d控制入口
 *
 */

public class AndroidDispatcher implements IDispatcher{


    private String host;
    private int port;

    private SSocket socket;
    private TCPRec rec;
    private AbsReceiver receiver;
    private TCPWrite write;
    private ReConnectThread beatThread;

    private Context context;

    private static AndroidDispatcher instance;
    private AndroidDispatcher (){}
    public static synchronized AndroidDispatcher getInstance() {
        if (instance == null) {
            instance = new AndroidDispatcher();
            }
        return instance;
    }

    /**
     * 初始化底层通讯   u3d测试端
     */
    public void init(String host, int port, AbsReceiver mRever){

        this.host = host;
        this.port = port;
        this.receiver = mRever;

        //init
        socket = new SSocket();

        //read
        rec = new TCPRec(socket,host,port,receiver);
        new Thread(rec).start();

        //write
        write = new TCPWrite(socket);

        //beat
        beatThread = new ReConnectThread();
        //beatThread.start();

    }

    /**
     *
     * 退出通讯框架
     */
    public void quit(){

        if(beatThread!=null)
            beatThread.quit();

        if(write!=null)
            write.quit();

        if(socket!=null)
            socket.disconnect();

        socket = null;
        rec = null;
        write = null;

    }

    public void reConnect(boolean isLib) throws Exception {

//        rec = null;
//
//        socket.connect(host,port);
//        rec = new TCPRec(socket,host,port,receiver);
//
//        new Thread(rec).start();
    }

    public boolean isAlive(){

        return socket==null?false:socket.getmSocket().isConnected();

    }

    /**
     * 发送消息
     * @param bytes
     */
    public void sendMsg(byte[] bytes){

        if(write!=null) {
            write.sendMsg(bytes);
        }

    }

    /**
     * 发送消息
     * @param
     */
    public void sendMsg(String str){

        if(write!=null) {
            write.sendMsg((str + ActionType.CONSTANT_HEARTBEAT).getBytes());
        }

    }


    public void connectTeacher(String str,String host, int port, AbsReceiver mRever){

        try {
            socket.write((str + ActionType.CONSTANT_HEARTBEAT).getBytes());

        } catch (Exception e) {

            init(host,port,mRever);
            Log.e("kaka","--teacher ip--"+host);
            try {
                Thread.sleep(1500);
                socket.write((str + ActionType.CONSTANT_HEARTBEAT).getBytes());
            } catch (Exception e1) {

            }
        }
    }




    //---------------------------------------------------------------------------------------------------



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }






}
