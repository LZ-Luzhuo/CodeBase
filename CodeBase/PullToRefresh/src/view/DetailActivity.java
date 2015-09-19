package com.example.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.pulltorefresh.R;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-12 下午3:22:40
 * 
 * 描述:显示详情信息的Activity
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class DetailActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.item);
		
		Intent intent = getIntent();
		
		 ((TextView) findViewById(R.id.title)).setText("title:"+intent.getStringExtra("title"));
		 ((TextView) findViewById(R.id.content)).setText("url:"+intent.getStringExtra("url"));

	}
}
