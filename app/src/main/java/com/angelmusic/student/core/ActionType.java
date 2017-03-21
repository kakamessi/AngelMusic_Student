package com.angelmusic.student.core;

import com.angelmusic.student.base.App;
import com.angelmusic.student.utils.Utils;

/**
 * Created by DELL on 2017/1/13.
 */

public class ActionType {

    public static final String GET_PAD_CODE = "1";

    public static final String GET_PAD_NUM = "2";

    public static final String ACTION_PREPARE = "3";

    public static final String ACTION_PLAY = "4";

    public static final String ACTION_PAUSE_RESUME = "5";

    public static final String ACTION_GZ_ONE = "6";

    public static final String ACTION_LOGIN = "7";

    public static final String ACTION_GET_CLASS = "8";
    //上传成绩
    public static final String ACTION_POST_SCORE = "9";
    //在线确认消息
    public static final String ACTION_RSP_ONLINE = "10";
    //静音
    public static final String ACTION_MUTE = "11";

    public static String getMsg(){

        String result = "";

        result = "1|" + Utils.getDeviceId(App.getApplication()) + "@" + Utils.getLocalIp(App.getApplication());

        return result;
    }

}
