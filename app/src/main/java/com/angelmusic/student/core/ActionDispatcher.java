package com.angelmusic.student.core;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.angelmusic.student.base.App;
import com.angelmusic.student.interfaces.IActionDispatcher;

import java.util.HashMap;

import static android.R.attr.key;
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

//        for (String key : mapHandler.keySet()) {
//            Log.e("angel_music", "Key = " + key);
//            if((ActionType.ACTION_MUTE.equals(action) && key.equals(App.class.getSimpleName()))
//                    || (ActionType.ACTION_RSP_ONLINE.equals(action) && key.equals(App.class.getSimpleName())) ){
//
//                Toast.makeText(App.getApplication(),"APp Msg",0).show();
//                sendHandlerMsg(mapHandler.get(App.class.getSimpleName()),actionType);
//                return;
//            }
//        }

        if(ActionType.ACTION_MUTE.equals(action)
                || ActionType.ACTION_RSP_ONLINE.equals(action) ){

            Handler mh = mapHandler.get(App.TAG);
            if(mh!=null) {
                sendHandlerMsg(mh, actionType);
            }
            return;
        }

        for (Handler value : mapHandler.values()) {
            sendHandlerMsg(value,actionType);
        }

    }

    private void sendHandlerMsg(Handler hander,String str){
        Message msg = Message.obtain();
        msg.obj = str;
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



