package com.bailv.smartdormitory;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.bailv.smartdormitory.SlidingMenuFragment.ChangeFragment;
import com.bailv.smartdormitory.SmartDormitoryFragment.ShowMenuListener;
import com.bailv.util.RealTime;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;

/**
 * APP的主Activity
 * 
 * @author: 泓钦
 * @time: 2015年5月2日 下午3:53:25
 */

public class MainActivity extends Activity implements ChangeFragment,
		ShowMenuListener {

	private CanvasTransformer mTransformer;

	private Fragment fragment;
	private HomeFragment homeFragment;
	private AirFragment airFragment;
	private PlatooninsertFragment platooninsertFragment;
	private Fragment tempFragment;

	private SlidingMenu menu;

	private int fd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);

		// 资源辅助初始化
		initResources();

		// Fragment初始化
		initFragment();

		// slidingmenu动画初始化
		initAnimation();

		// 初始化slidingmenu
		initSlidingMenu();
	}

	private void initResources() {
		// TODO 自动生成的方法存根
		new Thread(RealTime.getRealTime()).start();
	}

	private void initFragment() {
		// TODO 自动生成的方法存根
		homeFragment = new HomeFragment();
		airFragment = new AirFragment();
		platooninsertFragment = new PlatooninsertFragment();

		tempFragment = fragment = homeFragment;
	}

	// slidingmenu动画变化率
	private static Interpolator interp = new Interpolator() {
		@Override
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}
	};

	/**
	 * slidingmenu动画初始化 方法返回类型：void 动画类型：向上渐动
	 */
	private void initAnimation() {
		// TODO 自动生成的方法存根
		mTransformer = new CanvasTransformer() {

			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				// TODO 自动生成的方法存根
				// canvas.translate(
				// 0,
				// canvas.getHeight()
				// * (1 - interp.getInterpolation(percentOpen)));
				canvas.scale(percentOpen, 1, 0, 0);
			}

		};
	}

	/**
	 * 初始化slidingmenu 方法返回类型：void
	 */
	private void initSlidingMenu() {
		// TODO 自动生成的方法存根

		// 设置主界面
		setContentView(R.layout.content_view);
		getFragmentManager().beginTransaction()
				.replace(R.id.ContenView_fragment, fragment).commit();

		menu = new SlidingMenu(this);

		// 设置Slidingmenu
		menu.setMenu(R.layout.slidingmenu_view);
		getFragmentManager().beginTransaction()
				.replace(R.id.slidingmenu_fragment, new SlidingMenuFragment())
				.commit();

		// 设置滑动菜单的属性值
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
		// 点击返回键关闭滑动菜单
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void change(int key) {
		// TODO 自动生成的方法存根
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
			fragment = homeFragment;
			break;
		case 4:
			fragment = homeFragment;
			break;
		default:
			break;
		}
		// 防止两次点击造成黑屏
		/*
		 * if (tempFragment != fragment) {
		 * getFragmentManager().beginTransaction()
		 * .replace(R.id.ContenView_fragment, fragment).commit(); tempFragment =
		 * fragment; }
		 */
		if (tempFragment != fragment) {
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction().setCustomAnimations(
							R.animator.slide_in_left,
							R.animator.slide_out_right);
			if (!fragment.isAdded()) { // 先判断是否被add过
				transaction.hide(tempFragment)
						.add(R.id.ContenView_fragment, fragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(tempFragment).show(fragment).commit(); // 隐藏当前的fragment，显示下一个
			}
			tempFragment = fragment;
		}

	}

	@Override
	public void show() {
		// TODO 自动生成的方法存根

		menu.showMenu();
	}

}
