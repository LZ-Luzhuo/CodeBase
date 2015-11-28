package com.luzhuo.viewdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.luzhuo.viewdemo.ToggleButton.State;

public class MainActivity extends ActionBarActivity {
	private ToggleButton toggle_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		toggle_btn = (ToggleButton) findViewById(R.id.toggle_btn);
	}

	private void initData() {
		toggle_btn.setStateListener(new State() {
			@Override
			public void onState(boolean state) {
				System.out.println(state);
			}
		});
	}
}
