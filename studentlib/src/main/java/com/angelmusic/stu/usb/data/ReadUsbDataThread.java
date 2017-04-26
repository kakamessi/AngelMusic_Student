package com.angelmusic.stu.usb.data;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

import com.angelmusic.stu.bean.UnityInterface;
import com.angelmusic.stu.usb.callback.CallbackInterface;
import com.angelmusic.stu.utils.Log;
import com.angelmusic.stu.utils.SendDataUtil;

/**
 * @author huzhikun
 * @version 1.0 接收数据的线程
 */
public class ReadUsbDataThread extends Thread {
	private String TAG = "ReadUsbDataThread";
	private UsbDeviceConnection conn;// 设备连接，返回的对象，用于数据的传输接受

	private CallbackInterface callbackInterface;// 用于调试时候显示数据的一个接受和发送的回调

	private UsbEndpoint epBulkIn;// 数据接口的接受节点
	private boolean isRead = true;

	public ReadUsbDataThread(UsbDeviceConnection conn, UsbEndpoint epBulkIn,
			CallbackInterface callbackInterface) {
		super();
		this.conn = conn;
		this.epBulkIn = epBulkIn;
		this.callbackInterface = callbackInterface;
	}

	public void colse() {
		isRead = false;
		interrupt();
	}

	@Override
	public void run() {
		super.run();
		while (isRead) {
			_receiveMessageFormPiano();
			try {
				Thread.sleep(1);// 用于释放资源
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从设备接收数据bulkIn
	 * 
	 * */
	public void _receiveMessageFormPiano() {
		byte[] buffer = new byte[8];
		try {
			if (conn == null || epBulkIn == null) {
				Log.i(TAG, "===conn==null||epBulkIn==null===");
				return;
			}
			// bulkTransfer方法返回值判断是否操作成功
			if (conn.bulkTransfer(epBulkIn, buffer, buffer.length, 2000) < 0) {
				Log.i(TAG, "===Receive Message Fail===");

			} else {
				Log.i(TAG, "===Receive Message Success!===");
				StringBuffer buffer2 = new StringBuffer(1 + "=");
				for (int i = 0; i < buffer.length; i++) {

					buffer2.append(String.format("%x", buffer[i]) + " ");
				}
				Log.i(TAG, "===data===" + buffer2.toString());
				if (callbackInterface != null) {
					callbackInterface.onReadCallback(buffer2.toString());
				}
				SendDataUtil.sendDataToUnity(UnityInterface.cameraName,
						UnityInterface.sndDataAddress, buffer2.toString());
			}
		} catch (Exception e) {
			Log.i(TAG, "===Exception!===" + e.toString());
		}
	}
}
