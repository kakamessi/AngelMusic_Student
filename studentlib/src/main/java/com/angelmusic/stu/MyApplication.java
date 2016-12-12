package com.angelmusic.stu;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.angelmusic.stu.utils.MyCrashHandler;

import java.io.File;

public class MyApplication extends Application {
	/**
	 * 整个(app)程序初始化之前被调用
	 * 
	 * @author Administrator
	 * 
	 */
	// public NoteEntry entry;
	@Override
	public void onCreate() {
		super.onCreate();
		dele();
		// 初始化捕捉异常的类
		MyCrashHandler handler = MyCrashHandler.getInstance();
		handler.init(getApplicationContext());
		Thread.setDefaultUncaughtExceptionHandler(handler);

	}

	String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/wifiLog";
	String fileName = "Usb_connect_Log.txt";

	public void dele() {
		File file = new File(sdDir + fileName);

		if (file.exists()) {
			// file.mkdir();
			boolean isDele = file.delete();
			Log.e("SaveData", "删除文件-->" + isDele);
		}
	}
}
