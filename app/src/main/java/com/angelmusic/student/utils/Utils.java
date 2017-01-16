package com.angelmusic.student.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * Created by DELL on 2017/1/16.
 */

public class Utils {


    /**
     * 获取本机ip
     * @param con
     * @return
     */
    public static String getLocalIp(Context con){
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) con.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        return intToIp(ipAddress);
    }

    private static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    public static String getDeviceId(Context con){

        TelephonyManager tm = (TelephonyManager)con.getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();

        return DEVICE_ID;
    }


}
