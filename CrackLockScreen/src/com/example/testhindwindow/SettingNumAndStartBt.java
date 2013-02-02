package com.example.testhindwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SettingNumAndStartBt extends Activity {

	Button bt1;
	Button bt2;
	Button bt3;
	Button bt4;
	Button bt5;
	Button bt6;
	Button bt7;
	Button bt8;
	Button bt9;
	Button startBt;

	StringBuilder sb=new StringBuilder();
	String passNum;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		bt1=(Button) findViewById(R.id.Num1);
		bt2=(Button) findViewById(R.id.Num2);
		bt3=(Button) findViewById(R.id.Num3);
		bt4=(Button) findViewById(R.id.Num4);
		bt5=(Button) findViewById(R.id.Num5);
		bt6=(Button) findViewById(R.id.Num6);
		bt7=(Button) findViewById(R.id.Num7);
		bt8=(Button) findViewById(R.id.Num8);
		bt9=(Button) findViewById(R.id.Num9);
		startBt=(Button) findViewById(R.id.startBt);
		
		final OnClickListener listener1=new OnClickListener(){
			@Override
			public void onClick(View v){ 
				sb.append("1,");
				startBtEnable();
			}
		};

		final OnClickListener listener2=new OnClickListener(){
			@Override
			public void onClick(View v){ 
				sb.append("2,");
				startBtEnable();
			}
		};
		
		final OnClickListener listener3=new OnClickListener(){
			@Override
			public void onClick(View v){ 
				sb.append("3,");
				startBtEnable();
			}
		};

		final OnClickListener listener4=new OnClickListener(){
			@Override
			public void onClick(View v){ 
				sb.append("4,");
				startBtEnable();
			}
		};

		final OnClickListener listener5=new OnClickListener(){
			@Override
			public void onClick(View v){ 
				sb.append("5,");
				startBtEnable();
			}
		};
		
		final OnClickListener listener6=new OnClickListener(){
			@Override
			public void onClick(View v){ 
				sb.append("6,");
				startBtEnable();
			}
		};

		final OnClickListener listener7=new OnClickListener(){
			@Override
			public void onClick(View v){ 
				sb.append("7,");
				startBtEnable();
			}
		};

		final OnClickListener listener8=new OnClickListener(){
			@Override
			public void onClick(View v){ 
				sb.append("8,");
				startBtEnable();
			}
		};
		
		final OnClickListener listener9=new OnClickListener(){
			@Override
			public void onClick(View v){ 
				sb.append("9,");
				startBtEnable();
			}
		};
		
		final OnClickListener listenerSb=new OnClickListener(){
			@Override
			public void onClick(View v){ 
				passNum=new String(sb);
				intent=new Intent();
				intent.putExtra("pass", passNum);
				setResult(RESULT_OK,intent);
				finish();	
			}
		};
		
		bt1.setOnClickListener(listener1);
		bt2.setOnClickListener(listener2);
		bt3.setOnClickListener(listener3);
		bt4.setOnClickListener(listener4);
		bt5.setOnClickListener(listener5);
		bt6.setOnClickListener(listener6);
		bt7.setOnClickListener(listener7);
		bt8.setOnClickListener(listener8);
		bt9.setOnClickListener(listener9);
		startBt.setOnClickListener(listenerSb);
		startBt.setEnabled(false);
	}

	public void startBtEnable(){
		startBt.setEnabled(true);
	}

}
