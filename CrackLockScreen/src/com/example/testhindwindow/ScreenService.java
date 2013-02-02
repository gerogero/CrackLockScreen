package com.example.testhindwindow;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

public class ScreenService extends Service{

	WindowManager wm;
	int windowX;
	int windowY;
	WindowManager.LayoutParams pm;
	PngView pv;
	HelpClass hc;

	int stateInt1;
	int startPV;

	String startOnScreenEvent;


	@Override
	public void onCreate() {
		super.onCreate();
		stateInt1=0;

		wm=(WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display di=wm.getDefaultDisplay();
		windowX=di.getWidth();
		windowY=di.getHeight();

		pm=new WindowManager.LayoutParams(
				windowX,
				windowY,
				WindowManager.LayoutParams.TYPE_SYSTEM_ERROR|
				//WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
				//WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
				WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER,
				PixelFormat.TRANSLUCENT
				);

		pm.screenOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

		pv=new PngView(this);
		hc=new HelpClass(this);
		telephoneState();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		if(stateInt1==1){
			removePngView();
		}else if(stateInt1==2){
			removeHelpClass();
		}
		telmane.listen(telListener, PhoneStateListener.LISTEN_NONE);
		super.onDestroy();
	}


	void setPV(int i){
		if(i==1){
			if(stateInt1==0){
				stateInt1=1;
				wm.addView(pv, pm);
			}
		}
	}

	TelephonyManager telmane;
	PhoneStateListener telListener;

	private void telephoneState() {
		telListener=new PhoneStateListener(){

			@Override
			public void onCallStateChanged(int state, String number) {
				int i=0;
				switch(state){
				case TelephonyManager.CALL_STATE_RINGING:
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					break;
				case TelephonyManager.CALL_STATE_IDLE:
					i=1;
					break;
				default:
					i=1;
					break;
				}
				setPV(i);
			}
		};
		telmane= (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		telmane.listen(telListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	public void removePngView(){
		wm.removeView(pv);
		pv.destroyDrawingCache();
		pv=null;
		stateInt1=0;
	}

	public void removeHelpClass(){
		wm.removeView(hc.helpView());
		hc.helpView().destroyDrawingCache();
		hc=null;
		stateInt1=0;
	}

	public void helpMe(){
		wm.removeView(pv);
		wm.addView(hc.helpView(), pm);
		stateInt1=2;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}