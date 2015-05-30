package com.bailv.util;

import java.io.IOException;
import java.util.Observable;

import android.R.integer;
import android.os.Handler;

/**
 * 
 * ���ڽ�����
 * 
 * @author: ����
 * @time: 2015��5��13�� ����8:44:45
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
		// TODO �Զ����ɵķ������
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
	 * �Ӵ��ڻ����н���������
	 * �����������ͣ�String
	 */
	private String AnalyticalCmd(String SerialMsg) {
		String cmd = null;
		return cmd;
	}

	/**
	 * 
	 * ֪ͨ�����۲���
	 * �����������ͣ�void
	 */
	private void HandleCmd(String cmd) {
		setChanged();
		notifyObservers(cmd);
	}
}
