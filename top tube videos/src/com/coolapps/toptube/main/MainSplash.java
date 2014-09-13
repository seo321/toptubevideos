package com.coolapps.toptube.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coolapps.toptube.utils.Configuration;
import com.coolapps.toptube.utils.RefreshContentsXML;
import com.coolapps.toptube.utils.ServiceNotification;
import com.coolapps.toptube.*;

public class MainSplash extends Activity {

	protected static final int ERROR = 25;
	ProgressBar barra;
	RefreshContentsXML refresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_splash);
		refresh = new RefreshContentsXML();
		
		Intent serviceIntent = new Intent();

		serviceIntent.setAction("coolapps.myaction");

sendBroadcast(serviceIntent);		// cntx=this;
		barra = (ProgressBar) findViewById(R.id.BarraProgreso);

		if (Configuration.XMLREADER) {
			refresh.refreshContentsXML(this, null, barra, mHandler);
		} else {
			refresh.RefrechContentsYoutube(this, barra, mHandler);
		}
	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == ERROR) {
				Toast.makeText(getApplicationContext(), "Error de conexión",
						Toast.LENGTH_LONG).show();
				finish();
			} else {
				Log.w("test", "Enviando la activity");
				Intent i = new Intent(MainSplash.this, Main.class);
				i.putExtra("Model", refresh.model);
				// i.putParcelableArrayListExtra("Videos", VideosList);
				startActivity(i);
				finish();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}

		}
	};

}
