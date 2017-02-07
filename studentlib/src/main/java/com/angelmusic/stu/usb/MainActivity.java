package com.angelmusic.stu.usb;

import android.os.Bundle;
import android.view.KeyEvent;

import com.angelmusic.stu.bean.UnityInterface;

public class MainActivity extends UnityInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // if (isUpdate) {
        // if (UsbDeviceInfo.getUsbDeviceInfo(this).update()) {
        //
        // onUpdate();
        // UsbDeviceInfo.getUsbDeviceInfo(this).connect();
        // }
        // }
    }

    private boolean isUpdate;

    @Override
    public boolean onKeyDown(int arg0, KeyEvent arg1) {
        // TODO Auto-generated method stub
        if (arg0 == KeyEvent.KEYCODE_HOME) {
            isUpdate = true;

        }

        return super.onKeyDown(arg0, arg1);
    }

}
