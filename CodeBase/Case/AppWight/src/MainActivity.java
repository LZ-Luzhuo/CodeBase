package com.example.appdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.appdemo.weight.WidgetDemo;

public class MainActivity extends Activity implements OnClickListener {
	private TextView textview;
	private int x;
	private WidgetDemo widgetDemo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textview = (TextView) findViewById(R.id.textview);
		textview.setOnClickListener(this);
		initData();
	}

	private void initData() {
		widgetDemo = new WidgetDemo(this);
	}

	@Override
	public void onClick(View v) {
		x++;
		widgetDemo.changeText(x+"");
	}
}
