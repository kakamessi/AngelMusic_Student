package com.angelmusic.student.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.angelmusic.student.constant.Constant;

import java.io.File;

import static android.R.attr.path;

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

    public static String getDeviceId(Context context){

        String id;
        //android.telephony.TelephonyManager
        TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            id = mTelephony.getDeviceId();
        } else {
            //android.provider.Settings;
            id = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return id;

    }

    public static String getVideoPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FILE_PATH;
    }

    public static boolean isFileExist(String filename){
        boolean result = false;
        File file = new File(getVideoPath() + filename);
        if(file.exists()){
            result = true;
        }
        return result;
    }

    /**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
    public static void RecursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

}
