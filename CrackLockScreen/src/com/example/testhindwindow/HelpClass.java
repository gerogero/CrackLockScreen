package com.example.testhindwindow;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HelpClass extends View{

	View v;
	Button bt;
	EditText et;
	String inputGmail;
	ScreenService ss;
	
	public HelpClass(Context context) {
		super(context);
		
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v=inflater.inflate(R.layout.helpstyle, null);
		bt=(Button) v.findViewById(R.id.button1);
		et=(EditText) v.findViewById(R.id.editText1);
		ss=(ScreenService) context;

		final Context cnt=context;

		OnClickListener listener=new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(et!=null){
					inputGmail=et.getText().toString();
					ArrayList<String> gmailList=new ArrayList();
					Account[] accounts=AccountManager.get(cnt).getAccounts();

					for(Account account:accounts){
						String gmail=account.name;
						if(gmail.equals(inputGmail)){
							//ss.removeHelpClass();
							ss.stopSelf();
						}
					}
				}
			}
		};
		bt.setOnClickListener(listener);
	}
	public View helpView(){
		return v;
	}
}
