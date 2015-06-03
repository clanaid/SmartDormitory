package com.bailv.smartdormitory;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.bailv.util.SerialCmd;
import com.bailv.util.SerialThread;
import com.bailv.util.ToastShow;
import com.friendlyarm.AndroidSDK.HardwareControler;

import de.greenrobot.event.EventBus;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 
 * 串口后台服务
 * 
 * @author: 泓钦
 * @time: 2015年5月31日 下午6:49:02
 */
public class SerialService extends Service implements Observer {

	private SerialThread serialThread;
	private LocalBinder binder = null;
	private Thread thread = null;

	private int fd = 0;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return binder;
	}

	@Override
	public void onCreate() {
		// TODO 自动生成的方法存根

		binder = new LocalBinder();

		serialThread = SerialThread.getSerialThread(SerialThread.devName[0],
				115200, 8, 1);
		thread = new Thread(serialThread);
		Log.d("Service", "opening...");
		if (serialThread != null) {
			serialThread.addObserver(this);
			fd = serialThread.getfd();
			thread.start();
			SerialSend("{000000000}");
		}else {
			ToastShow.MakeText(getApplicationContext(), "串口启动失败", true).show();
		}
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		if (serialThread != null) {
			serialThread.close();
		}
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

	public void SerialSend(String cmd) {
		if (fd != 0)
			HardwareControler.write(fd, cmd.getBytes());
	}

	public void HandlerCmd(String cmd) {
		EventBus.getDefault().post(new SerialCmd(cmd));
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO 自动生成的方法存根
		String serialCmd = null;
		serialCmd = (String) data;
		Log.d("SerialGet", serialCmd);
		HandlerCmd(serialCmd);
	}

}
