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
 * ���ڽ�����
 * 
 * @author: ����
 * @time: 2015��5��13�� ����8:44:45
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
				Log.d("Serial","���������ɹ�");
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
				Log.d("Serial","��������ʧ��");
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
		Log.d("Serial","��������");
		fd = HardwareControler.openSerialPort(devName, baud, dataBits, stopBits);
		if (-1 == fd) {
			throw new IOException("�������ڴ���");
		}
	}
	

	public SerialThread() throws IOException{
		// TODO �Զ����ɵĹ��캯�����
		fd = HardwareControler.openSerialPort("/dev/ttyUSB0", 115200, 8, 1);
		if(fd == -1){
			Log.d("Serial", "���ڴ�ʧ��");
			throw new IOException();
		}
	}

	@Override
	public void run() {
		// TODO �Զ����ɵķ������
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
	 * �Ӵ��ڻ����н��������� �����������ͣ�String
	 */
	private String AnalyticalCmd(String SerialMsg) {
		String cmd = null;
		cmd = SerialMsg;
		return cmd;
	}

	/**
	 * 
	 * ֪ͨ�����۲��� �����������ͣ�void
	 */
	private void HandleCmd(String cmd) {
		setChanged();
		notifyObservers(cmd);
	}
	
	/**
	 * 
	 * ���ڽ��̹رպ���
	 * �����������ͣ�void
	 */
	public void close(){
		this.isStop = true;
		HardwareControler.close(fd);
		instance = null;
		Log.d("Serial", "���ڽ��̹رճɹ�");
	}
}
