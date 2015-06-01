package com.bailv.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Observable;
import java.util.Observer;





import com.friendlyarm.AndroidSDK.HardwareControler;

import android.R.integer;
import android.nfc.Tag;
import android.os.Handler;
import android.util.Log;

/**
 * 
 * 串口进程类
 * 
 * @author: 泓钦
 * @time: 2015年5月13日 下午8:44:45
 */

public class SerialThread extends Observable implements Runnable {

	private int fd;
	private String cmd;

	private boolean isStop = false;
	
	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}


	private static SerialThread instance = null;

	public static synchronized SerialThread getSerialThread(String devName,
			int baud, int dataBits, int stopBits) {
		if (instance == null) {
			try {
				instance = new SerialThread(devName, baud, dataBits, stopBits);
				Log.d("Serial","串口启动成功");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				Log.d("Serial","串口启动失败");
			}
		}
		return instance;
	}
	

	public static final String[] devName = { "/dev/ttyUSB0" };

	public int getfd() {
		return fd;
	}

	public String getCmd() {
		return cmd;
	}

	public void SetHandler() {
	}

	public SerialThread(String devName, long baud, int dataBits, int stopBits)
			throws IOException {
		Log.d("Serial","串口启动");
		fd = HardwareControler.openSerialPort(devName, baud, dataBits, stopBits);
		if (-1 == fd) {
			throw new IOException("启动串口错误");
		}
	}
	

	public SerialThread() throws IOException{
		// TODO 自动生成的构造函数存根
		fd = HardwareControler.openSerialPort("/dev/ttyUSB0", 115200, 8, 1);
		if(fd == -1){
			Log.d("Serial", "串口打开失败");
			throw new IOException();
		}
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		byte[] buffer = new byte[255];
		String serialMsg;
		StringBuilder serialMsgBuffer = new StringBuilder(12);
		while (!isStop) {
			if (HardwareControler.select(fd, 0, 1) == 1) {
				HardwareControler.read(fd, buffer, 255);
				serialMsg = new String(buffer);
				serialMsg = serialMsg.replaceAll(" ", "");
				serialMsg = serialMsg.trim();
				if(serialMsgBuffer.length() <= 11){
					serialMsgBuffer.append(serialMsg);
				}
				if(serialMsgBuffer.length() == 12){
					cmd = AnalyticalCmd(serialMsgBuffer.toString());
					HandleCmd(cmd);
					serialMsgBuffer.delete(0, serialMsgBuffer.length());
				}
				buffer = null;
				buffer = new byte[255];
			}
		}
	}


	/**
	 * 
	 * 从串口缓存中解析出命令 方法返回类型：String
	 */
	private String AnalyticalCmd(String SerialMsg) {
		String cmd = null;
		cmd = SerialMsg;
		return cmd;
	}

	/**
	 * 
	 * 通知各个观察者 方法返回类型：void
	 */
	private void HandleCmd(String cmd) {
		setChanged();
		notifyObservers(cmd);
	}
	
	/**
	 * 
	 * 串口进程关闭函数
	 * 方法返回类型：void
	 */
	public void close(){
		this.isStop = true;
		HardwareControler.close(fd);
		instance = null;
		Log.d("Serial", "串口进程关闭成功");
	}
}
