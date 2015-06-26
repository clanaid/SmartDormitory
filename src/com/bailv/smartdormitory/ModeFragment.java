package com.bailv.smartdormitory;

import com.bailv.util.EventsPost;
import com.bailv.util.EventsPost.EventType;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ModeFragment extends SmartDormitoryFragment {
	
	private static final int _24H = 1000 * 60 * 60 * 24;// 24小时

	private Button titleMenu;
	private TextView titleName;
	private TextView titleTime;
	private View view;

	private CheckBox sleepCheckBox;
	private CheckBox getupCheckBox;
	private CheckBox outCheckBox;
	
	private Button setSleepClock;
	private Button setGetupClock;

	private TextView sleepTextView;
	private TextView getupTextView;
	private TextView outTextView;

	private Resources myResources;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		view = inflater.inflate(R.layout.mode_fragment, null);
		initView();
		initLinstener();
		myResources = getResources();
		return view;
	}

	private void initLinstener() {
		// TODO 自动生成的方法存根

		sleepCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO 自动生成的方法存根
				if (isChecked) {
					sleepTextView.setTextColor(myResources
							.getColor(R.color.mode_text_select));
				} else {
					Toast.makeText(getActivity(), "sleeponCheck",
							Toast.LENGTH_SHORT).show();
					sleepTextView.setTextColor(myResources
							.getColor(R.color.mode_text_unselect));
				}
			}
		});

		getupCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO 自动生成的方法存根
				if (isChecked) {
					getupTextView.setTextColor(myResources
							.getColor(R.color.mode_text_select));
				} else {
					Toast.makeText(getActivity(), "getuponCheck",
							Toast.LENGTH_SHORT).show();
					getupTextView.setTextColor(myResources
							.getColor(R.color.mode_text_unselect));
				}
			}
		});

		outCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO 自动生成的方法存根
				if (isChecked) {
					
					outTextView.setTextColor(myResources
							.getColor(R.color.mode_text_select));

				} else {
					Toast.makeText(getActivity(), "outonCheck",
							Toast.LENGTH_SHORT).show();
					outTextView.setTextColor(myResources
							.getColor(R.color.mode_text_unselect));
				}
			}
		});
	}

	private void initView() {
		// TODO 自动生成的方法存根
		titleMenu = (Button) view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);

		titleName = (TextView) view.findViewById(R.id.title_name);
		this.setTitlename(titleName, "宿舍模式");

		titleTime = (TextView) view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);

		sleepCheckBox = (CheckBox) view.findViewById(R.id.mode_sleep_checked);
		getupCheckBox = (CheckBox) view.findViewById(R.id.mode_getup_checked);
		outCheckBox = (CheckBox) view.findViewById(R.id.mode_out_checked);

		sleepTextView = (TextView) view.findViewById(R.id.mode_sleep_text);
		outTextView = (TextView) view.findViewById(R.id.mode_out_text);
		getupTextView = (TextView) view.findViewById(R.id.mode_getUp_text);

	}
	
	private void initAlarm() {
		// TODO 自动生成的方法存根
		Intent intent = new Intent(getActivity(), ModeAlarmBReceiver.class);
		intent.setAction("short");
		
		Intent intent1 = new Intent(getActivity(), ModeAlarmBReceiver.class);
		intent1.setAction("out");
		
		PendingIntent sender = PendingIntent.getBroadcast(getActivity(), 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);
		PendingIntent sender1 = PendingIntent.getBroadcast(getActivity(), 0,
				intent1, PendingIntent.FLAG_CANCEL_CURRENT);
		long firstime = SystemClock.elapsedRealtime();

		AlarmManager alarmManager = (AlarmManager) getActivity()
				.getSystemService(Context.ALARM_SERVICE);
		// 5秒一个周期，不停的发送广播
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime,
				5 * 1000, sender);
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime,
				6 * 1000, sender1);

	}
	
	public void onEventMainThread(EventsPost eventcmd) {
		// TODO 自动生成的方法存根
		String cmd = eventcmd.getCmd();
		EventType eventType = eventcmd.getEventType();
		switch (eventType) {
		case DDPUSH:
			
			break;

		default:
			break;
		}
	}
}
