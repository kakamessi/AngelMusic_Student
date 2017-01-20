package com.angelmusic.student.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.angelmusic.stu.network.u3d.AndroidDispatcher;
import com.angelmusic.student.constant.Constant;
import com.angelmusic.student.core.MsgReceiver;
import com.angelmusic.student.core.UDPRec1Thread;

/**
 * Created by DELL on 2016/12/6.
 */

public class StudentService extends Service {

    UDPRec1Thread udpRec = null;

    @Override
    public void onCreate() {
        super.onCreate();

        //AndroidDispatcher.getInstance().init(Constant.HOST, Constant.PORT, new MsgReceiver());
        udpRec = new UDPRec1Thread(null);
        udpRec.start();

        udpRec.sendSycMsg();

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}




