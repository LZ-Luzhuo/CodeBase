package com.example.sideslippanel.widget;

import com.example.sideslippanel.widget.DragLayout.Status;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-1 下午8:55:24
 * 
 * 描述:与DragLayout相匹配的LinearLayout,优化触摸事件;用户主面板
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MLinearLayout extends LinearLayout{
	private DragLayout dragLayout;
	
	public MLinearLayout(Context context) {
		super(context);
	}
	
	public MLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setDraglayout(DragLayout dragLayout){
		this.dragLayout  = dragLayout;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(dragLayout.getStatus() == Status.Close){ //关闭状态不拦截
			return super.onInterceptTouchEvent(ev);
		}else{
			return true;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(dragLayout.getStatus() == Status.Close){ //关闭状态不拦截
			return super.onInterceptTouchEvent(event);
		}else{
			if(event.getAction() == MotionEvent.ACTION_UP){
				dragLayout.close();
			}
		}
		return true;
	}
}
