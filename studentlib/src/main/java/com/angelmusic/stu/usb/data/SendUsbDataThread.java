package com.angelmusic.stu.usb.data;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

import com.angelmusic.stu.usb.callback.CallbackInterface;
import com.angelmusic.stu.utils.Log;
import com.angelmusic.stu.utils.SendDataUtil;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author huzhikun
 * @version 1.0 发送数据的线程
 */
public class SendUsbDataThread extends Thread {

	//前期使用LinkedList   后期改为了LinkedBlockingQueue
	//private Queue<byte[]> queueBuffer = new LinkedList<byte[]>();// 信息的队列
	private BlockingQueue<byte[]> queueBuffer = new LinkedBlockingQueue<>();

	private UsbEndpoint epBulkOut;// 数据接口的发送节点
	private String TAG = "SendUsbDataThread";
	private UsbDeviceConnection conn;// 设备连接，返回的对象，用于数据的传输接受

	private CallbackInterface callbackInterface;// 用于调试时候显示数据的一个接受和发送的回调

	private boolean isSend=true;

	public SendUsbDataThread(UsbEndpoint epBulkOut, UsbDeviceConnection conn,
			CallbackInterface callbackInterface) {
		super();
		this.epBulkOut = epBulkOut;
		this.conn = conn;
		this.callbackInterface = callbackInterface;
	}
	public void colse() {
		isSend=false;
		interrupt();
	}
	@Override
	public void run() {
		super.run();
		while (isSend) {
			int length=queueBuffer.size();
			for (int i = 0; i < length; i++) {
				_sendMessageToPiano(queueBuffer.poll());// 在消息队列取出要发送的数据进行发送
				try {
					Thread.sleep(1);// 用于释放资源
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param byte[] bs 发送数据，将要发送的数据添加到队列等待发送
	 */
	public void sendData(byte[] bs) {
		queueBuffer.add(bs);
	}

	/**
	 * 数据发送的方法
	 * 
	 * @param byte[] buffer
	 * */
	public void _sendMessageToPiano(byte[] buffer) {
		boolean isSuccess = false;
		if (conn == null || epBulkOut == null) {
			Log.i(TAG, "==UsbDeviceConnection|| UsbEndpoint==null====");
		} else {
			if (buffer != null && buffer.length > 0) {

				// bulkTransfer方法返回值判断是否操作成功
				// bulkOut传输
				if (conn.bulkTransfer(epBulkOut, buffer, buffer.length, 0) < 0) {
					Log.i(TAG, "===Send Message Fail===");
					isSuccess = true;
				} else {
					Log.i(TAG, "===Send Message Success!===");
				}
			} else {
				Log.i(TAG, "===Send 的数据为空===");
			}
		}

		SendDataUtil.sendDataToUnity("Main Camera", "receiveData",
				isSuccess ? "发送成功" : "发送失败");
		if (callbackInterface != null) {
			callbackInterface.onSendCallback(isSuccess);
		}
	}

}
