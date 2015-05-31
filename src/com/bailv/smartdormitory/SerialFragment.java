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
		// TODO �Զ����ɵķ������
		SendLinstener = (SerialSendLinstener)activity;
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		regigisterEventBus();
		SendCmd("{000000000}");
		super.onCreate(savedInstanceState);
	}
	
	protected void regigisterEventBus() {
		EventBus.getDefault().register(this);
	}
	
	@Override
	public void onDestroy() {
		// TODO �Զ����ɵķ������
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	
	public abstract void onEventMainThread(SerialCmd cmd);
	
	protected void SendCmd(String cmd) {
		SendLinstener.Send(cmd);
	}
}
