package com.bailv.smartdormitory;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.bailv.util.SerialCmd;
import com.bailv.util.SerialThread;
import com.friendlyarm.AndroidSDK.HardwareControler;

import de.greenrobot.event.EventBus;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 
 * ���ں�̨����
 * @author:	����
 * @time:	2015��5��31��	����6:49:02
 */
public class SerialService extends Service implements Observer{

	private SerialThread serialThread;
	private LocalBinder binder = null;
	private Thread thread = null;
	
	private int fd;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO �Զ����ɵķ������

		return binder;
	}

	@Override
	public void onCreate() {
		// TODO �Զ����ɵķ������

		binder = new LocalBinder();
		
		serialThread = SerialThread.getSerialThread(SerialThread.devName[0],
				115200, 8, 1);
		fd = serialThread.getfd();
		thread = new Thread(serialThread);
		serialThread.addObserver(this);
		Log.d("Service", "opening...");
		thread.start();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO �Զ����ɵķ������
		serialThread.close();
		super.onDestroy();
	}

	public class LocalBinder extends Binder {

		SerialService getService() {
			return SerialService.this;
		}

	}

	public int getFd() {
		return serialThread.getfd();
	}

	public void SerialSend(String cmd){
		HardwareControler.write(fd, cmd.getBytes());
	}
	
	public void HandlerCmd(String cmd){
		EventBus.getDefault().post(new SerialCmd(cmd));
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// TODO �Զ����ɵķ������
		String serialCmd = null;
		SerialThread sThread = (SerialThread)observable;
		serialCmd = sThread.getCmd();
		Log.d("SerialGet",serialCmd);
		HandlerCmd(serialCmd);
	}

}
