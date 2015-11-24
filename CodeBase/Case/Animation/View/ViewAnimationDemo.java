package com.example.appdemo.demo;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.appdemo.R;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-24 下午8:22:23
 * 
 * 描述:View控件动画案例
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ViewAnimationDemo extends Activity implements OnClickListener {
	private TextView textview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		textview = (TextView) findViewById(R.id.textview);
		textview.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);//这是一个来回摇头的动画
		//animation.setInterpolator(new Interpolator() {
		//	@Override
		//	public float getInterpolation(float input) {
		//		// 经过函数的运算,返回y的值
		//		return 0;
		//	}
		//}); //自定义动画
		textview.startAnimation(animation);
	}
}