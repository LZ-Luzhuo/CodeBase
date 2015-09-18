package com.example.luzhuo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-3 下午11:54:15
 * 
 * 描述:拦截ViewPager的Touch事件,让其不能左右移动
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class CustomViewPager extends LazyViewPager {
	private boolean isScrollable;

	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (isScrollable == false) {
			return false;
		} else {
			return super.onInterceptTouchEvent(ev);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (isScrollable == false) {
			return false;
		} else {
			return super.onTouchEvent(ev);
		}
	}

	public boolean isScrollable() {
		return isScrollable;
	}

	public void setScrollable(boolean isScrollable) {
		this.isScrollable = isScrollable;
	}
}
