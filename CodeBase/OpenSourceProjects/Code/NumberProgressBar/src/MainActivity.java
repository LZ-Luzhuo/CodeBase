package com.example.numberprogressbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;

import com.example.numberprogressbar.numberprogressbar.NumberProgressBar;

public class MainActivity extends Activity {
	private int max = 100;
	private int tem = 0;
	private NumberProgressBar numberbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
	}

	private void initData() {
		numberbar = (NumberProgressBar) findViewById(R.id.numberbar);
		numberbar.setMax(max);
		
		new Thread(){ public void run() {
			while(tem <= max){

				runOnUiThread(new Runnable() { public void run() {
						numberbar.setProgress(tem);
				}});
				
				tem++;
				SystemClock.sleep(100);
			}	
		};}.start();
	}
}
