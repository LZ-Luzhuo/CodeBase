package com.example.sildingfinishdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.sildingfinishdemo.R;
import com.example.sildingfinishdemo.activity.base.BaseActivity;

public class MainActivity extends BaseActivity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}

	private void initView() {
		Button normal = (Button) findViewById(R.id.normal);
		Button listview = (Button) findViewById(R.id.listview);
		Button scrollview = (Button) findViewById(R.id.scrollview);
		Button viewpager = (Button) findViewById(R.id.viewpager);
		
		normal.setOnClickListener(this);
		listview.setOnClickListener(this);
		scrollview.setOnClickListener(this);
		viewpager.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent mIntent = null;
		switch (v.getId()) {
		case R.id.normal:
			mIntent = new Intent(MainActivity.this, NormalActivity.class);
			break;
		case R.id.listview:
			mIntent = new Intent(MainActivity.this, ListViewActivity.class);
			break;
		case R.id.scrollview:
			mIntent = new Intent(MainActivity.this, ScrollViewActivity.class);
			break;
		case R.id.viewpager:
			mIntent = new Intent(MainActivity.this, ViewPagerActivity.class);
			break;
		}
		startActivity(mIntent);
	}

}
