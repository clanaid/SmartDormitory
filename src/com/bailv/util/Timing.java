package com.bailv.util;

import java.util.Calendar;

import android.os.SystemClock;

/**
 * 
 * 定时任务设置类
 * 
 * @author: 泓钦
 * @time: 2015年5月25日 下午1:52:53
 */
public class Timing {

	private static final int interval_24h = 60*60*24*1000;
	private static final int interval_week = 60*60*24*1000*7;

	public enum DateMode{DATE_MODE_ONCE,DATE_MODE_WEEK};
	
	long firstTime;// 开机之后到现在的运行时间(包括睡眠时间)
	long systemTime;// 系统时间

	private Calendar firstCalendar;//

	public Timing() {
		firstTime = SystemClock.elapsedRealtime();
		systemTime = System.currentTimeMillis();
	}

}
