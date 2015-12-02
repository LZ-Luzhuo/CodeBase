package com.example.parallax.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.parallax.R;
import com.example.parallax.animation.SpringbackAnimation;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-2 下午3:25:43
 * 
 * 描述:视差特效listview;更换图片到布局文件里改
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ParallaxListView extends ListView {
	private int originalHeight;
	private int drawableHeight;
	private ImageView image;
	private int newHeight;

	public ParallaxListView(Context context) {
		this(context, null);
	}

	public ParallaxListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ParallaxListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initData();
	}

	private void initData() {
		this.setOverScrollMode(View.OVER_SCROLL_NEVER); //下拉顶部蓝色

		setParallaxImage();
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		// [deltaY:竖直方向瞬时变化量(顶↓-底↑+); scrollY:竖直方向的变化量; scrollRangeY:滑动范围; isTouchEvent:是否是手势事件(false为惯性)]
		if(isTouchEvent && deltaY < 0){ //是手指拉动 并且 是下拉
			if(image.getHeight() <= drawableHeight){ //图片小于等于图片高度就不让其生效
				newHeight = image.getHeight() + Math.abs(deltaY/3);
				image.getLayoutParams().height = newHeight;
				image.requestLayout(); //让其生效
			}
		}
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (MotionEventCompat.getActionMasked(ev)) { //与ev.getAction()没区别
		case MotionEvent.ACTION_UP:
			int startHeight = image.getHeight();
			int endHeight = originalHeight;
			
			// 执行回弹动画,自定义动画
			SpringbackAnimation animation = new SpringbackAnimation(image, startHeight, endHeight);
			startAnimation(animation);
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	private void setParallaxImage(){
		final View viewheader = View.inflate(getContext(), R.layout.view_header, null);
		image = (ImageView) viewheader.findViewById(R.id.iv);
		viewheader.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				// 当布局填充结束之后,此方法被回调
				getParallaxImageSize();
				viewheader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
		this.addHeaderView(viewheader);
	}
	
	private void getParallaxImageSize() {
		originalHeight = image.getHeight(); //ImageView高度
		drawableHeight = image.getDrawable().getIntrinsicHeight(); //图片高度
	}
}
