package com.example.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;

import com.example.view.Page;
import com.example.viewpagerindicator.R;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-6 下午8:14:55
 * 
 * 描述:这是测试类,将page设置给某个viewgroup.
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MainActivity extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Page page = new Page(this);
		
		RelativeLayout view = (RelativeLayout) findViewById(R.id.frame);
		view.addView(page.getContentView());
		
		page.initData();
	}

}
