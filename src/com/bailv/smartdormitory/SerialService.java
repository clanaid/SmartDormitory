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
		// TODO �Զ����ɵķ������
		
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO �Զ����ɵķ������
		super.onCreate();
		try {
			serialThread = new SerialThread(SerialThread.devName[0], 9600, 8, 1);
			new Thread(serialThread).start();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
	}

}
