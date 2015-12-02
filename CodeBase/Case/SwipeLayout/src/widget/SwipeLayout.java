package com.example.swipelayout.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-2 下午10:23:43
 * 
 * 描述:侧拉删除;用于列表条目管理
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class SwipeLayout extends FrameLayout {
	private Status status = Status.Close;
	private OnSwipeLayoutListener swipeLayoutListener;
	private ViewDragHelper dragHelper;
	private View frontView;
	private View backView;
	private int height;
	private int width;
	private int range;
	public static enum Status{
		Close, Open, Draging
	}

	public SwipeLayout(Context context) {
		this(context, null);
	}

	public SwipeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initData();
	}

	private void initData() {
		dragHelper = ViewDragHelper.create(this, 1.0f, callback);
	}

	ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
		@Override
		public boolean tryCaptureView(View arg0, int arg1) {
			return true;
		}
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// 修正位置
			if(child == frontView){
				if(left > 0){
					return 0;
				}else if(left < -range){
					return -range;	
				}
			}else if(child == backView){
				if(left > width){
					return width;
				}else if(left < width-range){
					return width - range;
				}
			}
			return left;
		};
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			if(changedView == frontView){
				backView.offsetLeftAndRight(dx);
			}else if(changedView == backView){
				frontView.offsetLeftAndRight(dx);
			}
			dispatchSwipeEvent();
			invalidate();
		};

		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			if(xvel == 0 && releasedChild.getLeft() < -range/2.0f){
				open();
			}else if(xvel < 0){
				open();
			}else{
				close();
			}
		}
	};
	
	private void dispatchSwipeEvent() {
		Status preStatus = status; //记录上一次的状态
		status = updateStatus(); //更新当前状态
		if(preStatus != status && swipeLayoutListener != null){
			if(status == Status.Close){
				swipeLayoutListener.onClose(this);
			}else if(status == Status.Open){
				swipeLayoutListener.onOpen(this);
			}else if(status == Status.Draging){
				if(preStatus == Status.Close){
					swipeLayoutListener.onStartOpen(this);
				}else if(preStatus == Status.Open){
					swipeLayoutListener.onStartClose(this);
				}
			}
		}
		if(swipeLayoutListener != null) swipeLayoutListener.onDraging(this);
	}
	
	private Status updateStatus() {
		int left = frontView.getLeft();
		if(left == 0){
			return Status.Close;
		}else if(left == -range){
			return Status.Open;
		}
		return Status.Draging;
	}

	private void open() {
		open(true);
	};
	
	/**
	 * 打开
	 * @param isSmooth 是否执行平滑动画
	 */
	public void open(boolean isSmooth){
		int finalLeft = -range;
		if(isSmooth){
			if(dragHelper.smoothSlideViewTo(frontView, finalLeft, 0)){
				ViewCompat.postInvalidateOnAnimation(this);
			}
		}else{
			layoutContent(true);
		}
	}
	
	private void close() {
		close(true);
	}
	
	/**
	 * 关闭
	 * @param isSmooth 是否执行平滑动画
	 */
	public void close(boolean isSmooth){
		int finalLeft = 0;
		if(isSmooth){
			if(dragHelper.smoothSlideViewTo(frontView, finalLeft, 0)){
				ViewCompat.postInvalidateOnAnimation(this);
			}
		}else{
			layoutContent(false);
		}
	}
	
	@Override
	public void computeScroll() {
		super.computeScroll();
		if(dragHelper.continueSettling(true)){
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
	
	public boolean onInterceptTouchEvent(android.view.MotionEvent ev) {
		return dragHelper.shouldInterceptTouchEvent(ev);
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try{
			dragHelper.processTouchEvent(event);
		}catch (Exception e) { e.printStackTrace(); }
		return true;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		layoutContent(false); //摆放位置:开着/关着
	}
	
	private void layoutContent(boolean isOpen) {
		// 摆放前View
		Rect frontRect = computefrontViewRect(isOpen);
		frontView.layout(frontRect.left, frontRect.top, frontRect.right, frontRect.bottom);
		// 摆放后View
		Rect backRect = computeBackViewViaFront(frontRect);
		backView.layout(backRect.left, backRect.top, backRect.right, backRect.bottom);
		
		//调整顺序,摆前面
		bringChildToFront(frontView);
	}

	private Rect computefrontViewRect(boolean isOpen) {
		// 矩形;将点位封装到矩形里
		int left = 0;
		if(isOpen){
			left = -range;
		}
		return new Rect(left, 0, left + width, height);
	}

	private Rect computeBackViewViaFront(Rect frontRect) {
		int left = frontRect.right;
		return new Rect(left, 0, left + range, height);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		backView = getChildAt(0);
		frontView = getChildAt(1);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		height = frontView.getMeasuredHeight();
		width = frontView.getMeasuredWidth();
		range = backView.getMeasuredWidth();
	}
	
	public void setSwipeLayoutListener(OnSwipeLayoutListener swipeLayoutListener){
		this.swipeLayoutListener = swipeLayoutListener;
	}
	
	public static interface OnSwipeLayoutListener{
		void onClose(SwipeLayout swipeLayout);
		void onOpen(SwipeLayout swipeLayout);
		void onDraging(SwipeLayout swipeLayout);
		void onStartClose(SwipeLayout swipeLayout);
		void onStartOpen(SwipeLayout swipeLayout);
	}
}
