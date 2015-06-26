package com.bailv.smartdormitory;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.bailv.smartdormitory.SerialFragment.SerialSendLinstener;
import com.bailv.smartdormitory.SlidingMenuFragment.ChangeFragment;
import com.bailv.smartdormitory.SmartDormitoryFragment.ShowMenuListener;
import com.bailv.util.RealTime;
import com.bailv.util.SerialThread;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;

import de.greenrobot.event.EventBus;

/**
 * APP����Activity
 * 
 * @author: ����
 * @time: 2015��5��2�� ����3:53:25
 */

public class MainActivity extends Activity implements ChangeFragment,
		ShowMenuListener, SerialSendLinstener {

	private CanvasTransformer mTransformer;

	private SmartDormitoryFragment fragment;
	private HomeFragment homeFragment;
	private AirFragment airFragment;
	private PlatooninsertFragment platooninsertFragment;
	private SafeFragment safeFragment;
	private ModeFragment modeFragment;
	private SmartDormitoryFragment tempFragment;

	private SlidingMenu menu;

	private Intent serialIntent;
	private SerialService serialService;
	
	private Intent startSrv;

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO �Զ����ɵķ������

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			// TODO �Զ����ɵķ������
			serialService = ((SerialService.LocalBinder) binder).getService();
			Log.d("Service", "SerivceOK");
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);

		// ��Դ������ʼ��
		initResources();

		// �����ʼ��
		initService();

		// Fragment��ʼ��
		initFragment();

		// slidingmenu������ʼ��
		initAnimation();

		// ��ʼ��slidingmenu
		initSlidingMenu();

	}

	private void addObserver() {
		// TODO �Զ����ɵķ������

	}

	private void initService() {
		// TODO �Զ����ɵķ������
		serialIntent = new Intent(MainActivity.this, SerialService.class);
		bindService(serialIntent, serviceConnection, BIND_AUTO_CREATE);
		
		startSrv = new Intent(this,
				DDPushService.class);
		startService(startSrv);
	}

	@Override
	protected void onDestroy() {
		// TODO �Զ����ɵķ������
		unbindService(serviceConnection);
		stopService(startSrv);
		super.onDestroy();
	}

	private void initResources() {
		// TODO �Զ����ɵķ������
		new Thread(RealTime.getRealTime()).start();
	}

	private void initFragment() {
		// TODO �Զ����ɵķ������
		homeFragment = new HomeFragment();
		airFragment = new AirFragment();
		platooninsertFragment = new PlatooninsertFragment();
		safeFragment = new SafeFragment();
		modeFragment = new ModeFragment();
		tempFragment = fragment = null;
		addFragment(airFragment);
		addFragment(platooninsertFragment);
		addFragment(safeFragment);
		addFragment(modeFragment);
		addFragment(homeFragment);

	}

	// slidingmenu�����仯��
	private static Interpolator interp = new Interpolator() {
		@Override
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}
	};

	/**
	 * slidingmenu������ʼ�� �����������ͣ�void �������ͣ����Ͻ���
	 */
	private void initAnimation() {
		// TODO �Զ����ɵķ������
		mTransformer = new CanvasTransformer() {

			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				// TODO �Զ����ɵķ������
				// canvas.translate(
				// 0,
				// canvas.getHeight()
				// * (1 - interp.getInterpolation(percentOpen)));
				canvas.scale(percentOpen, 1, 0, 0);
			}

		};
	}

	/**
	 * ��ʼ��slidingmenu �����������ͣ�void
	 */
	private void initSlidingMenu() {
		// TODO �Զ����ɵķ������

		// ����������
		setContentView(R.layout.content_view);
		/*getFragmentManager().beginTransaction()
				.add(R.id.ContenView_fragment, fragment).commit();*/

		menu = new SlidingMenu(this);

		// ����Slidingmenu
		menu.setMenu(R.layout.slidingmenu_view);
		getFragmentManager().beginTransaction()
				.replace(R.id.slidingmenu_fragment, new SlidingMenuFragment())
				.commit();

		// ���û����˵�������ֵ
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.setBehindWidth(110);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setBehindCanvasTransformer(mTransformer);

	}

	@Override
	public void onBackPressed() {
		// ������ؼ��رջ����˵�
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void change(int key) {
		// TODO �Զ����ɵķ������
		switch (key) {
		case 0:
			fragment = homeFragment;
			break;
		case 1:
			fragment = platooninsertFragment;
			break;
		case 2:
			fragment = airFragment;
			break;
		case 3:
			fragment = safeFragment;
			break;
		case 4:
			fragment = modeFragment;
			break;
		default:
			break;
		}
		if (tempFragment != fragment) {
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction().setCustomAnimations(
							R.animator.slide_in_left,
							R.animator.slide_out_right);
			if (!fragment.isAdded()) { // ���ж��Ƿ�add��
				transaction.hide(tempFragment)
						.add(R.id.ContenView_fragment, fragment).commit(); // ���ص�ǰ��fragment��add��һ����Activity��
			} else {
				transaction.hide(tempFragment).show(fragment).commit(); // ���ص�ǰ��fragment����ʾ��һ��
			}
			tempFragment = fragment;
		}
		menu.showContent();

	}

	private void addFragment(SmartDormitoryFragment fragment) {
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		if (this.tempFragment == null) {
			transaction.add(R.id.ContenView_fragment, fragment).commit();
		} else {
			transaction.hide(tempFragment)
					.add(R.id.ContenView_fragment, fragment).commit();
		}
		this.tempFragment = fragment;
	}

	@Override
	public void show() {
		// TODO �Զ����ɵķ������
		menu.showMenu();
	}

	@Override
	public void Send(String cmd) {
		// TODO �Զ����ɵķ������
		if (serialService != null)
			serialService.SerialSend(cmd);
	}

}
