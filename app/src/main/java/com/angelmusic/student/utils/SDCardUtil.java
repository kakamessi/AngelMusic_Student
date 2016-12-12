package com.angelmusic.student.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;


/**
 * Created by fei on 2016/12/2.
 * SD卡的工具类
 */

public class SDCardUtil {
    /**
     * SD卡是否可用
     */
    public static boolean isAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        if (isAvailable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator;
        }
        return "sd卡不可用";
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirPath() {
        return Environment.getRootDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获取SD卡剩余可用容量字节数，单位byte
     *
     * @return 容量字节 SD卡可用空间
     */
    public static long getSDAvailableSize() {
        if (isAvailable()) {
            StatFs fs = new StatFs(getSDCardPath());
            long count,size;
            if(Build.VERSION.SDK_INT>18){
                count = fs.getFreeBlocksLong();
                size = fs.getBlockSizeLong();
            }else{
                 count = fs.getFreeBlocks();
                 size = fs.getBlockSize();
            }
            return count * size;
        }
        return 0;
    }
}