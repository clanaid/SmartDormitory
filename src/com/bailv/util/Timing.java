package com.bailv.util;

import java.util.Calendar;

import android.os.SystemClock;

/**
 * 
 * ��ʱ����������
 * 
 * @author: ����
 * @time: 2015��5��25�� ����1:52:53
 */
public class Timing {

	private static final int interval_24h = 60*60*24*1000;
	private static final int interval_week = 60*60*24*1000*7;

	public enum DateMode{DATE_MODE_ONCE,DATE_MODE_WEEK};
	
	long firstTime;// ����֮�����ڵ�����ʱ��(����˯��ʱ��)
	long systemTime;// ϵͳʱ��

	private Calendar firstCalendar;//

	public Timing() {
		firstTime = SystemClock.elapsedRealtime();
		systemTime = System.currentTimeMillis();
	}

}
