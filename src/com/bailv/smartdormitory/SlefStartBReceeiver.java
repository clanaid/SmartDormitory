package com.bailv.smartdormitory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SlefStartBReceeiver extends BroadcastReceiver {

	static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO 自动生成的方法存根
		if (intent.getAction().equals(ACTION)) {
			Intent sayHelloIntent = new Intent(context, MainActivity.class);
			sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			context.startActivity(sayHelloIntent);
		}
	}

}
