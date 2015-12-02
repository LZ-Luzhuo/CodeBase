package com.example.swipelayout;

import com.example.swipelayout.adapter.MAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.listview);
	}

	private void initData() {
		listview.setAdapter(new MAdapter(MainActivity.this));
	}
}
