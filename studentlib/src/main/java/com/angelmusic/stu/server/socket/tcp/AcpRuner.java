package com.angelmusic.stu.server.socket.tcp;

import com.angelmusic.stu.server.receiver.Receiver;
import com.angelmusic.stu.server.socket.constant.NetParams;
import com.angelmusic.stu.utils.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by DELL on 2016/10/25.
 */

public class AcpRuner implements Runnable {

    private Receiver handler;

    private ServerSocket serverSocket;
    private  ArrayList<Ssocket> socketList = new ArrayList<>();


    public AcpRuner(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public AcpRuner(Receiver handler, ServerSocket serverSocket) {
        this.handler = handler;
        this.serverSocket = serverSocket;
    }

    public void quit(){

        for(Ssocket ss : socketList){
            ss.disconnect();
        }

        try {

            serverSocket.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        try {

            serverSocket = new ServerSocket(NetParams.PORT);

            while(true){
                Ssocket socket = new Ssocket(serverSocket.accept());
//                for(Ssocket ss : socketList){
//                    if(ss.mSocket.getInetAddress().getHostAddress().equals(socket.mSocket.getInetAddress().getHostAddress())){
//                        ss.disconnect();
//                        socketList.remove(ss);
//                    }
//                }
                for (Iterator<Ssocket> it = socketList.iterator(); it.hasNext();) {
                    Ssocket ss = it.next();
                    if (ss.mSocket.getInetAddress().getHostAddress().equals(socket.mSocket.getInetAddress().getHostAddress())) {
                        ss.disconnect();
                        it.remove();  // ok
                    }
                }
                socketList.add(socket);
                Thread th = new Thread(new RecRunner(handler,socket));
                th.setName("RecThread___________kaka" + socket.mSocket.getInetAddress().getHostAddress() + " : "+socket.mSocket.getPort());
                th.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{

            try {

                if(serverSocket!=null) {
                    serverSocket.close();
                }

            } catch (Exception e) {

            }
        }

    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ArrayList<Ssocket> getSocketList() {
        return socketList;
    }

}
