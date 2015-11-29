package com.luzhuo.waterfall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-29 下午8:17:02
 * 
 * 描述:瀑布流的案例;瀑布流布局里必须添加的是子view必须是3个listview;
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class WaterfallListview extends LinearLayout{
	private int childwidth;
	private int childheight;
	private int childcount;

	public WaterfallListview(Context context) {
		super(context);
	}
	
	public WaterfallListview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		childwidth = getWidth()/getChildCount(); //宽度 = 屏幕LinearLayout宽度/子view个数
		childheight = getHeight();
		childcount = getChildCount();
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 拦截所有传递给子view的touch事件
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 处理touch事件,并把拦截到的touch事件进行分发
		try {
			float eventX = event.getX();
			if (eventX < childwidth){ // 左边的listView  
				event.setLocation(childwidth/2, event.getY());
				getChildAt(0).dispatchTouchEvent(event);
				return true;
			} else if (eventX > childwidth && eventX < 2 * childwidth) { // 中间的listView
				float eventY = event.getY();
				if (eventY < childheight/2) { // 中间上半部分
					event.setLocation(childwidth/2, event.getY());
					for (int i = 0; i < childcount; i++) { //分发给每隔子view
						View child = getChildAt(i);
						child.dispatchTouchEvent(event);
					}
					return true;
				} else if (eventY > childheight/2) {
					event.setLocation(childwidth/2, event.getY());
					getChildAt(1).dispatchTouchEvent(event);
					return true;
				}
			}else if (eventX>2*childwidth){ // 右边的listView  
				event.setLocation(childwidth/2, event.getY());
				getChildAt(2).dispatchTouchEvent(event);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
