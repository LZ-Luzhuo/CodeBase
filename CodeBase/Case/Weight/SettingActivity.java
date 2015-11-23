package com.example.appdemo;

import com.example.appdemo.R;
import com.example.appdemo.weight.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity{
	private SettingItemView settingItemView;
	private int i = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		initView();
	}

	private void initView() {
		settingItemView = (SettingItemView) findViewById(R.id.setting);
		settingItemView.setChecked(true);
		
		settingItemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(i%2 == 0){ settingItemView.setChecked(true);
				}else{ settingItemView.setChecked(false); }
				i++;
				System.out.println("SettingItemView:"+settingItemView.isChecked());
			}
		});
	}
}
