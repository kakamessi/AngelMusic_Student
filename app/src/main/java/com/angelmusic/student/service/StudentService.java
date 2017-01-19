package com.angelmusic.student.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.angelmusic.student.core.UDPRec1Thread;

/**
 * Created by DELL on 2016/12/6.
 */

public class StudentService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

        //AndroidDispatcher.getInstance().init(Constant.HOST, Constant.PORT, new MsgReceiver());
        new UDPRec1Thread(null).start();

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}




