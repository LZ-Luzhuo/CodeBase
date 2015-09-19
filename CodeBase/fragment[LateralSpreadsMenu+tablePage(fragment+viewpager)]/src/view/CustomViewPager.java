package com.example.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 拦截ViewPager的Touch事件,让其不能左右移动
 * @author Luzhuo
 *
 */
public class CustomViewPager extends LazyViewPager {
	private boolean setTouchModel = false;

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(setTouchModel){
			return super.onInterceptTouchEvent(ev);
		}else{
			return false;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(setTouchModel){
			return super.onTouchEvent(ev);
		}else{
			return false;
		}
	}

}
