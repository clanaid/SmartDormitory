package com.bailv.smartdormitory;

import java.util.Observable;
import java.util.Observer;

import com.bailv.util.SerialCmd;
import com.bailv.util.ToastShow;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

	private Button button;
	private ImageView img;
	private TextView textView;

	private View view;

	private Animation animation, animation2;

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
		animation = AnimationUtils.loadAnimation(getActivity(),
				R.animator.fade_out);
		animation2 = AnimationUtils.loadAnimation(getActivity(),
				R.animator.fade_in);

		AnimationSet animationSet = new AnimationSet(true);
	}

	private void initview() {
		// TODO 自动生成的方法存根
		titleMenu = (Button) view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);

		titleName = (TextView) view.findViewById(R.id.title_name);
		this.setTitlename(titleName);

		titleTime = (TextView) view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);

		textView = (TextView) view.findViewById(R.id.rain_value);
		img = (ImageView) view.findViewById(R.id.rain_img);

	}

	@Override
	public void onEventMainThread(SerialCmd cmd) {
		// TODO 自动生成的方法存根
		String cmdString = cmd.getCmd();
		ToastShow.OpenDor(getActivity(), R.drawable.ic_launcher, "梁泓钦开门", true).show();
	}

	
}
