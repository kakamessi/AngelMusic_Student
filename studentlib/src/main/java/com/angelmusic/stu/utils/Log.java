package com.angelmusic.stu.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log {
	// public static String log="";
	public static ArrayList<String> logs = new ArrayList<String>();
	public static boolean isLog = true;

	private static String getDate() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		// 通过毫秒数构造日期 即可将毫秒数转换为日期
		Date d = new Date();
		return format.format(d);
	}

	public static void saveE(String tag, String msg) {
		SaveData.writeToSDcardFile(getDate() + "\t" + tag + "\t" + msg + "\r\n");
	}

	public static void e(String tag, String msg) {
		android.util.Log.e(tag, msg);
		// log+=msg+"\n";
		// logs.add(msg);
		// SaveData.SaveDataToTxt(msg);
		if (isLog)
			SaveData.writeToSDcardFile(getDate() + "\r\n" + "\t\t" + tag
					+ "\t\t" + msg + "\r\n");
	}

	public static void w(String tag, String msg) {
		android.util.Log.w(tag, msg);
		// log+=msg+"\n";
		// logs.add(msg);
		if (isLog)
			SaveData.writeToSDcardFile(getDate() + "\r\n" + "\t\t" + tag
					+ "\t\t" + msg + "\r\n");
	}

	public static void d(String tag, String msg) {
		android.util.Log.d(tag, msg);
		// log+=msg+"\n";
		// logs.add(msg);
		if (isLog)
			SaveData.writeToSDcardFile(getDate() + "\r\n" + "\t\t" + tag
					+ "\t\t" + msg + "\r\n");
	}

	public static void i(String tag, String msg) {
		android.util.Log.i(tag, msg);
		// log+=msg+"\n";
		// logs.add(msg);
		if (isLog)
			SaveData.writeToSDcardFile(getDate() + "\r\n" + "\t\t" + tag
					+ "\t\t" + msg + "\r\n");
	}



	public static void kException(Exception e){

		if(isLog) {

			// StringWriter将包含堆栈信息
			StringWriter stringWriter = new StringWriter();
			//必须将StringWriter封装成PrintWriter对象，
			//以满足printStackTrace的要求
			PrintWriter printWriter = new PrintWriter(stringWriter);
			//获取堆栈信息
			e.printStackTrace(printWriter);
			//转换成String，并返回该String
			StringBuffer error = stringWriter.getBuffer();

		}
	}


}
