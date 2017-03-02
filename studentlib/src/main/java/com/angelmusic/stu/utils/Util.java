package com.angelmusic.stu.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class Util {
	private static String TAG = "Util";
	private static boolean TOAST=true;
	
	public static void toast(Context context, String content) {
		if (TOAST)
			Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}
//	public static void sendToUnity(String cameraName, String methodName,
//			String srtData) {
//
//		UnityPlayer.UnitySendMessage(cameraName, methodName, srtData);
//	}

	/**
	 * 获取资源id String mess = getResources().getString(R.string.mess_1);
	 * defType(图片：drawable，控件：id，字符串：string等)
	 * */
	public static int getResID(Context context, String resName, String defType) {
		return context.getResources().getIdentifier(resName, defType,
				context.getPackageName());
	}

	/**
	 * 获取String资源 defType(字符串：string等)
	 * */
	public static String getStringRes(Context context, String resName) {
		try {
			int id = getResID(context, resName, "string");
			return context.getResources().getString(id);
		} catch (NotFoundException e) {
			Log.e(TAG, "没有找到String资源-->" + e.toString());
			return "";
		}
	}

	/**
	 * 获取Drawable资源 defType(字符串：drawable等)
	 * */
	public static Drawable getDrawableRes(Context context, String resName) {
		try {
			int id = getResID(context, resName, "drawable");
			return context.getResources().getDrawable(id);
		} catch (NotFoundException e) {
			Log.e(TAG, "没有找到图片资源-->" + e.toString());
			return null;
		}
	}

	/**
	 * 获取apk的sdk版本号 sdkVersion
	 * 
	 * @param ctx
	 * @return
	 */
	public static int getSdkVersion(Context ctx) {
		int sdkVersion = -1;
		PackageManager manager = ctx.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
			sdkVersion = info.applicationInfo.targetSdkVersion; // sdk最大版本
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch blockd
			e.printStackTrace();
		}
		return sdkVersion;
	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOPen(final Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}

		return false;
	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @param context
	 * @return true 表示开启
	 */
	public static boolean isGPSEnable(Context context) {
		@SuppressWarnings("deprecation")
		String str = Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

		if (str != null) {
			return str.contains("gps") || str.contains("network");
		} else {
			return false;
		}
	}

	public static String ToSBC(Context context, String resName, String defType) {
		String input = context.getResources().getString(
				getResID(context, resName, defType));
		if (input != null) {
			char c[] = input.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] == ' ') {
					c[i] = '\u3000';
				} else if (c[i] < '\177') {
					c[i] = (char) (c[i] + 65248);
				}
			}
			return new String(c);
		}
		return "没有获得字符串";
	}

	/**
	 * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
	 * @param context
	 * @return 平板返回 True，手机返回 False
	 */
	public static boolean isPad(Context context) {
		return (context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK)
				>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

}
