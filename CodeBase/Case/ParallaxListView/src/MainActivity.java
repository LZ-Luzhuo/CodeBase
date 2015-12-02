package com.example.parallax;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.parallax.utils.Cheeses;
import com.example.parallax.widget.ParallaxListView;

public class MainActivity extends Activity {
	private ParallaxListView plistview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		plistview = (ParallaxListView) findViewById(R.id.plistview);
	}

	private void initData() {
		plistview.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, Cheeses.NAMES));
	}

}
