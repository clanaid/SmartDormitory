package com.bailv.smartdormitory;

import java.text.SimpleDateFormat;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.Contacts.Data;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bailv.util.CircleButton;
import com.bailv.util.EventsPost;
import com.bailv.util.RealTime;
import com.bailv.util.controlSocket;
import com.bailv.util.EventsPost.EventType;
import com.bailv.util.controlSocket.ControlType;

import de.greenrobot.event.EventBus;

/**
 * 
 * 空调Fragment 使用socket通信
 * 
 * @author: 泓钦
 * @time: 2015年5月14日 下午1:15:51
 */
public class AirFragment extends SmartDormitoryFragment {

	private boolean is_learn;

	private boolean isPower = false;

	private View view;

	private TextView titleTime;

	private ImageView air;

	private Button temp_up; // 温度上升
	private Button temp_down;// 温度下降
	private Button anto; // 自动模式
	private Button refrigeration;// 制冷
	private Button desiccant;// 除湿
	private Button heating;// 制热
	private Button ventilation;// 通风
	private Button strong;// 强风

	private Button titleMenu;

	private CheckBox control;
	private CheckBox learn;

	private TextView title;

	private CircleButton power;

	private Handler handler = new Handler() {

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		view = inflater.inflate(R.layout.air_control, null);
		initView();
		initListener();

		return view;
	}

	private void power_learn() {

		if (isPower) {
			new Thread(new controlSocket(ControlType.LEARN_OPEN, null)).start();
			air.setImageResource(R.drawable.air_open);
			power_color();
		} else {
			new Thread(new controlSocket(ControlType.LEARN_CLOSE, null))
					.start();
			air.setImageResource(R.drawable.air_close);
			power_color();
		}
		/*
		 * switch (powerNum) { case true: new Thread(new
		 * controlSocket(ControlType.LEARN_OPEN, null)).start();
		 * air.setImageResource(R.drawable.air_open); power_color(); break; case
		 * false: new Thread(new controlSocket(ControlType.LEARN_CLOSE, null))
		 * .start(); air.setImageResource(R.drawable.air_close); power_color();
		 * break; default: break; }
		 */
	}

	private void power_color() {
		if (isPower) {
			power.setColor(Color.GREEN);
		} else {
			power.setColor(Color.RED);
		}
		/*
		 * switch (powerNum) { case 0: power.setColor(Color.GREEN); break; case
		 * -1: power.setColor(Color.RED); break; default: break; }
		 */
	}

	private void power_button() {
		if (isPower) {
			new Thread(new controlSocket(ControlType.OPEN, null)).start();
			air.setImageResource(R.drawable.air_open);
			power_color();
			SendState('A', true);
		} else {
			power_color();
			new Thread(new controlSocket(ControlType.CLOSE, null)).start();
			air.setImageResource(R.drawable.air_close);
			SendState('A', false);
		}
		/*
		 * switch (powerNum) { case 0: new Thread(new
		 * controlSocket(ControlType.OPEN, null)).start();
		 * air.setImageResource(R.drawable.air_open); power_color();
		 * SendState('A', true); break; case -1: power_color(); new Thread(new
		 * controlSocket(ControlType.CLOSE, null)).start();
		 * air.setImageResource(R.drawable.air_close); SendState('A', false);
		 * break;
		 * 
		 * default: break; }
		 */
	}

	private void SendState(char which, Boolean action) {

		EventsPost eventsPost = new EventsPost(which, action,
				EventType.HOMESTATE);
		EventBus.getDefault().post(eventsPost);

	}

	private void initListener() {
		// TODO 自动生成的方法存根
		power.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isPower = !isPower;
				if (is_learn) {
					power_learn();
				} else {
					power_button();
				}
			}
		});

		temp_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (is_learn) {
					new Thread(new controlSocket(ControlType.LEARN_UP, null))
							.start();
				} else {

					new Thread(new controlSocket(ControlType.UP, null)).start();
				}
			}
		});

		temp_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (is_learn) {
					new Thread(new controlSocket(ControlType.LEARN_DOWN, null))
							.start();
				} else {
					new Thread(new controlSocket(ControlType.DOWN, null))
							.start();
				}
			}
		});

		anto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (is_learn) {
					new Thread(new controlSocket(ControlType.LEARN_AUTO, null))
							.start();
				} else {
					new Thread(new controlSocket(ControlType.AUTO, null))
							.start();
				}
			}
		});

		refrigeration.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (is_learn) {
					new Thread(new controlSocket(
							ControlType.LEARN_REFRIGERATION, null)).start();
				} else {
					new Thread(new controlSocket(ControlType.REFRIGERATION,
							null)).start();
				}
			}
		});

		desiccant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (is_learn) {
					new Thread(new controlSocket(ControlType.LEARN_DESICCANT,
							null)).start();
				} else {
					new Thread(new controlSocket(ControlType.DESICCANT, null))
							.start();
				}
			}
		});

		heating.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (is_learn) {
					new Thread(new controlSocket(ControlType.LEARN_HEAT, null))
							.start();
				} else {
					new Thread(new controlSocket(ControlType.HEAT, null))
							.start();
				}
			}
		});

		ventilation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (is_learn) {
					new Thread(new controlSocket(ControlType.LEARN_VENTILATION,
							null)).start();
				} else {
					new Thread(new controlSocket(ControlType.VENTILATION, null))
							.start();
				}
			}
		});
		strong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (is_learn) {
					new Thread(
							new controlSocket(ControlType.LEARN_STRONG, null))
							.start();
				} else {
					new Thread(new controlSocket(ControlType.STRONG, null))
							.start();
				}
			}
		});

		learn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO 自动生成的方法存根
				if (isChecked) {
					is_learn = true;
					new Thread(new controlSocket(ControlType.LEARN, null))
							.start();
					control.setChecked(false);
				}
			}

		});

		control.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO 自动生成的方法存根
				if (isChecked) {
					is_learn = false;
					new Thread(new controlSocket(ControlType.CONTROL, null))
							.start();
					learn.setChecked(false);
				}
			}
		});
	}

	private void initView() {
		// TODO 自动生成的方法存根
		temp_up = (Button) view.findViewById(R.id.air_temp_up);
		temp_down = (Button) view.findViewById(R.id.air_temp_down);
		anto = (Button) view.findViewById(R.id.air_mode_anto);
		refrigeration = (Button) view.findViewById(R.id.air_mode_refrigeration);
		desiccant = (Button) view.findViewById(R.id.air_mode_desiccant);
		heating = (Button) view.findViewById(R.id.air_mode_heating);
		ventilation = (Button) view.findViewById(R.id.air_mode_ventilation);
		strong = (Button) view.findViewById(R.id.air_mode_strong);

		power = (CircleButton) view.findViewById(R.id.air_power);

		title = (TextView) view.findViewById(R.id.title_name);
		this.setTitlename(title, "空调控制");

		titleTime = (TextView) view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);

		titleMenu = (Button) view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);

		learn = (CheckBox) view.findViewById(R.id.air_learn_checked);
		control = (CheckBox) view.findViewById(R.id.air_control_checked);

		air = (ImageView) view.findViewById(R.id.air);

		is_learn = false;
	}

	@Override
	public void onEventMainThread(EventsPost eventcmd) {
		// TODO 自动生成的方法存根
		String cmdString = eventcmd.getCmd();
		EventType type = eventcmd.getEventType();
		switch (type) {
		case DDPUSH:
			dealPush(cmdString);
			break;

		default:
			break;
		}
	}

	private void dealPush(String cmdString) {
		// TODO 自动生成的方法存根
		char state = cmdString.charAt(0);
		boolean action = (cmdString.charAt(1) == 'O') ? true : false;
		switch (state) {
		case 'A':
			dealAir(action);
			break;
		default:
			break;
		}
	}

	private void dealAir(boolean action) {
		// TODO 自动生成的方法存根
		if (!is_learn) {
			isPower = action;
			power_button();
		}
	}

}
