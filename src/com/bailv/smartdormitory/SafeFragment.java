package com.bailv.smartdormitory;

import java.util.Observable;

import com.bailv.util.SerialCmd;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * 安防Fragment
 * 
 * @author: 泓钦
 * @time: 2015年5月28日 上午1:32:34
 */
public class SafeFragment extends SerialFragment {
	
	private static final int _24H = 1000*60*60*24;//24小时
	
	private View view;
	
	private Button titleMenu;
	private TextView titleName;
	private TextView titleTime;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		view = inflater.inflate(R.layout.safe_fragment, null);
		initView();
		return view;
	}
	
	private void initView() {
		// TODO 自动生成的方法存根
		titleMenu = (Button)view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);
		
		titleName = (TextView)view.findViewById(R.id.title_name);
		this.setTitlename(titleName,"宿舍安防");
		
		titleTime = (TextView)view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);

	}

	@Override
	public void onEventMainThread(SerialCmd cmd) {
		// TODO 自动生成的方法存根
		
	}

}
