package com.bailv.smartdormitory;

import java.io.IOException;

import com.bailv.util.SerialThread;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SerialService extends Service {

	private SerialThread serialThread;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO 自动生成的方法存根
		super.onCreate();
		try {
			serialThread = new SerialThread(SerialThread.devName[0], 9600, 8, 1);
			new Thread(serialThread).start();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
	}

}
