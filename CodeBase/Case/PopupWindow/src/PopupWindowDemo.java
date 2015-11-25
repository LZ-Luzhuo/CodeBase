package com.example.appdemo.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.example.appdemo.R;

public class PopupWindowDemo extends Activity implements OnClickListener{
	private RelativeLayout layout;
	private AddPopWindow addPopWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup);
		initView();
		initData();
	}
	
	private void initView(){
		layout = (RelativeLayout) findViewById(R.id.layout);
		
		layout.setOnClickListener(this);
	}
	
	private void initData(){
		addPopWindow = new AddPopWindow(this);
	}

	@Override
	public void onClick(View v) {
		addPopWindow.show(layout);
	}
}
