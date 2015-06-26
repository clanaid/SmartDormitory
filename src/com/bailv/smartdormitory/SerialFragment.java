package com.bailv.smartdormitory;

import com.bailv.util.SerialCmd;
import com.bailv.util.SerialThread;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public abstract class SerialFragment extends SmartDormitoryFragment {

	public interface SerialSendLinstener{
		public void Send(String cmd);
	}
	
	private SerialSendLinstener SendLinstener;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO 自动生成的方法存根
		SendLinstener = (SerialSendLinstener)activity;
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
	}
	
	
	/**
	 * 
	 * 串口事件接收
	 * 方法返回类型：void
	 */
	public abstract void onEventMainThread(SerialCmd cmd);
	
	protected void SendCmd(String cmd) {
		SendLinstener.Send(cmd);
	}
}
