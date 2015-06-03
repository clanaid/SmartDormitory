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
 * ����Fragment
 * 
 * @author: ����
 * @time: 2015��5��28�� ����1:32:34
 */
public class SafeFragment extends SerialFragment {
	
	private static final int _24H = 1000*60*60*24;//24Сʱ
	
	private View view;
	
	private Button titleMenu;
	private TextView titleName;
	private TextView titleTime;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		view = inflater.inflate(R.layout.safe_fragment, null);
		initView();
		return view;
	}
	
	private void initView() {
		// TODO �Զ����ɵķ������
		titleMenu = (Button)view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);
		
		titleName = (TextView)view.findViewById(R.id.title_name);
		this.setTitlename(titleName,"���ᰲ��");
		
		titleTime = (TextView)view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);

	}

	@Override
	public void onEventMainThread(SerialCmd cmd) {
		// TODO �Զ����ɵķ������
		
	}

}
