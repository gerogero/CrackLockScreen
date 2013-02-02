package com.example.testhindwindow;

import java.util.ArrayList;
import java.util.Random;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

class PngView extends View{

	private float volume;
	Context cnt;
	int flagFrom=0;
	
	ScreenService ss;

	Paint paint;
	Bitmap[] bmp;
	MediaPlayer oggplayer;
	
	/*過去タッチした位置を記録し、再描画してbmpを表示する*/
	ArrayList<Integer> positionX;
	ArrayList<Integer> positionY;
	ArrayList<Integer> num4bmp;
	
	ArrayList<Integer> passNum;
	ArrayList<Integer> inputNum;
	
	int flag;
	int liftingFlag=0;

	/*画面サイズを取得し入れている、widthThは三分割した際の大きさ*/
	float width;
	float height;
	float widthTh;
	float heightTh;
	Context serviceContext;
	
	AudioManager audioManager;
	float maxVolume;
	float nowVolume;
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		int rndNum;
		int bmpWidth;
		int bmpHeight;
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			flag=flag+1;

			Random rnd=new Random();
			rndNum=rnd.nextInt(8);
			bmpWidth=bmp[rndNum].getWidth();
			bmpHeight=bmp[rndNum].getHeight();

			num4bmp.add(rndNum);
			positionX.add((int) event.getX()-bmpWidth/2);
			positionY.add((int) event.getY()- bmpHeight/2);
			inputNum.add(getNumPosition(event));

			oggplayer.seekTo(0);
			oggplayer.start();

			if(inputNum.size()==passNum.size()){
				for(int i=0;i<passNum.size();i++){
					if(passNum.get(i)!=inputNum.get(i)){
						liftingFlag=0;
						break;
					}
					liftingFlag=1;
				}
			}
			invalidate();
		}
		return false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if(liftingFlag==1){
			flag=0;
			liftingFlag=0;
			//ss.removePngView();
			ss.stopSelf();
		}else if(liftingFlag==0){
			if(flag==0){
				width=canvas.getWidth();
				height=canvas.getHeight();
				widthTh=width/3;
				heightTh=height/3;
			}else if(flag>=1&&flag<50){
				for(int i=0;i<flag;i++){
					canvas.drawBitmap(bmp[num4bmp.get(i)], positionX.get(i), positionY.get(i),paint);
				}
			}else if(flag>=50){
				ss.helpMe();
			}
		}
	}

	
	public void init(Context context){
		
		ss=(ScreenService) context;
		paint=new Paint();

		positionX=new ArrayList<Integer>();
		positionY=new ArrayList<Integer>();
		num4bmp=new ArrayList<Integer>();
		
		passNum=new ArrayList<Integer>();
		inputNum=new ArrayList<Integer>();
		
		bmp=new Bitmap[8];
		bmp[0]=BitmapFactory.decodeResource(context.getResources(), R.drawable.a);
		bmp[1]=BitmapFactory.decodeResource(context.getResources(), R.drawable.b);
		bmp[2]=BitmapFactory.decodeResource(context.getResources(), R.drawable.c);
		bmp[3]=BitmapFactory.decodeResource(context.getResources(), R.drawable.d);
		bmp[4]=BitmapFactory.decodeResource(context.getResources(), R.drawable.e);
		bmp[5]=BitmapFactory.decodeResource(context.getResources(), R.drawable.f);
		bmp[6]=BitmapFactory.decodeResource(context.getResources(), R.drawable.g);
		bmp[7]=BitmapFactory.decodeResource(context.getResources(), R.drawable.h);
		
		audioManager=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
		nowVolume=audioManager.getStreamVolume(AudioManager.STREAM_RING);
		volume=nowVolume/maxVolume;
	
		oggplayer=MediaPlayer.create(context, R.raw.crack);
		oggplayer.setVolume(volume, volume);

		
		cnt=context;
	}
	
	public PngView(Context cnt) {
		super(cnt);
		init(cnt);
		getPassNum(cnt);
	}


	private void getPassNum(Context cnt) {
		
		final String[] columnsList={"passWord"};
		final String where="keyWord LIKE ?";
		final String keyWord="keyWord";

		Intent intent=new Intent();
		intent.setData(Uri.parse("content://com.exsample.sqlite.Dataprovider.eventDB/"));
		
		ContentResolver cr=cnt.getContentResolver();
		Cursor c=cr.query(intent.getData(), columnsList, where, new String[]{keyWord}, null);		
		
		String allNum;
		String[] partNum;
		
		if(c.moveToFirst()){
			do{
				allNum=c.getString(0);
				partNum=allNum.split(",");
				
				for(String tmp:partNum){
					passNum.add(Integer.parseInt(tmp));
				}
			}while(c.moveToNext());
		}		
	}
	
	int getNumPosition(MotionEvent event){
		int position;
		if(event.getX()<=widthTh){
			if(event.getY()<=heightTh){
				position=1;
			}else if(event.getY()<=heightTh*2){
				position=4;
			}else{
				position=7;
			}
		}else if(event.getX()<=widthTh*2){
			if(event.getY()<=heightTh){
				position=2;
			}else if(event.getY()<=heightTh*2){
				position=5;
			}else{
				position=8;
			}
		}else{
			if(event.getY()<=heightTh){
				position=3;
			}else if(event.getY()<=heightTh*2){
				position=6;
			}else{
				position=9;
			}
		}
		return position;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode==KeyEvent.KEYCODE_VOLUME_UP){
//			volume=volume+0.1f;
//			if(volume>=1.0f) volume=1.0f;
//		}else if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN){
//			volume=volume-0.1f;
//			if(volume<=0.0f) volume=0.0f;
//		}
		
		nowVolume=audioManager.getStreamVolume(AudioManager.STREAM_RING);
		volume=1.0f*nowVolume/maxVolume;
		
		//oggplayer.setVolume(volume, volume);
		//settingVolume();
		return super.onKeyDown(keyCode, event);
	}


	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getAction()==KeyEvent.ACTION_DOWN){
			switch(event.getKeyCode()){
			case KeyEvent.KEYCODE_BACK:
				return true;
			}
		}
		
		return super.dispatchKeyEvent(event);
	}
	
	
	
// 以下　廃棄クラス　前はDBに値を入れて読み込んでいたけど保存する必要ねーや	
//	void settingVolume(){
//	
//	final String[] columnsList={"passWord"};
//	final String where="keyWord LIKE ?";
//	final String keyWord="keyWord";
//
//	Intent intent=new Intent();
//	intent.setData(Uri.parse("content://com.exsample.sqlite.Dataprovider.eventDB/"));
//	
//	ContentResolver cr=cnt.getContentResolver();
//	Cursor c=cr.query(intent.getData(), columnsList, where, new String[]{keyWord}, null);		
//	String allNum = "1";
//	
//	if(c.moveToFirst()){
//		do{
//			allNum=c.getString(0);
//		}while(c.moveToNext());
//	}		
//	
//	intent.setData(Uri.parse("content://com.exsample.sqlite.Dataprovider.eventDB/"));
//    cr.delete(intent.getData(), "keyWord LIKE ?", new String[]{"keyWord"});
//
//    ContentValues cv=new ContentValues();
//	cv.put("keyWord", "keyWord");
//	cv.put("passWord", allNum);
//	cv.put("volume", volume);
//    cr.insert(intent.getData(), cv);
//    
//}
//	private void getVolume(Context cnt){
//	
//	final String[] columnsList={"volume"};
//	final String where="keyWord LIKE ?";
//	final String keyWord="keyWord";
//
//	Intent intent=new Intent();
//	intent.setData(Uri.parse("content://com.exsample.sqlite.Dataprovider.eventDB/"));
//	
//	ContentResolver cr=cnt.getContentResolver();
//	Cursor c=cr.query(intent.getData(), columnsList, where, new String[]{keyWord}, null);		
//	
//	if(c.moveToFirst()){
//		do{
//			volume=c.getFloat(0);
//		}while(c.moveToNext());
//	}
//}
	
	
	
	
	
	
	
	
}