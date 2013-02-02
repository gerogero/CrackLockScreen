package com.example.testhindwindow;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode==0){
			if(resultCode==RESULT_OK){
				Bundle extra=data.getExtras();
				if(extra!=null){
					createPassWord(extra.getString("pass"));
					
					Intent intent=new Intent(MainActivity.this,LayerService.class);
					startService(intent);
					
					
					finish();
				}
			}	
		}
	}
	
	Button bt1;
	Button bt2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bt1=(Button) findViewById(R.id.settingStartBt);
		bt2=(Button) findViewById(R.id.stopBt);
		
		final OnClickListener onstartListener=new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent1=new Intent(MainActivity.this,SettingNumAndStartBt.class);
				startActivityForResult(intent1, 0);
			}
		};
		final OnClickListener onstopListener=new OnClickListener(){
			@Override
			public void onClick(View v) {
				deletePassWord();
				stopService(new Intent(MainActivity.this,LayerService.class));
				btEnable();
			}
		};
		bt1.setOnClickListener(onstartListener);
		bt2.setOnClickListener(onstopListener);
		btEnable();
	}

	public void btEnable(){
		if(checkPassWord()==0){
			bt1.setEnabled(false);
			bt2.setEnabled(true);
		}else if(checkPassWord()==1){
			bt1.setEnabled(true);
			bt2.setEnabled(false);
		}
	}
	
	private int checkPassWord() {
		final String[] columnsList={"passWord"};
		final String where="keyWord LIKE ?";
		final String keyWord="keyWord";

		Intent intent=new Intent();
		intent.setData(Uri.parse("content://com.exsample.sqlite.Dataprovider.eventDB/"));
		
		ContentResolver cr=getContentResolver();
		Cursor c=cr.query(intent.getData(), columnsList, where, new String[]{keyWord}, null);		
		
		String allNum;
		
		if(c.moveToFirst()){
			allNum=c.getString(0);
			if(allNum!=null){
					return 0;
			}else {
				return 1;
			}
		}
		return 1;		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void createPassWord(String wpassWord){
		ContentValues cv=new ContentValues();
		cv.put("keyWord", "keyWord");
		cv.put("passWord", wpassWord);
		cv.put("volume", 0.0f);
		ContentResolver cr=getContentResolver();
        Intent intent=new Intent();
		intent.setData(Uri.parse("content://com.exsample.sqlite.Dataprovider.eventDB"));
        cr.insert(intent.getData(), cv);
	}
	
	public void deletePassWord(){
		Intent intent=new Intent();
		intent.setData(Uri.parse("content://com.exsample.sqlite.Dataprovider.eventDB"));
        ContentResolver cr=getContentResolver();
        cr.delete(intent.getData(), "keyWord LIKE ?", new String[]{"keyWord"});
	}
}
