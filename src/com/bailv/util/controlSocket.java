package com.bailv.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import android.R.bool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class controlSocket implements Runnable {

	public static final byte[] host = { (byte) 192, (byte) 168, 1, 17 };
	public static final int port = 4660;

	private ControlType cmd;
	private BufferedReader reader;
	private BufferedWriter writer;
	private Socket socket;
	
	private boolean is_learn;

	public enum ControlType {
		UP, DOWN, OPEN, CLOSE, AUTO, REFRIGERATION, DESICCANT, HEAT, 
		VENTILATION, STRONG, LEARN, LEARN_UP, LEARN_DOWN, LEARN_OPEN, 
		LEARN_CLOSE, LEARN_AUTO, LEARN_REFRIGERATION, LEARN_DESICCANT, 
		LEARN_HEAT, LEARN_VENTILATION, LEARN_STRONG,CONTROL;
	};

	private Handler handler;

	private static final Map<ControlType, String> cmdS = new HashMap<ControlType, String>() {
		{
			put(ControlType.OPEN, "h019999999999999999999");
			put(ControlType.CLOSE, "h022999999999999999999");
			put(ControlType.UP, "h018999999999999999999");
			put(ControlType.DOWN, "h020999999999999999999");
			put(ControlType.AUTO, "h021999999999999999999");
			put(ControlType.DESICCANT, "h024999999999999999999");
			put(ControlType.REFRIGERATION, "h027999999999999999999");
			put(ControlType.STRONG, "h030999999999999999999");
			put(ControlType.HEAT, "h033999999999999999999");
			put(ControlType.VENTILATION, "h025999999999999999999");
			
			put(ControlType.CONTROL, "q099999999999999999999");

			put(ControlType.LEARN, "i099999999999999999999");
			put(ControlType.LEARN_AUTO, "b021999999999999999999");
			put(ControlType.LEARN_CLOSE, "b019999999999999999999");
			put(ControlType.LEARN_DESICCANT, "b024999999999999999999");
			put(ControlType.LEARN_DOWN, "b020999999999999999999");
			put(ControlType.LEARN_HEAT, "b033999999999999999999");
			put(ControlType.LEARN_OPEN, "h019999999999999999999");
			put(ControlType.LEARN_REFRIGERATION, "h027999999999999999999");
			put(ControlType.LEARN_STRONG, "b030999999999999999999");
			put(ControlType.LEARN_UP, "b018999999999999999999");
			put(ControlType.LEARN_VENTILATION, "b025999999999999999999");
		}

	};

	public controlSocket(ControlType cmd, Handler handler) {
		// TODO 自动生成的构造函数存根
		this.cmd = cmd;
		this.handler = handler;

	}

	public controlSocket(ControlType cmd, boolean learn) {
		this.cmd = cmd;
		this.is_learn = learn;
	}

	public controlSocket(ControlType cmd) {
		// TODO 自动生成的构造函数存根
		this.cmd = cmd;

	}

	public void sendMsg() {
		try {

			switch (cmd) {
			case UP:
				writer.write(cmdS.get(ControlType.UP));
				break;
			case DOWN:
				writer.write(cmdS.get(ControlType.DOWN));
				break;
			case OPEN:
				writer.write(cmdS.get(ControlType.OPEN));
				break;
			case CLOSE:
				writer.write(cmdS.get(ControlType.CLOSE));
				break;
			case AUTO:
				writer.write(cmdS.get(ControlType.AUTO));
				break;
			case REFRIGERATION:
				writer.write(cmdS.get(ControlType.REFRIGERATION));
				break;
			case DESICCANT:
				writer.write(cmdS.get(ControlType.DESICCANT));
				break;
			case HEAT:
				writer.write(cmdS.get(ControlType.HEAT));
				break;
			case VENTILATION:
				writer.write(cmdS.get(ControlType.VENTILATION));
				break;
			case STRONG:
				writer.write(cmdS.get(ControlType.STRONG));
				break;
			case LEARN:
				writer.write(cmdS.get(ControlType.LEARN));
				break;
			case LEARN_UP:
				writer.write(cmdS.get(ControlType.LEARN_UP));
				break;
			case LEARN_DOWN:
				writer.write(cmdS.get(ControlType.LEARN_DOWN));
				break;
			case LEARN_OPEN:
				writer.write(cmdS.get(ControlType.LEARN_OPEN));
				break;
			case LEARN_CLOSE:
				writer.write(cmdS.get(ControlType.LEARN_CLOSE));
				break;
			case LEARN_AUTO:
				writer.write(cmdS.get(ControlType.LEARN_AUTO));
				break;
			case LEARN_REFRIGERATION:
				writer.write(cmdS.get(ControlType.LEARN_REFRIGERATION));
				break;
			case LEARN_DESICCANT:
				writer.write(cmdS.get(ControlType.LEARN_DESICCANT));
				break;
			case LEARN_HEAT:
				writer.write(cmdS.get(ControlType.LEARN_HEAT));
				break;
			case LEARN_VENTILATION:
				writer.write(cmdS.get(ControlType.LEARN_VENTILATION));
				break;
			case LEARN_STRONG:
				writer.write(cmdS.get(ControlType.LEARN_STRONG));
				break;
			case CONTROL:
				writer.write(cmdS.get(ControlType.CONTROL));
				break;
			default:
				break;
			}

			writer.flush();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public String ReceiveMsg() {
		String txt = "";
		String line = "";
		try {
			while ((line = reader.readLine()) != null) {
				txt += line;
			}
			reader.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return txt;
	}

	@Override
	public void run() {
		String rev;

		try {
			socket = new Socket(InetAddress.getByAddress(host), port);
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			sendMsg();
			rev = ReceiveMsg();

			if (rev != null) {
				writer.close();
				socket.close();
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("REC", rev);
				msg.setData(bundle);
				if (handler != null)
					handler.sendMessage(msg);
			}
			
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {

		}

	}

}
