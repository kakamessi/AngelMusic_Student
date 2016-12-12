package com.angelmusic.stu.bean;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.Toast;

import com.angelmusic.stu.usb.UsbDeviceInfo;
import com.angelmusic.stu.utils.Log;
import com.angelmusic.stu.utils.SendDataUtil;
import com.unity3d.player.UnityPlayerActivity;

public class UnityInterface extends UnityPlayerActivity {
	private final String TAG = "UnityInterface";
	private OnUpdateListener updateListener;
	// ------------------------------android发送数据到unity的地址（相机名，unity接收数据的方法名和状态数据的方法名）-------------------------------------------------
	public static String sndDataAddress;
	public static String sendStatusAddress;
	public static String cameraName;
	// -------------------------------------------------------------------------------
	protected static final String ACTION_USB_PERMISSION = "com.Aries.usbhosttest.USB_PERMISSION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		 SendDataUtil.isOpenUnity = false;// 是否发送数据到unity
//		Log.isLog = true;// 是否打印log
		// 设备连接时权限请求的监听，和usb连接的状态的监听
		IntentFilter filter = new IntentFilter();
		filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
		filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
		filter.addAction(ACTION_USB_PERMISSION);
		registerReceiver(mUsbReceiver, filter);
		updateDevice();
		connectDevice();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		UsbDeviceInfo.getUsbDeviceInfo(UnityInterface.this).colse();
	}


	/*--------------------------------------------------接口-------------------------------------------------------------------------------*/
	/**
	 * 设置发送数据的相机名
	 * */
	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	/**
	 * 设置发送数据的方法名
	 * */
	public void setSendDataAddress(String methodName) {
		sndDataAddress = methodName;
	}

	/**
	 * 设置发送状态的方法名
	 * */
	public void setSendStatusAddress(String methodName) {
		sendStatusAddress = methodName;
	}

	/**
	 * @param byte[] bs 发送数据，将要发送的数据添加到队列等待发送
	 */
	public void setData(byte[] bs) {
		UsbDeviceInfo.getUsbDeviceInfo(UnityInterface.this).setData(bs);
	}

	// 更新设备列表
	public boolean updateDevice() {

		return UsbDeviceInfo.getUsbDeviceInfo(UnityInterface.this).update();
	}

	// 获取设备列表
	public String getDevice() {

		return UsbDeviceInfo.getUsbDeviceInfo(UnityInterface.this)._getDevice()
				.toString();
	}

	// 连接设备
	public void connectDevice() {

		UsbDeviceInfo.getUsbDeviceInfo(UnityInterface.this).connect();
	}
	public void stopConnect() {
		UsbDeviceInfo.getUsbDeviceInfo(UnityInterface.this).stopConnect();
	}
	/*---------------------------------------------------------------------------------------------------------------------------------*/

	public interface OnUpdateListener {
		public void onUpdate();
	}
	public void setOnUpdateListener(OnUpdateListener updateListener) {
		this.updateListener = updateListener;
	}

	private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.i(TAG, "BroadcastReceiver-->" + action);
			String status = null;
			switch (action) {
			case UsbManager.ACTION_USB_DEVICE_ATTACHED:
				status = "usb-insert";
				Log.i(TAG, "检测到有USB插口接入-->" + action);
				Toast.makeText(context, "检测到有USB插口接入", Toast.LENGTH_SHORT)
						.show();
				updateDevice();
				connectDevice();
				// updateDeviceList();
				if (updateListener != null) {
					updateListener.onUpdate();
				}
				break;
			case UsbManager.ACTION_USB_DEVICE_DETACHED:
				status = "usb-discrete";
				Log.i(TAG, "USB线被拔出-->" + action);
				Toast.makeText(context, "USB线被拔出", Toast.LENGTH_SHORT).show();
				UsbDeviceInfo.getUsbDeviceInfo(UnityInterface.this).colse();
				if (updateListener != null) {
					updateListener.onUpdate();
				}
				// clear();
				break;
			case ACTION_USB_PERMISSION:

				boolean isconnect = false;
				// 判断用户点击的是取消还是确认
				if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED,
						false)) {
					Log.i(TAG, "连接权限被允许-->" + action);
					Toast.makeText(context, "连接权限被允许", Toast.LENGTH_SHORT)
							.show();
					isconnect = UsbDeviceInfo.getUsbDeviceInfo(
							UnityInterface.this).getUsbDeviceConnection();

					Log.d(TAG, "连接-->" + isconnect);
				} else {
					stopConnect();
					Log.i(TAG, "连接权限被取消-->" + action);
				}
				status = isconnect ? "link-success" : "link-fail";
				break;
			}
			if (status != null) {
				// 发送状态到unity
				SendDataUtil.sendDataToUnity(UnityInterface.cameraName,
						UnityInterface.sendStatusAddress, status);
			}
		}
	};
}
