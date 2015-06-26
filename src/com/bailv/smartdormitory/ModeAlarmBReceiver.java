package com.bailv.smartdormitory;

import com.bailv.util.EventsPost;
import com.bailv.util.EventsPost.EventType;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ModeAlarmBReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO �Զ����ɵķ������
		String action = intent.getAction();

		switch (action) {
		case "sleep":
			EventsPost slepalarmCmd = new EventsPost("",EventType.SLEEPALARM);
			break;
			
		case "out":
			EventsPost outalarmCmd = new EventsPost("",EventType.OUTALARM);
			break;
			
		case "getup":
			EventsPost getupalarmCmds = new EventsPost("",EventType.GETUPALARM);
			break;

		default:
			break;
		}
	}

}
