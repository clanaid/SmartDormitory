package com.bailv.smartdormitory;

import java.util.Observable;
import java.util.Observer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 宿舍环境Fragment
 * 
 * @author: 泓钦
 * @time: 2015年5月28日 上午1:31:55
 */
public class HomeFragment extends SerialFragment{

	
	private Button titleMenu;
	private TextView titleName;
	private TextView titleTime;
	
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		view = inflater.inflate(R.layout.home_fragment, null);
		
		initview();
		
		return view;
	}

	private void initview() {
		// TODO 自动生成的方法存根
		titleMenu = (Button)view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);
		
		titleName = (TextView)view.findViewById(R.id.title_name);
		this.setTitlename(titleName);
		
		titleTime = (TextView)view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO 自动生成的方法存根
		
	}
	
	

}
