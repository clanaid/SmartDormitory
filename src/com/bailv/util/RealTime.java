package com.bailv.util;

import android.os.Handler;
import android.os.Message;

public class RealTime implements Runnable {

	public static final int msgKey1 = 0;

	private Handler handler = null;

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	private static RealTime instance = null;

	public static synchronized RealTime getRealTime() {
		if (instance == null) {
			instance = new RealTime();
		}
		return instance;
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		do {
			try {
				Thread.sleep(1000);
				Message msg = new Message();
				msg.what = msgKey1;
				if (handler != null)
					handler.sendMessage(msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

}
