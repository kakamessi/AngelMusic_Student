package com.angelmusic.student.core;

import com.angelmusic.student.base.App;
import com.angelmusic.student.utils.Utils;

/**
 * Created by DELL on 2017/1/13.
 */

public class ActionType {


    public static String getMsg(){

        String result = "";

        result = "1|" + Utils.getDeviceId(App.getApplication()) + "@" + Utils.getLocalIp(App.getApplication());

        return result;
    }

}
