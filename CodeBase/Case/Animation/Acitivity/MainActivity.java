package com.example.appdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClick(View v) {
		Intent intent = new Intent(MainActivity.this, Activity2.class);
		startActivity(intent);
		
		// 要求在finish()/startActivity(intent)后面执行
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
}
