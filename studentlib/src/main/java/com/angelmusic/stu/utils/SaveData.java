package com.angelmusic.stu.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SaveData {

	public static void SaveDataToTxt(String str) {
		String sdDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/wifiLog";
		String fileName = "Usb_connect_Log.txt";
		try {
			File filePath = new File(sdDir);
			File file = new File(sdDir + fileName);
			// if file doesnt exists, then create it
			if (!filePath.exists()) {
				Log.e("SaveData", "保存时文件异常-->");
				boolean s = filePath.mkdir();
				Log.e("SaveData", "创建目录-->" + s);
				// file.createNewFile();
			}
			if (!file.exists()) {
				// file.mkdir();
				boolean createNewFile = file.createNewFile();
				Log.e("SaveData", "创建文件-->" + createNewFile);
			}
			RandomAccessFile rf = new RandomAccessFile(sdDir + fileName, "rw");

			// 定义一个类RandomAccessFile的对象，并实例化
			rf.seek(rf.length());// 将指针移动到文件末尾
			rf.writeBytes("\r\n" + str);
			rf.close();// 关闭文件流
			// System.out.println("写入文件内容为：<br>");
			// FileReader fr = new FileReader(path + "\\WriteData.txt");
			// BufferedReader br = new BufferedReader(fr);// 读取文件的BufferedRead对象
			// String Line = br.readLine();
			// while (Line != null) {
			// System.out.println(Line + "<br>");
			// Line = br.readLine();
			// }
			// fr.close();// 关闭文件
		} catch (IOException e) {
			// TODO Auto-generated catch block

			Log.e("SaveData", "保存时文件异常-->" + e.toString());
			e.printStackTrace();
		}
	}

	// 三个参数
	// 第一个是文件名字
	// 第二个是文件存放的目录
	// 第三个是文件内容
	public static void writeToSDcardFile(String szOutText) {
		String destDirStr = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/wifiLog";
		String file = "wifiLog_piano_le.txt";
		// 获取扩展SD卡设备状态
		String sDStateString = Environment.getExternalStorageState();

		File myFile = null;
		// 拥有可读可写权限
		if (sDStateString.equals(Environment.MEDIA_MOUNTED)) {

			try {

				// 获取扩展存储设备的文件目录
				File SDFile = Environment
						.getExternalStorageDirectory();

				File destDir = new File(destDirStr);// 文件目录
				if (!destDir.exists()) {
					boolean s = destDir.mkdir();
					Log.e("SaveData", "创建目录-->" + s);
					// file.createNewFile();
				}

				// 打开文件
				myFile = new File(destDir + File.separator + file);

				// 判断文件是否存在,不存在则创建
				if (!myFile.exists()) {
					boolean createNewFile = myFile.createNewFile();
					Log.e("SaveData", "创建文件-->" + createNewFile);
				}

				// 写数据 注意这里，两个参数，第一个是写入的文件，第二个是指是覆盖还是追加，
				// 默认是覆盖的，就是不写第二个参数，这里设置为true就是说不覆盖，是在后面追加。
				FileOutputStream outputStream = new FileOutputStream(myFile,
						true);
				outputStream.write(szOutText.getBytes());// 写入内容
				outputStream.close();// 关闭流

			} catch (Exception e) {
				// TODO: handle exception
				e.getStackTrace();
			}

		}
	}
}
