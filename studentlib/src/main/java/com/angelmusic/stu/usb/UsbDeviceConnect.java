package com.angelmusic.stu.usb;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

import com.angelmusic.stu.bean.UnityInterface;
import com.angelmusic.stu.usb.callback.CallbackInterface;
import com.angelmusic.stu.usb.data.ReadUsbDataThread;
import com.angelmusic.stu.usb.data.SendUsbDataThread;
import com.angelmusic.stu.utils.Log;
import com.angelmusic.stu.utils.SendDataUtil;

/**
 * @author huzhikun
 * @version 1.0 usb设备连接与数据的接受发送
 */

public class UsbDeviceConnect {
	protected static final String ACTION_USB_PERMISSION = "com.Aries.usbhosttest.USB_PERMISSION";

	private String TAG = "UsbConnect";
	private Context context;
	private UsbDeviceConnection conn;// 设备连接，返回的对象，用于数据的传输接受
	private UsbInterface usbInterface;// 数据接口
	private UsbEndpoint epBulkIn;// 数据接口的接受节点
	private UsbEndpoint epBulkOut;// 数据接口的发送节点

	private static CallbackInterface callbackInterface;// 用于调试时候显示数据的一个接受和发送的回调

	private ReadUsbDataThread readUsbDataThread;
	private SendUsbDataThread sendUsbDataThread;

	public static void setCallbackInterface(CallbackInterface callback) {
		callbackInterface = callback;
	}

	public UsbDeviceConnect(Context context, UsbManager myUsbManager,
			UsbDevice device) {
		super();
		this.context = context;
		_getDeviceInterface(device);
		_getAssignEndpoint(usbInterface);
	}

	/**
	 * 设备有变更清除数据
	 * 
	 * */
	public void colse() {
		stopDataThread();
		if (conn != null) {
			conn.close();
		}
	}

	private void stopDataThread() {
		if (readUsbDataThread != null) {
			readUsbDataThread.colse();
			readUsbDataThread = null;
		}
		if (sendUsbDataThread != null) {
			sendUsbDataThread.colse();
			sendUsbDataThread = null;
		}
	}

	/**
	 * @param byte[] bs 发送数据，将要发送的数据添加到队列等待发送
	 */
	public void setData(byte[] bs) {
		if (sendUsbDataThread != null) {
			sendUsbDataThread.sendData(bs);
		}
	}

	/**
	 * 获取设备接口列表（此设备接口id:为0是管理接口，为1是数据传输接口），目前只处理数据传输接口
	 * 
	 * */
	public boolean _getDeviceInterface(UsbDevice device) {
		if (device == null) {
			Log.i(TAG, "device==null");
			return false;
		}
		Log.i(TAG, "==InterfaceCounts : " + device.getInterfaceCount() + "===");
		for (int i = 0; i < device.getInterfaceCount(); i++) {
			UsbInterface intf = device.getInterface(i);

			Log.i(TAG, "intf-->" + intf.toString());
			// if (i == 0) {
			// interfaces.add(intf); // 保存设备接口
			// 成功获得设备接口
			Log.i(TAG, "===Get DeviceInterface  Success:" + intf.getId()
					+ "===");
			if (intf.getId() == 1) {
				usbInterface = intf;
				return true;
			}
		}
		return false;
	}

	/**
	 * 分配端点，IN | OUT，即输入输出；可以通过判断
	 * 
	 * */
	public boolean _getAssignEndpoint(UsbInterface interface1) {
		if (interface1 == null) {
			Log.i(TAG, "interface1==null");
			return false;
		}
		for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
			UsbEndpoint ep = usbInterface.getEndpoint(i);
			Log.i(TAG, "==ep.getType()--：" + ep.getType());
			// 根据设备的方向属性判断是出还是进
			if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
				epBulkOut = ep;
				Log.i(TAG, "===Find the BulkEndpointOut," + "index:" + i + "\n"
						+ "使用端点号：" + ep.getEndpointNumber() + "===");
			} else if (ep.getDirection() == UsbConstants.USB_DIR_IN) {
				epBulkIn = ep;
				Log.i(TAG, "===Find the BulkEndpointIn:" + "index:" + i + ","
						+ "使用端点号：" + ep.getEndpointNumber() + "===");
			}
			if (epBulkOut != null && epBulkIn != null) {

				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否获取打开设备的权限，为打开设备做做准备
	 * 
	 * */
	public void _openDevice(UsbDevice device, UsbManager myUsbManager) {
		// MyCode
		// MyCode End
		if (usbInterface != null) {
			// 在open前判断是否有连接权限；对于连接权限可以静态分配，也可以动态分配权限
			if (!myUsbManager.hasPermission(device)) {

				PendingIntent mPermissionIntent = PendingIntent.getBroadcast(
						context, 0, new Intent(ACTION_USB_PERMISSION), 0);
				myUsbManager.requestPermission(device, mPermissionIntent);
			} else {
				boolean isconnect = connectDevice(device, myUsbManager);
				// 发送状态到unity
				SendDataUtil.sendDataToUnity(UnityInterface.cameraName,
						UnityInterface.sendStatusAddress,
						isconnect ? "link-success" : "link-fail");
			}
		} else {

			Log.d(TAG, "mInterface==null");
		}
	}

	/**
	 * 连接设备，接受数据
	 * 
	 * */
	public boolean connectDevice(UsbDevice device, UsbManager myUsbManager) {
		conn = myUsbManager.openDevice(device);
		if (conn == null) {
			return false;
		}

		if (conn.claimInterface(usbInterface, true)) {
			Log.i(TAG, "===Open Device Success!===");
			stopDataThread();
			// 创建接收数据线程
			readUsbDataThread = new ReadUsbDataThread(conn, epBulkIn,
					callbackInterface);
			readUsbDataThread.start();
			// 创建发送数据线程
			sendUsbDataThread = new SendUsbDataThread(epBulkOut, conn,
					callbackInterface);
			sendUsbDataThread.start();
			return true;
		}

		return false;
	}

}
