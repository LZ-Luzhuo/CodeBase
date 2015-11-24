package com.example.appdemo.demo;

import com.example.appdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-25 上午12:35:44
 * 
 * 描述:两次返回键后退案例
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class TwoClickBack extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	

	long[] mHits = new long[2];
	@Override
	public void onBackPressed() {
		System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
		mHits[mHits.length-1] = SystemClock.uptimeMillis();
		if(mHits[0]>=(SystemClock.uptimeMillis()-500)){
			super.onBackPressed();
		}
	}
	
}
