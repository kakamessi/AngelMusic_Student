package com.angelmusic.stu.usb;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.angelmusic.stu.utils.Log;
import com.angelmusic.stu.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author huzhikun
 * @version 1.0 usb设备信息
 */
public class UsbDeviceInfo {
	private Context context;
	private UsbManager myUsbManager;// usb管理类
	private String TAG = "UsbDeviceInfo";
	// 设备对象
	private UsbDevice device;
	private UsbDeviceConnect deviceConnect;// usb设备连接的类
	private static UsbDeviceInfo deviceInfo;// 单例的实例

	private UsbDeviceInfo(Context context) {
		super();
		this.context = context;
		myUsbManager = (UsbManager) context
				.getSystemService(Context.USB_SERVICE); // UsbManager
	}

	public static UsbDeviceInfo getUsbDeviceInfo(Context context) {
		if (deviceInfo == null) {
			synchronized (UsbDeviceInfo.class) {
				if (deviceInfo == null) {
					deviceInfo = new UsbDeviceInfo(context);
				}
			}
		}
		return deviceInfo;
	}

	/**
	 * 用于更新当前的列表设备
	 * 
	 * @return boolean
	 * */
	public boolean update() {
		UsbDevice usbDevice = _getDevice();
		// 通过当前的设备对象device和重新获取的设备对象usbDevice，进行判断比较，看是否需要更新设备信息
		if (usbDevice == null && device == null) {// 没有设备插入
			return false;
		} else if (usbDevice != null && device != null) {// 更换设备
			if (usbDevice.getDeviceId() == device.getDeviceId()) {
				return false;
			} else {
				colse();
				device = usbDevice;
				return true;
			}
		} else if (usbDevice != null) {// 有一个设备插入
			device = usbDevice;
			return true;
		} else {// 设备拔出
			colse();
			return true;
		}

	}

	/**
	 * 获取列表显示内容(将设备对象转换成String)
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getShowContent() {
		ArrayList<String> strings = new ArrayList<String>();
		if (device != null) {

			int VendorID = device.getVendorId();
			int ProductID = device.getProductId();
			int deviceID = device.getDeviceId();
			String deviceName = device.getDeviceName();
			String productName = device.getDeviceName();
			strings.add("设备名:" + deviceName + "\n供应商ID:" + deviceID
					+ "\n供应商ID:" + VendorID + "\n产品名:" + productName
					+ "\n产品ID:" + ProductID);
		}
		return strings;

	}

	/**
	 * 获取设备对象
	 * 
	 * @return UsbDevice
	 */
	public UsbDevice _getDevice() {
		// ArrayList<UsbDevice> usbDevices = new ArrayList<UsbDevice>();
		// 开始枚举设备
		Log.i(TAG, "===Start Get Device List===");
		if (myUsbManager == null) {
			// 创建UsbManager失败，请重启程序
			Log.e(TAG,
					"===Create UsbManager Fail , Please Restart Application!===");
			return null;
		} else {
			HashMap<String, UsbDevice> deviceList = myUsbManager
					.getDeviceList();

			// Util.toast(context, (deviceList.size()) + "");
			// devices.clear();
			if (!(deviceList.isEmpty())) {
				// deviceList不为空
				Log.i(TAG, "===DeviceList Is Not Null!===");

				Iterator<UsbDevice> deviceIterator = deviceList.values()
						.iterator();
				while (deviceIterator.hasNext()) {
					UsbDevice usbDevice = deviceIterator.next();
					// usbDevices.add(device);
					Log.i(TAG, "device-->" + usbDevice.toString());
					// Util.toast(context, "发现待匹配设备");
					return usbDevice;
					// }
				}
			} else {
				// 请连接USB设备
				Log.e(TAG, "===Please Link USB Device===");
				Util.toast(context, "请连接USB设备至PAD！");
			}
		}
		return null;
	}

	/**
	 * 连接打开usb设备
	 * 
	 * @return ArrayList<String>
	 */
	public void connect() {
		if (deviceConnect != null) {
			Log.e(TAG, "===设备已连接===");
		} else {
			if (device != null) {
				deviceConnect = new UsbDeviceConnect(context, myUsbManager,
						device);
				deviceConnect._openDevice(device, myUsbManager);
			} else {
				Log.i(TAG, "===没有找到要连接的设备===");
			}
		}
	}

	/**
	 * 获取设备的连接对象UsbDeviceConnection
	 * 
	 * @return boolean
	 */
	public boolean getUsbDeviceConnection() {
		if (deviceConnect == null) {
			Log.e(TAG, "==deviceConnect==null===");
			return false;
		}
		return deviceConnect.connectDevice(device, myUsbManager);
	}

	/**
	 * @param byte[] bs 发送数据，将要发送的数据添加到队列等待发送
	 */
	public void setData(byte[] bs) {
		if (deviceConnect != null) {
			deviceConnect.setData(bs);
		}
	}

	/**
	 * 清除数据到初始化状态
	 * 
	 */
	public void colse() {
		stopConnect();
		device = null;
	}

	public void stopConnect() {
		if (deviceConnect != null) {
			deviceConnect.colse();
			deviceConnect = null;
		}
	}
}
