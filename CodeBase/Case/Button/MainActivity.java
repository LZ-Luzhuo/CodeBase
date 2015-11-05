package com.example.button;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	/**
	 * 获取/失去焦点
	 */
	public void onFocus(View v){
		Toast.makeText(this, "onFocus", 0).show();
	}
	
	/**
	 * 按钮
	 */
	public void onButton(View v){
		Toast.makeText(this, "onButton", 0).show();
	}
}
