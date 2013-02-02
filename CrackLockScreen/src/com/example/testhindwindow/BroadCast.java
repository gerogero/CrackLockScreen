package com.example.testhindwindow;


import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

public class BroadCast extends BroadcastReceiver{

	PngView pv;
	HelpClass hv;
	WindowManager.LayoutParams pm;
	int windowX;
	int windowY;
	WindowManager wm;
	KeyguardManager km;
	int starting1;
	int starting2;
	
	int stateInt=0;
	int serviceCnt=0;
	
	Context context;
	Intent serviceIt;

	public BroadCast(Context cnt){
		}
	
	@Override
	public void onReceive(Context cnt, Intent it) {
		
//		serviceIt=new Intent(cnt,ScreenService.class);
		Intent serviceIt=new Intent(cnt,ScreenService.class);
		
		String action=it.getAction();
		
		if(action!=null){
			if(action.equals(Intent.ACTION_SCREEN_ON)){
				if(isServiceRunning(cnt)){
				}else{
						cnt.startService(serviceIt);
				}
			}else if(action.equals(Intent.ACTION_SCREEN_OFF)){
				if(isServiceRunning(cnt)){
					cnt.stopService(serviceIt);
				}
			}
		}
	}

	private boolean isServiceRunning(Context cnt) {
		ActivityManager am=(ActivityManager) cnt.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningservice=am.getRunningServices(Integer.MAX_VALUE);
		for(RunningServiceInfo i:runningservice){
			if(i.service.getClassName().equals("com.example.testhindwindow.ScreenService")){
				return true;
			}
		}
		return false;
	}


//	public void createWakeupFlag(Context cnt){
//		ContentValues cv=new ContentValues();
//		cv.put("keyWord", "wakeupFlag");
//		cv.put("passWord", "RingingStart");
//		cv.put("volume", 0.0f);
//		ContentResolver cr=cnt.getContentResolver();
//        Intent intent=new Intent();
//		intent.setData(Uri.parse("content://com.exsample.sqlite.Dataprovider.eventDB"));
//        cr.insert(intent.getData(), cv);
//    }
}