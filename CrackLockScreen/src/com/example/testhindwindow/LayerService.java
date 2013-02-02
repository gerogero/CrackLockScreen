package com.example.testhindwindow;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class LayerService extends Service{

	IntentFilter filter;
	IntentFilter filter1;
	int stateInt=0;
	BroadCast br;
	
	@Override
	public void onCreate() {
		super.onCreate();

		br=new BroadCast(this);
		
		filter=new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter1=new IntentFilter(Intent.ACTION_SCREEN_OFF);

		try{
			registerReceiver(br , filter);
			registerReceiver(br , filter1);
		}catch(RuntimeException e	){
		}		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}