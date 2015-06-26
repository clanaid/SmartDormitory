package com.bailv.smartdormitory;

import java.util.Observable;
import java.util.Observer;

import com.bailv.util.CircleProgressBar;
import com.bailv.util.EventsPost;
import com.bailv.util.EventsPost.EventType;
import com.bailv.util.SerialCmd;
import com.bailv.util.ToastShow;

import android.R.integer;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 宿舍环境Fragment
 * 
 * @author: 泓钦
 * @time: 2015年5月28日 上午1:31:55
 */
public class HomeFragment extends SerialFragment {

	private Button titleMenu;
	private TextView titleName;
	private TextView titleTime;

	private TextView lightType;
	private TextView lightValue;

	private View view;

	private Animation out_animation, in_animation;

	private CircleProgressBar tempBar, humidityBar;
	private TextView tempView, humidityView;

	private TextView isSmoke, noSmoke;
	private boolean SmokeState = false;

	private TextView noRain;
	private LinearLayout isRain;
	private boolean RainState = false;

	private TextView doorState;
	private TextView windowState;
	private TextView airState;
	private TextView lightState;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		view = inflater.inflate(R.layout.home_fragment, null);

		initAnimation();
		initview();

		return view;
	}

	private void initAnimation() {
		// TODO 自动生成的方法存根
		out_animation = AnimationUtils.loadAnimation(getActivity(),
				R.animator.fade_out);
		in_animation = AnimationUtils.loadAnimation(getActivity(),
				R.animator.fade_in);

		AnimationSet animationSet = new AnimationSet(true);
	}

	private void initview() {

		tempBar = (CircleProgressBar) view.findViewById(R.id.temp_progress);
		humidityBar = (CircleProgressBar) view
				.findViewById(R.id.humidity_progress);
		tempView = (TextView) view.findViewById(R.id.temp_value);
		humidityView = (TextView) view.findViewById(R.id.humidity_value);
		tempBar.setTextView(tempView);
		tempBar.setFuhao("℃");
		humidityBar.setTextView(humidityView);

		lightType = (TextView) view.findViewById(R.id.light_type);
		lightValue = (TextView) view.findViewById(R.id.light_value);

		isSmoke = (TextView) view.findViewById(R.id.home_smoke_ye);
		noSmoke = (TextView) view.findViewById(R.id.home_smoke_no);

		isRain = (LinearLayout) view.findViewById(R.id.home_rain_yes);
		noRain = (TextView) view.findViewById(R.id.home_rain_no);
		
		windowState = (TextView) view.findViewById(R.id.home_window_state);
		doorState = (TextView) view.findViewById(R.id.home_door_state);
		airState = (TextView) view.findViewById(R.id.home_air_state);
		lightState = (TextView) view.findViewById(R.id.home_light_state);

		// TODO 自动生成的方法存根
		titleMenu = (Button) view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);

		titleName = (TextView) view.findViewById(R.id.title_name);
		this.setTitlename(titleName);

		titleTime = (TextView) view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);

	}

	@Override
	public void onEventMainThread(SerialCmd cmd) {
		// TODO 自动生成的方法存根
		String cmdString = cmd.getCmd();
		char[] cmdChar = new char[5];
		cmdString.getChars(0, cmdString.length() - 1, cmdChar, 0);
		Log.d("HomeGet", cmdString);
		// ToastShow.OpenDor(getActivity(), R.drawable.ic_launcher, "梁泓钦开门",
		// true).show();
		switch (cmdString.charAt(0)) {
		case 'd':
			dealwithTempHumidity(cmdString);
			break;

		case 'r':
			dealwithRain(cmdString);
			break;

		case 'f':
			dealwithSmoke(cmdString);
			break;

		case 'l':
			dealwithLight(cmdString);
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * 雨滴传感器数据处理 方法返回类型：void
	 */
	private void dealwithRain(String cmdString) {

		boolean tempState = cmdString.charAt(1) == '1';

		if (tempState != RainState) {
			if (tempState) {
				isRain.startAnimation(in_animation);
				noRain.startAnimation(out_animation);
			} else {
				noRain.startAnimation(in_animation);
				isRain.startAnimation(out_animation);
			}
		}

		RainState = tempState;
	}

	/**
	 * 
	 * 光传感器数据处理 方法返回类型：void
	 */
	private void dealwithLight(String cmdString) {
		int light = (cmdString.charAt(1) - 48) * 10000;
		light += (cmdString.charAt(2) - 48) * 1000;
		light += (cmdString.charAt(3) - 48) * 100;
		light += cmdString.charAt(4) - 48;
		TextValue lValue = new TextValue(lightValue, "lx");
		ObjectAnimator.ofInt(lValue, "text", light).setDuration(700).start();
	}

	/**
	 * 
	 * 温湿度传感器数据处理 方法返回类型：void
	 */
	private void dealwithTempHumidity(String cmdString) {
		int temp = (cmdString.charAt(1) - 48) * 10;
		temp += cmdString.charAt(2) - 48;
		int humidity = (cmdString.charAt(3) - 48) * 10;
		humidity += cmdString.charAt(4) - 48;
		ObjectAnimator.ofInt(tempBar, "progress", temp).setDuration(700)
				.start();
		ObjectAnimator.ofInt(humidityBar, "progress", humidity)
				.setDuration(700).start();
	}

	private void dealwithSmoke(String cmdString) {

		boolean tempState = cmdString.charAt(1) == '1';

		if (tempState != SmokeState) {
			if (tempState) {
				isSmoke.startAnimation(in_animation);
				noSmoke.startAnimation(out_animation);
			} else {
				isSmoke.startAnimation(out_animation);
				noSmoke.startAnimation(in_animation);
			}
		}

		SmokeState = tempState;
	}

	/**
	 * 
	 * TextView 数字变化动画自定义类
	 * 
	 * @author: 泓钦
	 * @time: 2015年6月17日 上午12:41:21
	 */
	private static class TextValue {

		private TextView mTarget;
		private String company;

		public TextValue(TextView view, String company) {
			// TODO 自动生成的构造函数存根
			this.mTarget = view;
			this.company = company;
		}

		public void setText(int value) {
			mTarget.setText(value + company);
			mTarget.requestLayout();
		}

		public int getText() {
			return Integer.parseInt(mTarget.getText().toString());
		}
	}

	@Override
	public void onEventMainThread(EventsPost eventcmd) {
		// TODO 自动生成的方法存根
		EventType type = eventcmd.getEventType();
		switch (type) {
		case HOMESTATE:
			dealHomeState(eventcmd);
			break;

		default:
			break;
		}
	}

	private void dealHomeState(EventsPost eventcmd) {
		// TODO 自动生成的方法存根
		char which = eventcmd.getHomesate().getWhich();
		boolean action = eventcmd.getHomesate().isAction();
		switch (which) {
		case 'A':
			if (action)
				airState.setText("开");
			else
				airState.setText("关");
			break;
		case 'D':
			if (action)
				doorState.setText("开");
			else
				doorState.setText("关");
			break;
		case 'W':
			if (action)
				windowState.setText("开");
			else
				windowState.setText("关");
			break;
		case 'L':
			if (action)
				lightState.setText("开");
			else
				lightState.setText("关");
			break;
		default:
			break;
		}
	}

	

}
