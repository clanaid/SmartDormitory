package com.bailv.util;

import java.util.Timer;
import java.util.TimerTask;

import com.bailv.smartdormitory.R;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ToastShow {

	private boolean mShowTime;
	private boolean mIsShow;
	private WindowManager mWdm;
	private View mToastView;
	private Timer mTimer;
	private LayoutParams mParams;

	public ToastShow(Context context, String text, boolean showTime) {
		// TODO �Զ����ɵĹ��캯�����
		mShowTime = showTime;// ��¼Toast����ʾ��������
		mIsShow = false;// ��¼��ǰToast�������Ƿ��Ѿ�����ʾ
		mWdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		// ͨ��Toastʵ����ȡ��ǰandroidϵͳ��Ĭ��Toast��View����
		mToastView = Toast.makeText(context, text, Toast.LENGTH_SHORT)
				.getView();
		mTimer = new Timer();
		// ���ò��ֲ���
		setParams();
	}

	public ToastShow(Context context, int imgResource, String text,
			boolean showTime) {
		// TODO �Զ����ɵĹ��캯�����
		
		mShowTime = showTime;// ��¼Toast����ʾ��������
		mIsShow = false;// ��¼��ǰToast�������Ƿ��Ѿ�����ʾ
		mWdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		// ͨ�����ز��ֻ�ȡView����
		mToastView = LayoutInflater.from(context).inflate(R.layout.toast_open_dor, null);
		TextView textView = (TextView)mToastView.findViewById(R.id.rfid_user_name);
		ImageView imageView = (ImageView)mToastView.findViewById(R.id.rfid_user_img);
		textView.setText(text);
		imageView.setImageResource(imgResource);
		mTimer = new Timer();
		// ���ò��ֲ���
		setParams();
	}

	private void setParams() {
		// TODO �Զ����ɵķ������
		mParams = new WindowManager.LayoutParams();
//		mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//		mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.height = 80;
		mParams.width = 200;
		mParams.format = PixelFormat.TRANSLUCENT;
		mParams.windowAnimations = R.style.toast_view;// ���ý����˳�����Ч��
		mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
		mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		mParams.gravity = Gravity.RIGHT;
		mParams.y = -180;
		mParams.x = 5;
	}

	public static ToastShow MakeText(Context context, String text,
			boolean showTime) {
		ToastShow result = new ToastShow(context, text, showTime);
		return result;
	}
	
	public static ToastShow OpenDor(Context context, int imgResource,String text,
			boolean showTime){
		ToastShow result = new ToastShow(context, imgResource,text, showTime);
		return result;
	}

	public void show() {
		if (!mIsShow) {// ���Toastû����ʾ����ʼ������ʾ
			mIsShow = true;
			mWdm.addView(mToastView, mParams);// ������ص�windowManager��
			mTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					mWdm.removeView(mToastView);
					mIsShow = false;
				}
			}, (long) (mShowTime ? 3500 : 2000));
		}
	}
}
