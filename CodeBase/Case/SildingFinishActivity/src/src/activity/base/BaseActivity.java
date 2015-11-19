package com.example.sildingfinishdemo.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.sildingfinishdemo.R;
import com.example.sildingfinishdemo.R.layout;
import com.example.sildingfinishdemo.view.SildingFinish;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 只需要把以下代码拷贝到BaseActivity即可实现右划删除
		SildingFinish layout = (SildingFinish) LayoutInflater.from(this).inflate(R.layout.base, null);
		layout.attachToActivity(this);
	}
}
