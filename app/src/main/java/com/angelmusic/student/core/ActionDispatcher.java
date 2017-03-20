package com.angelmusic.student.core;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.angelmusic.student.base.App;
import com.angelmusic.student.interfaces.IActionDispatcher;

import java.util.HashMap;

import static android.R.attr.tag;
import static android.R.attr.value;

/**
 * Created by DELL on 2016/12/6.
 */

public class ActionDispatcher implements IActionDispatcher {

    private HashMap<String,Handler> mapHandler = new HashMap<>();

    private static ActionDispatcher instance;
    private ActionDispatcher (){}
    public static synchronized ActionDispatcher getInstance() {
        if (instance == null) {
            instance = new ActionDispatcher();
        }
        return instance;
    }


    @Override
    public void dispatch(String actionType) {

        String[] str = actionType.split("\\|");
        String action = str[0];

        for (String key : mapHandler.keySet()) {
            Log.e("angel_music", "Key = " + key);
            if("".equals(action) && key.equals(App.class.getSimpleName())){
                sendHandlerMsg(mapHandler.get(App.class.getSimpleName()),actionType);
                return;
            }
        }

        for (Handler value : mapHandler.values()) {
            sendHandlerMsg(value,actionType);
        }

    }

    private void sendHandlerMsg(Handler hander,String msg){
        Message msg = Message.obtain();
        msg.obj = msg;
        hander.sendMessage(msg);
    }

    @Override
    public void register(String tag, Handler handler) {

        mapHandler.put(tag,handler);
    }

    @Override
    public void remove(String tag) {

        mapHandler.remove(tag);
    }


    @Override
    public void reset() {

        mapHandler.clear();
    }




}



