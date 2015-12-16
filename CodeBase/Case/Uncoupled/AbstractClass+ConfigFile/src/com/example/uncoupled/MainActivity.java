package com.example.uncoupled;

import com.example.uncoupled.Test.Test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-12 下午5:27:04
 * 
 * 描述:BeanFactory的使用.
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MainActivity extends Activity {
	private Test test;
	private TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}
	
	private void initView() {
		textview = (TextView) findViewById(R.id.text);
	}

	private void initData() {
		test = BeanFactory.getImpl(Test.class);
	}

	public void onClick(View v){
		test.show(textview);
	}
}
