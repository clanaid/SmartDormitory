package com.bailv.smartdormitory;

import java.text.SimpleDateFormat;

import com.bailv.util.EventsPost;
import com.bailv.util.RealTime;

import de.greenrobot.event.EventBus;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public abstract class SmartDormitoryFragment extends Fragment {

	public interface ShowMenuListener {
		public void show();
	}

	private TextView titleTime;
	private TextView titlename;
	private Button titleMenuButton;
	private ShowMenuListener showMenuListener;

	private SharedPreferences myShareActionProvider;
	private SharedPreferences.Editor myshareEditor;

	protected Handler handler = new Handler() {

		SimpleDateFormat myFmt = new SimpleDateFormat("HH:mm:ss\tyyyy/M/d");

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case RealTime.msgKey1:
				long sysTime = System.currentTimeMillis();
				CharSequence sysTimeStr = myFmt.format(sysTime);
				titleTime.setText(sysTimeStr);
				break;

			default:
				break;
			}
		}
	};

	public void onAttach(android.app.Activity activity) {
		super.onAttach(activity);
		showMenuListener = (ShowMenuListener) activity;
	};

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public void setTitleTime(TextView titleTime) {
		this.titleTime = titleTime;
	}

	public void setTitlename(TextView titlename) {
		this.titlename = titlename;
	}

	public void setTitlename(TextView titlename, String title) {
		this.titlename = titlename;
		this.titlename.setText(title);
	}

	public void setTitleMenuButton(Button titleMenuButton) {
		this.titleMenuButton = titleMenuButton;
		this.titleMenuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				showMenuListener.show();
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		RealTime.getRealTime().setHandler(handler);
		regigisterEventBus();
		myShareActionProvider = getActivity().getSharedPreferences(
				"sdormitoryConfig", getActivity().MODE_PRIVATE);
		myshareEditor = myShareActionProvider.edit();
	}
	
	protected void regigisterEventBus() {
		EventBus.getDefault().register(this);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO 自动生成的方法存根
		super.onHiddenChanged(hidden);
		if (!hidden)
			RealTime.getRealTime().setHandler(handler);
	}
	
	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	
	/**
	 * 
	 * 接收push、定时器等命令（除了串口）
	 * 方法返回类型：void
	 */
	abstract public void onEventMainThread(EventsPost eventcmd);

}
