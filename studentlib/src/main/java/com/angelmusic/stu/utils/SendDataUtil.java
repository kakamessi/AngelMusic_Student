package com.angelmusic.stu.utils;

import android.text.TextUtils;

import com.unity3d.player.UnityPlayer;

public class SendDataUtil {

	public static boolean isOpenUnity = true;// 切换测试和sdk'状态的标志
	private static String TAG = "SendDataUtil";

	public static void sendDataToUnity(String cameraName, String methodName,
			String content) {
		if (!isOpenUnity)
			return;
		if (cameraName != null && !TextUtils.isEmpty(cameraName)
				&& methodName != null && !TextUtils.isEmpty(methodName)) {

			UnityPlayer.UnitySendMessage(cameraName, methodName, content);
		} else {
			android.util.Log.e(TAG, "没有传相机名字或是方法名");
		}
	}

}
