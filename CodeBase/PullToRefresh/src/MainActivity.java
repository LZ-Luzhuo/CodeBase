package com.example.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.pulltorefresh.R;
import com.example.view.ItemPage;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		FrameLayout view = (FrameLayout) findViewById(R.id.frame);
		ItemPage itemPage = new ItemPage(this, "null");
		view.addView(itemPage.getContentView());
		itemPage.initData();
	}
		
}
