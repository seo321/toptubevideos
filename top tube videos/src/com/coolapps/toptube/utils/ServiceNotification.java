package com.coolapps.toptube.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.coolapps.toptube.R;
import com.coolapps.toptube.main.MainSplash;


public class ServiceNotification extends Service {
	private Context cont;
	private Timer tim;
	private RefreshContentsXML refresh;
    private DataBase db;
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		try{
		cont = this;
		Log.w("Service112", "onCreate");
	
		
//		tim = new Timer();
//		tim.schedule(new timer(), Configuration.TIMER_NOTIFICATION);
		db= new DataBase(cont);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.w("Service112", "onStartCommand");
		alarmMgr = (AlarmManager)cont.getSystemService(Context.ALARM_SERVICE);
		Intent intet = new Intent(cont, AlarmNotificationReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(cont, 0, intet, 0);

		alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
		        SystemClock.elapsedRealtime() +
		       Configuration.TIMER_NOTIFICATION, alarmIntent);

		Log.w("Service112", "timer");

		refresh = new RefreshContentsXML();
		if (Configuration.XMLREADER) {
			refresh.refreshContentsXML(cont, null, null, mHandler);
		} else {
			refresh.RefrechContentsYoutube(cont, null, mHandler);
		}
	
		return super.onStartCommand(intent, flags, startId);
	}

	public void notification(int news) {

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.notif)
				.setContentTitle("Top Tube Vídeos")
				.setContentText("Hay "+news+" vídeos nuevos!");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainSplash.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainSplash.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) cont
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(22, mBuilder.build());

	}

	class timer extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			tim.schedule(new timer(), Configuration.TIMER_NOTIFICATION);
			Log.w("Service112", "timer");

			refresh = new RefreshContentsXML();
			if (Configuration.XMLREADER) {
				refresh.refreshContentsXML(cont, null, null, mHandler);
			} else {
				refresh.RefrechContentsYoutube(cont, null, mHandler);
			}
		}

	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			int news=0;
			for(int i=0;i<refresh.model.bloq.size();i++){
				for(int j=0;j<refresh.model.bloq.get(i).contents.size();j++){
					if(!db.isNotNews(refresh.model.bloq.get(i).contents.get(j).url))
					{
					news++;
					Log.i("Service112"," url: "+refresh.model.bloq.get(i).contents.get(j).url+" "+db.isNotNews(refresh.model.bloq.get(i).contents.get(j).url));
					}
				}
			}
			if(news!=0){
			notification(news);
			}
		}
	};

}
