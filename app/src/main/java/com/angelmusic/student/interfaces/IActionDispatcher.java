package com.angelmusic.student.interfaces;

import android.os.Handler;

/**
 * Created by DELL on 2016/12/6.
 */

public interface IActionDispatcher {

    void dispatch(String actionType);

    void register(String tag, Handler handler);

    void remove(String tag);

    void reset();

}
