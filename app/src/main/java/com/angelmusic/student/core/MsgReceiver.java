package com.angelmusic.student.core;

import com.angelmusic.stu.network.model.ActionType;
import com.angelmusic.stu.network.socket.AbsReceiver;
import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.interfaces.IActionDispatcher;

/**
 * Created by DELL on 2016/12/6.
 */

public class MsgReceiver extends AbsReceiver {

    private IActionDispatcher ad;

    public MsgReceiver(){
        ad = ActionDispatcher.getInstance();
    }

    /**
     * 当获取网络数据回调接口
     *
     * @param buffer 字节数据
     */
    public void receive(byte[] buffer){

        String[] sourceStrArray = checkData(buffer);
        for (int i = 0; i < sourceStrArray.length; i++){
            //Log.e("kaka","receive teacher msg :::: " + sourceStrArray[i]);
            ad.dispatch(sourceStrArray[i]);
        }

    }

    private String[] checkData(byte[] buffer) {

        String[] sourceStrArray = new String(buffer).split(ActionType.CONSTANT_HEARTBEAT);

        return sourceStrArray;

    }


}
