package com.bailv.smartdormitory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ModeFragment extends SmartDormitoryFragment {

	private Button titleMenu;
	private TextView titleName;
	private TextView titleTime;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		view = inflater.inflate(R.layout.mode_fragment, null);
		initView();
		return view;
	}

	private void initView() {
		// TODO 自动生成的方法存根
		titleMenu = (Button)view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);
		
		titleName = (TextView)view.findViewById(R.id.title_name);
		this.setTitlename(titleName,"宿舍模式");
		
		titleTime = (TextView)view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);

	}
}
