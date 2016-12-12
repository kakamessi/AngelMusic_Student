package com.angelmusic.stu.network.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by DELL on 2016/11/11.
 */

public class FileUtil {


    public static void deleteLogFile() {

        String destDirStr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wifiLog/";

        String files = "wifiLog_piano_le.txt";

        File file = new File(destDirStr + files);

        if (file.exists()) {
            boolean isDele = file.delete();
            Log.e("kaka", "删除文件-->" + isDele);
        }

    }


}
