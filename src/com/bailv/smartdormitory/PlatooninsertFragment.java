package com.bailv.smartdormitory;


import com.bailv.util.CheckSwitchButton;
import com.bailv.util.SerialCmd;
import com.bailv.util.SerialThread;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * ��������Fragment
 * 
 * @author: ����
 * @time: 2015��5��20�� ����2:01:33
 */
public class PlatooninsertFragment extends SerialFragment{

	private View view;

	private CheckSwitchButton only_checkSwitchButton1;
	private CheckSwitchButton only_checkSwitchButton2;
	private CheckSwitchButton only_checkSwitchButton3;
	private CheckSwitchButton two_checkSwitchButton1;
	private CheckSwitchButton two_checkSwitchButton2;
	private CheckSwitchButton two_checkSwitchButton3;

	private ImageView only_image_1;
	private ImageView only_image_2;
	private ImageView only_image_3;
	private ImageView two_image_1;
	private ImageView two_image_2;
	private ImageView two_image_3;

	private Drawable drawable;
	private Resources resources;

	private Button titleMenu;
	private TextView titleName;
	private TextView titleTime;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		view = inflater.inflate(R.layout.switch_six, null);

		initView();
		initListener();

		return view;
	}

	private void changeImage(ImageView imageView, boolean is_chagned, int type) {
		switch (type) {
		case 1:
			if (is_chagned) {
				resources = getResources();
				drawable = resources.getDrawable(R.drawable.switch_background);
				imageView.setBackgroundDrawable(drawable);
				imageView.setImageResource(R.drawable.chazuo_san);
			} else {
				resources = getResources();
				drawable = resources
						.getDrawable(R.drawable.switch_unbackground);
				imageView.setBackgroundDrawable(drawable);
				imageView.setImageResource(R.drawable.chazuo_san_un);
			}
			break;
		case 0:
			if (is_chagned) {
				resources = getResources();
				drawable = resources.getDrawable(R.drawable.switch_background);
				imageView.setBackgroundDrawable(drawable);
				imageView.setImageResource(R.drawable.chazuo_dan);
			} else {
				resources = getResources();
				drawable = resources
						.getDrawable(R.drawable.switch_unbackground);
				imageView.setBackgroundDrawable(drawable);
				imageView.setImageResource(R.drawable.chazuo_dan_un);
			}
			break;

		default:
			break;
		}
	}

	private void ToastMakeText(String text) {
		Toast.makeText(getActivity(), text, 100).show();;
	}

	private void initListener() {
		// TODO �Զ����ɵķ������

		only_checkSwitchButton1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO �Զ����ɵķ������
						if (isChecked) {
							ToastMakeText("��");
							changeImage(only_image_1, true, 0);
						} else {
							ToastMakeText("�ر�");
							changeImage(only_image_1, false, 0);
						}
					}
				});
		only_checkSwitchButton2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO �Զ����ɵķ������
						if (isChecked) {
							ToastMakeText("��");
							changeImage(only_image_2, true, 0);
						} else {
							ToastMakeText("�ر�");
							changeImage(only_image_2, false, 0);
						}
					}
				});
		only_checkSwitchButton3
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO �Զ����ɵķ������
						if (isChecked) {
							ToastMakeText("��");
							changeImage(only_image_3, true, 0);
						} else {
							ToastMakeText("�ر�");
							changeImage(only_image_3, false, 0);
						}
					}
				});
		two_checkSwitchButton1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO �Զ����ɵķ������
						if (isChecked) {
							ToastMakeText("��");
							changeImage(two_image_1, true, 1);
						} else {
							ToastMakeText("�ر�");
							changeImage(two_image_1, false, 1);
						}
					}
				});
		two_checkSwitchButton2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO �Զ����ɵķ������
						if (isChecked) {
							ToastMakeText("��");
							changeImage(two_image_2, true, 1);
						} else {
							ToastMakeText("�ر�");
							changeImage(two_image_2, false, 1);
						}
					}
				});
		two_checkSwitchButton3
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO �Զ����ɵķ������
						if (isChecked) {
							ToastMakeText("��");
							changeImage(two_image_3, true, 1);
							PlatooninsertFragment.this.SendCmd("{0000000001}");
						} else {
							ToastMakeText("�ر�");
							changeImage(two_image_3, false, 1);
						}
					}
				});

	}

	private void initView() {
		// TODO �Զ����ɵķ������
		only_checkSwitchButton1 = (CheckSwitchButton) view
				.findViewById(R.id.platooninsert_only_1);
		only_checkSwitchButton2 = (CheckSwitchButton) view
				.findViewById(R.id.platooninsert_only_2);
		only_checkSwitchButton3 = (CheckSwitchButton) view
				.findViewById(R.id.platooninsert_only_3);
		two_checkSwitchButton1 = (CheckSwitchButton) view
				.findViewById(R.id.platooninsert_two_1);
		two_checkSwitchButton2 = (CheckSwitchButton) view
				.findViewById(R.id.platooninsert_two_2);
		two_checkSwitchButton3 = (CheckSwitchButton) view
				.findViewById(R.id.platooninsert_two_3);

		only_image_1 = (ImageView) view
				.findViewById(R.id.platooninsert_onle_image_1);
		only_image_2 = (ImageView) view
				.findViewById(R.id.platooninsert_onle_image_2);
		only_image_3 = (ImageView) view
				.findViewById(R.id.platooninsert_onle_image_3);
		two_image_1 = (ImageView) view
				.findViewById(R.id.platooninsert_two_image_1);
		two_image_2 = (ImageView) view
				.findViewById(R.id.platooninsert_two_image_2);
		two_image_3 = (ImageView) view
				.findViewById(R.id.platooninsert_two_image_3);

		only_checkSwitchButton1.setChecked(false);
		only_checkSwitchButton2.setChecked(false);
		only_checkSwitchButton3.setChecked(false);
		two_checkSwitchButton1.setChecked(false);
		two_checkSwitchButton2.setChecked(false);
		two_checkSwitchButton3.setChecked(false);
		
		titleMenu = (Button)view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);
		
		titleName = (TextView)view.findViewById(R.id.title_name);
		this.setTitlename(titleName,"��������");
		
		titleTime = (TextView)view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);

	}

	@Override
	public void onEventMainThread(SerialCmd cmd) {
		// TODO �Զ����ɵķ������
		String cmdString = cmd.getCmd();
	}

	

}
