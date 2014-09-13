package com.coolapps.toptube.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;

public class AlarmNotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		try {
			Log.d("tag", "Rceived");
			Intent inte = new Intent();
			inte.setClass(context, ServiceNotification.class);
			context.startService(inte);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
