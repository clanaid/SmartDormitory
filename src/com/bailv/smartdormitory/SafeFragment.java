package com.bailv.smartdormitory;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bailv.util.CheckSwitchButton;
import com.bailv.util.CircleButton;
import com.bailv.util.ControlCmd;
import com.bailv.util.ControlCmd.CmdName;
import com.bailv.util.EventsPost.EventType;
import com.bailv.util.EventsPost;
import com.bailv.util.SerialCmd;

import de.greenrobot.event.EventBus;

/**
 * 
 * ����Fragment
 * 
 * @author: ����
 * @time: 2015��5��28�� ����1:32:34
 */
public class SafeFragment extends SerialFragment {

	private View view;

	private Button titleMenu;
	private TextView titleName;
	private TextView titleTime;

	private TextView irTextView;

	CheckSwitchButton chuanlianSwitch, windowSwitch;

	private Animation out_animation;
	private Animation in_animation;

	private CircleButton doorOpenButton, doorCloseButton;

	private ImageView window_on, chuanlian_on, window_off, chuanlian_off,
			door_open, door_close;

	private boolean IRState = false, WindowState = false,
			ChuanlianStae = false, DoorState = false;

	private boolean pushWindowState = false, pushChuanlianState = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		view = inflater.inflate(R.layout.safe_fragment, null);
		initView();
		initAnimation();
		initListener();
		return view;
	}

	private void initListener() {
		// TODO �Զ����ɵķ������
		chuanlianSwitch
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO �Զ����ɵķ������
						if (!pushChuanlianState)
							chuanlianChange(isChecked);
						else
							pushChuanlianState = false;
					}
				});

		windowSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO �Զ����ɵķ������
				if (!pushWindowState)
					windowChange(isChecked);
				else
					pushWindowState = false;
			}
		});

		doorOpenButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				DoorChange(true);
			}
		});

		doorCloseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������

				DoorChange(false);
			}
		});

	}

	private void windowChange(boolean isOpen) {
		if (isOpen != WindowState) {
			if (isOpen) {
				window_off.startAnimation(out_animation);
				window_on.startAnimation(in_animation);
				SafeFragment.this.SendCmd(ControlCmd
						.getCmd(CmdName.OPEN_WINDOW));
			} else {
				window_on.startAnimation(out_animation);
				window_off.startAnimation(in_animation);
				SafeFragment.this.SendCmd(ControlCmd
						.getCmd(CmdName.CLOSE_WINDOW));
			}
			SendState('W',isOpen);
		}
		WindowState = isOpen;
	}

	private void DoorChange(boolean isOpen) {
		if (isOpen != DoorState) {
			if (isOpen) {
				SafeFragment.this.SendCmd(ControlCmd.getCmd(CmdName.OPEN_DOOR));
				door_open.startAnimation(in_animation);
				door_close.startAnimation(out_animation);
			} else {
				SafeFragment.this
						.SendCmd(ControlCmd.getCmd(CmdName.CLOSE_DOOR));
				door_close.startAnimation(in_animation);
				door_open.startAnimation(out_animation);
			}
			SendState('D',isOpen);
		}
		DoorState = isOpen;
	}

	private void chuanlianChange(boolean isOpen) {
		if (isOpen != ChuanlianStae) {
			if (isOpen) {
				chuanlian_off.startAnimation(out_animation);
				chuanlian_on.startAnimation(in_animation);
				SafeFragment.this.SendCmd(ControlCmd
						.getCmd(CmdName.OPEN_CURTAIN));
			} else {
				chuanlian_on.startAnimation(out_animation);
				chuanlian_off.startAnimation(in_animation);
				SafeFragment.this.SendCmd(ControlCmd
						.getCmd(CmdName.CLOSE_CURTAIN));
			}
			SendState('C',isOpen);
		}
		ChuanlianStae = isOpen;
	}

	private void SendState(char which, Boolean action) {
		
		EventsPost eventsPost = new EventsPost(which, action, EventType.HOMESTATE);
		EventBus.getDefault().post(eventsPost);
		
	}

	private void initView() {

		chuanlianSwitch = (CheckSwitchButton) view
				.findViewById(R.id.safe_chuanlian_swtich);
		windowSwitch = (CheckSwitchButton) view
				.findViewById(R.id.safe_window_switch);

		window_on = (ImageView) view.findViewById(R.id.safe_window_on);
		window_off = (ImageView) view.findViewById(R.id.safe_window_off);
		chuanlian_off = (ImageView) view.findViewById(R.id.safe_chuanglian_off);
		chuanlian_on = (ImageView) view.findViewById(R.id.safe_chuanglian_on);

		irTextView = (TextView) view.findViewById(R.id.safe_ir_state);

		doorOpenButton = (CircleButton) view
				.findViewById(R.id.safe_door_open_power);
		doorCloseButton = (CircleButton) view
				.findViewById(R.id.safe_door_close_power);
		door_open = (ImageView) view.findViewById(R.id.safe_door_on);
		door_close = (ImageView) view.findViewById(R.id.safe_door_off);

		// TODO �Զ����ɵķ������
		titleMenu = (Button) view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);

		titleName = (TextView) view.findViewById(R.id.title_name);
		this.setTitlename(titleName, "���ᰲ��");

		titleTime = (TextView) view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);

	}

	private void initAnimation() {
		// TODO �Զ����ɵķ������
		out_animation = AnimationUtils.loadAnimation(getActivity(),
				R.animator.fade_out);
		in_animation = AnimationUtils.loadAnimation(getActivity(),
				R.animator.fade_in);
	}

	@Override
	public void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
		/*
		 * Intent intent = new Intent(getActivity(),
		 * SleepModeAlarmBReceiver.class); intent.setAction("short");
		 * PendingIntent sender = PendingIntent.getBroadcast(getActivity(), 0,
		 * intent, PendingIntent.FLAG_CANCEL_CURRENT);
		 * 
		 * AlarmManager alarmManager = (AlarmManager) getActivity()
		 * .getSystemService(Context.ALARM_SERVICE);
		 * alarmManager.cancel(sender);
		 */
	}

	@Override
	public void onEventMainThread(SerialCmd cmd) {
		// TODO �Զ����ɵķ������
		String cmdString = cmd.getCmd();
		char[] cmdChar = new char[5];
		cmdString.getChars(0, cmdString.length() - 1, cmdChar, 0);
		// ToastShow.OpenDor(getActivity(), R.drawable.ic_launcher, "�����տ���",
		// true).show();
		switch (cmdString.charAt(0)) {
		case 'p':
			dealIR(cmdString);
			break;
		case 'e':
			dealDoor(cmdString);
			break;
		}
	}

	private void dealDoor(String cmdString) {
		// TODO �Զ����ɵķ������

	}

	private void dealIR(String cmdString) {
		// TODO �Զ����ɵķ������
		boolean tempState = cmdString.charAt(1) == '1';

		if (tempState != IRState) {
			if (tempState) {
				irTextView.setText("����");
			} else {
				irTextView.setText("����");
			}
		}

		IRState = tempState;
	}

	@Override
	public void onEventMainThread(EventsPost eventcmd) {
		// TODO �Զ����ɵķ������
		String cmd = eventcmd.getCmd();
		EventType eventType = eventcmd.getEventType();
		switch (eventType) {
		case SLEEPALARM:

			break;
		case OUTALARM:
			break;
		case DDPUSH:
			dealPush(cmd);
			
			break;
		case GETUPALARM:
			break;
		default:
			break;
		}
	}

	private void dealPush(String cmd) {
		// TODO �Զ����ɵķ������
		char state = cmd.charAt(0);
		boolean action = (cmd.charAt(1) == 'O') ? true : false;
		switch (state) {
		case 'W':
			dealpushWindown(action);
			break;

		case 'C':
			dealpushChuanian(action);
			break;
		case 'E':
			dealpushDoor(action);
			break;
		default:
			break;
		}
	}

	private void dealpushDoor(boolean action) {
		// TODO �Զ����ɵķ������
		DoorChange(action);
	}

	private void dealpushChuanian(boolean action) {
		// TODO �Զ����ɵķ������
		this.pushWindowState = true;
		chuanlianChange(action);
		chuanlianSwitch.setChecked(action);
	}

	private void dealpushWindown(boolean action) {
		// TODO �Զ����ɵķ������
		this.pushChuanlianState = true;
		windowChange(action);
		windowSwitch.setChecked(action);
	}

}
