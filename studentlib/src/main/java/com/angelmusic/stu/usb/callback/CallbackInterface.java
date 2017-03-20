package com.angelmusic.stu.usb.callback;

public interface CallbackInterface {

	public void onReadCallback(String str);

	public void onSendCallback(boolean isSend);

	public void onConnect(boolean isConnected);

}
