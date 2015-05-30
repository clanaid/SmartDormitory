package com.bailv.util;

import java.io.IOException;
import java.util.Observable;

import android.R.integer;
import android.os.Handler;

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

	public static final String[] devName = { "/dev/ttyUSB0" };

	public int getfd() {
		return fd;
	}

	public String getCmd() {
		return cmd;
	}

	public void SetHandler() {
	}

	public SerialThread(String devName, int baud, int dataBits, int stopBits)
			throws IOException {
		fd = ArmHardware.openSerialPort(devName, baud, dataBits, stopBits);
		if (-1 == fd) {
			throw new IOException();
		}
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		byte[] buffer = new byte[64];
		String serialMsg;
		while (true) {
			if (ArmHardware.select(fd, 0, 10000) == 1) {
				ArmHardware.read(fd, buffer, 64);
				serialMsg = new String(buffer);
				cmd = AnalyticalCmd(serialMsg);
				HandleCmd(cmd);
			}
		}
	}

	/**
	 * 
	 * 从串口缓存中解析出命令
	 * 方法返回类型：String
	 */
	private String AnalyticalCmd(String SerialMsg) {
		String cmd = null;
		return cmd;
	}

	/**
	 * 
	 * 通知各个观察者
	 * 方法返回类型：void
	 */
	private void HandleCmd(String cmd) {
		setChanged();
		notifyObservers(cmd);
	}
}
