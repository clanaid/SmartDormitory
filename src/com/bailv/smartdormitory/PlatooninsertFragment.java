package com.bailv.smartdormitory;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bailv.util.CheckSwitchButton;
import com.bailv.util.CircleButton;
import com.bailv.util.ControlCmd;
import com.bailv.util.EventsPost;
import com.bailv.util.EventsPost.EventType;
import com.bailv.util.SerialCmd;
import com.bailv.util.ControlCmd.CmdName;

/**
 * 
 * 电器控制Fragment
 * 
 * @author: 泓钦
 * @time: 2015年5月20日 下午2:01:33
 */
public class PlatooninsertFragment extends SerialFragment {

	private View view;

	private CheckSwitchButton two_checkSwitchButton1;
	private CheckSwitchButton two_checkSwitchButton2;
	private CheckSwitchButton two_checkSwitchButton3;
	private boolean pushCheckState1 = false, pushCheckState2 = false,
			pushCheckState3 = false;

	private ImageView two_image_1;
	private ImageView two_image_2;
	private ImageView two_image_3;

	private TextView two_tag_1;
	private TextView two_tag_2;
	private TextView two_tag_3;

	private Drawable drawable;
	private Resources resources;

	private Button titleMenu;
	private TextView titleName;
	private TextView titleTime;

	private CircleButton lightButton;

	private TagDialog dialog;
	private TextView tempView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		view = inflater.inflate(R.layout.platooninser, null);

		initView();
		initListener();

		return view;
	}

	private void ToastMakeText(String text) {
		Toast.makeText(getActivity(), text, 100).show();
		;
	}

	private void initListener() {
		// TODO 自动生成的方法存根

		two_checkSwitchButton1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO 自动生成的方法存根
						if(!PlatooninsertFragment.this.pushCheckState1)
							swtichChange('1', isChecked);
						else
							PlatooninsertFragment.this.pushCheckState1 = false;
					}

				});
		two_checkSwitchButton2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO 自动生成的方法存根
						if(!PlatooninsertFragment.this.pushCheckState2)
							swtichChange('2', isChecked);
						else
							PlatooninsertFragment.this.pushCheckState2 = false;
					}
				});
		two_checkSwitchButton3
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO 自动生成的方法存根
						if(!PlatooninsertFragment.this.pushCheckState3)
							swtichChange('3', isChecked);
						else
							PlatooninsertFragment.this.pushCheckState3 = false;
					}
				});

		two_tag_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				dialog(two_tag_1);
			}
		});

		two_tag_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				dialog(two_tag_2);
			}
		});

		two_tag_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				dialog(two_tag_3);
			}
		});
	}

	private void swtichChange(char which, boolean isChecked) {
		changeImage(which, isChecked);
		switchCmdSend(which, isChecked);
	}

	private void changeImage(char which, boolean action) {
		switch (which) {
		case 'A':
			setSwitchImage(two_image_1, action);
			setSwitchImage(two_image_2, action);
			setSwitchImage(two_image_3, action);
			break;
		case '1':
			setSwitchImage(two_image_1, action);
			break;
		case '2':
			setSwitchImage(two_image_2, action);
			break;
		case '3':
			setSwitchImage(two_image_3, action);
			break;
		default:
			break;
		}
	}

	private void setSwitchImage(ImageView imageView, boolean is_chagned) {
		if (is_chagned) {
			resources = getResources();
			drawable = resources.getDrawable(R.drawable.switch_background);
			imageView.setBackgroundDrawable(drawable);
			imageView.setImageResource(R.drawable.chazuo_san);
		} else {
			resources = getResources();
			drawable = resources.getDrawable(R.drawable.switch_unbackground);
			imageView.setBackgroundDrawable(drawable);
			imageView.setImageResource(R.drawable.chazuo_san_un);
		}
	}

	private void switchCmdSend(char which, boolean action) {
		Log.d("sendWhich", which + "");
		switch (which) {
		case 'A':
			if (action) {
				this.SendCmd(ControlCmd.getCmd(CmdName.OPEN_ALL_SWITCH));
			} else {
				this.SendCmd(ControlCmd.getCmd(CmdName.CLOSE_ALL_SWITCH));
			}
			break;
		case '1':
			if (action) {
				this.SendCmd(ControlCmd.getCmd(CmdName.OPEN_FIRST_SWITCH));
			} else {
				this.SendCmd(ControlCmd.getCmd(CmdName.CLOSE_FIRST_SWITCH));
			}
			break;
		case '2':
			if (action) {
				this.SendCmd(ControlCmd.getCmd(CmdName.OPEN_SECOND_SWITCH));
			} else {
				this.SendCmd(ControlCmd.getCmd(CmdName.CLOSE_SECOND_SWITCH));
			}
			break;
		case '3':
			if (action) {
				this.SendCmd(ControlCmd.getCmd(CmdName.OPEN_THIRD_SWITCH));
			} else {
				this.SendCmd(ControlCmd.getCmd(CmdName.CLOSE_THIRD_SWITCH));
			}
			break;
		default:
			break;
		}
	}

	private void dialog(TextView tag) {
		dialog = new TagDialog(getActivity());
		final EditText editText = (EditText) dialog.getEditText();
		tempView = tag;
		dialog.setOnPositiveListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				dialog.dismiss();
				String tagString = editText.getText().toString();
				if (!tagString.isEmpty()) {
					tempView.setText(tagString);
					tempView.setTextSize(40);
					tempView.setTextColor(getResources().getColor(
							R.color.platooninser_switch_tag_set));
				}
			}
		});
		dialog.setOnNegativeListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private void initView() {
		// TODO 自动生成的方法存根

		two_checkSwitchButton1 = (CheckSwitchButton) view
				.findViewById(R.id.platooninsert_two_1);
		two_checkSwitchButton2 = (CheckSwitchButton) view
				.findViewById(R.id.platooninsert_two_2);
		two_checkSwitchButton3 = (CheckSwitchButton) view
				.findViewById(R.id.platooninsert_two_3);

		two_image_1 = (ImageView) view
				.findViewById(R.id.platooninsert_two_image_1);
		two_image_2 = (ImageView) view
				.findViewById(R.id.platooninsert_two_image_2);
		two_image_3 = (ImageView) view
				.findViewById(R.id.platooninsert_two_image_3);

		two_checkSwitchButton1.setChecked(false);
		two_checkSwitchButton2.setChecked(false);
		two_checkSwitchButton3.setChecked(false);

		two_tag_1 = (TextView) view.findViewById(R.id.platooninsert_two_tag_1);
		two_tag_2 = (TextView) view.findViewById(R.id.platooninsert_two_tag_2);
		two_tag_3 = (TextView) view.findViewById(R.id.platooninsert_two_tag_3);

		lightButton = (CircleButton) view.findViewById(R.id.light_power);
		
		pushCheckState1 = false;
		pushCheckState2 = false;
		pushCheckState3 = false;

		titleMenu = (Button) view.findViewById(R.id.title_menu_button);
		this.setTitleMenuButton(titleMenu);

		titleName = (TextView) view.findViewById(R.id.title_name);
		this.setTitlename(titleName, "电器控制");

		titleTime = (TextView) view.findViewById(R.id.title_time);
		this.setTitleTime(titleTime);

	}

	@Override
	public void onEventMainThread(SerialCmd cmd) {
		// TODO 自动生成的方法存根
		String cmdString = cmd.getCmd();
	}

	public void onEventMainThread(EventsPost eventcmd) {
		// TODO 自动生成的方法存根
		String cmd = eventcmd.getCmd();
		EventType eventType = eventcmd.getEventType();
		switch (eventType) {
		case DDPUSH:
			dealPush(cmd);
			break;

		default:
			break;
		}
	}

	private void dealPush(String pushcmd) {
		char state = pushcmd.charAt(0);
		boolean action;
		switch (state) {
		case 'S':
			action = (pushcmd.charAt(2) == 'O') ? true : false;
			dealswitch(pushcmd.charAt(1), action);
			break;
		case 'L':
			break;
		default:
			break;
		}
	}

	private void dealswitch(char which, boolean action) {
		switch (which) {
		case 'A':
			swtichChange(which, action);
			this.pushCheckState1 = true;
			this.pushCheckState2 = true;
			this.pushCheckState3 = true;
			two_checkSwitchButton1.setChecked(action);
			two_checkSwitchButton2.setChecked(action);
			two_checkSwitchButton3.setChecked(action);
			break;
		case '1':
			swtichChange(which, action);
			two_checkSwitchButton1.setChecked(action);
			break;
		case '2':
			swtichChange(which, action);
			two_checkSwitchButton2.setChecked(action);
			break;
		case '3':
			swtichChange(which, action);
			two_checkSwitchButton3.setChecked(action);
			break;
		default:
			break;
		}
	}
}

/**
 * 
 * 插座标签设置窗口类
 * 
 * @author: 泓钦
 * @time: 2015年6月15日 上午1:24:58
 */
class TagDialog extends Dialog {

	private EditText editText;
	private Button positiveButton, negativeButton;

	public TagDialog(Context context) {
		super(context, R.style.dialog);
		// TODO 自动生成的构造函数存根
		setCustomDialog();
	}

	private void setCustomDialog() {
		View mView = LayoutInflater.from(getContext()).inflate(
				R.layout.tag_dialog, null);
		editText = (EditText) mView.findViewById(R.id.tag_edit);
		positiveButton = (Button) mView.findViewById(R.id.tag_positive);
		negativeButton = (Button) mView.findViewById(R.id.tag_neative);
		super.setContentView(mView);
	}

	public View getEditText() {
		return editText;
	}

	@Override
	public void setContentView(int layoutResID) {
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
	}

	@Override
	public void setContentView(View view) {
	}

	/**
	 * 确定键监听器
	 * 
	 * @param listener
	 */
	public void setOnPositiveListener(View.OnClickListener listener) {
		positiveButton.setOnClickListener(listener);
	}

	/**
	 * 取消键监听器
	 * 
	 * @param listener
	 */
	public void setOnNegativeListener(View.OnClickListener listener) {
		negativeButton.setOnClickListener(listener);
	}

}
