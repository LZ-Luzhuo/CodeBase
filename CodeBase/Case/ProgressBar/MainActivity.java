package com.example.appdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		progressBar = (ProgressBar) findViewById(R.id.progress);
		
	}

	private void initData() {
		progressBar.setMax(10);
		new Thread(){
			public void run() {
				int a = 0;
				boolean flag = true;
				while(flag){
					if(a >= 10) flag = false;
					progressBar.setProgress(a);
					a++;
					SystemClock.sleep(1000);
				}
			};
		}.start();
	}
	
}
