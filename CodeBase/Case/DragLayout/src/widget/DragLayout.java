package com.example.sideslippanel.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-30 下午10:57:58
 * 
 * 描述:侧滑面板;侧滑布局.用于扩展主面板的功能
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class DragLayout extends FrameLayout {
	private ViewDragHelper dragHelper;
	private ViewGroup leftContent;
	private ViewGroup mainContent;
	private onDragStatusListener dragListener;
	private Status status = Status.Close;
	public static enum Status{ //状态枚举
		Close, Open, Draging
	}
	
	public DragLayout(Context context) {
		this(context, null);
	}

	public DragLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DragLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initData();
	}

	private void initData() {
		// ViewDragHelper: Google2013年IO大会提出的,用于解决界面控件拖拽移动问题.
		dragHelper = ViewDragHelper.create(this, callback); //forParent:父View; sensitivity:敏感度(mTouchSlop:最小敏感范围,越小越敏感,默认1.0); cb:回调
	}
		
	ViewDragHelper.Callback callback= new ViewDragHelper.Callback() { 
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			// child 当前被拖拽的view; pointerId 区分多点触摸的id; return true:允许拖拽;false:不允许
			return true;
		}
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// return 哪个值就移动到哪(横向)个位置  [child当前拖拽的view;left新的位置;dx位置变化量]
			if(child == mainContent){
				left = fixLeft(left);
			}
			fixLeft(left);
			return left;
		}
		
		private int fixLeft(int left) {
			// 修正Left坐标
			if(left < 0 ){
				return 0; 
			}else if(left > mRange) {
				return mRange;
			}
			return left;
		};
		
		public int getViewHorizontalDragRange(View child) {
			// 限制水平拖动范围
			return mRange;
		};
		
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			//当view位置改变时回调(做:更新状态,重绘界面,伴随动画) [left坐标;dx变化量]
			int newLeft = left;
			if(changedView == leftContent){
				newLeft = mainContent.getLeft() + dx;
			}
			newLeft = fixLeft(newLeft);
			if(changedView == leftContent){
				leftContent.layout(0, 0, mWidth, mHeight);
				mainContent.layout(newLeft, 0, newLeft + mWidth, mHeight); //把leftContent变化量传给mainContent重绘
			}
			dispathDraEvent(newLeft); //执行动画
			invalidate(); //兼容低版本,因为低版本的view没有写重绘代码
		};
		
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			// 当view被拖拽释放时调用 [releasedChild被释放的view;xvel速度→↓+]
			if(xvel == 0 && mainContent.getLeft() > mRange/2.0f){
				open();
			}else if (xvel > 0){
				open();
			}else{
				close();
			}
		}
	};
	
	/**
	 * 执行动画的打开
	 */
	public void open(){
		open(true);
	}
	
	/**
	 * 打开
	 * @param isSmooth 是否执行平滑动画
	 */
	public void open(boolean isSmooth) {
		if(isSmooth){
			if(dragHelper.smoothSlideViewTo(mainContent, mRange, 0)){ //true:需要继续移动,还没有移动到指定位置
				ViewCompat.postInvalidateOnAnimation(this); //专门针对动画的重绘
			}
		}else{
			mainContent.layout(mRange, 0, mRange + mWidth, mHeight);
		}
	};
	
	/**
	 * 执行动画的关闭
	 */
	public void close(){
		close(true);
	}
	
	/**
	 * 关闭
	 * @param isSmooth 是否执行平滑动画
	 */
	public void close(boolean isSmooth) {
		if(isSmooth){
			if(dragHelper.smoothSlideViewTo(mainContent, 0, 0)){ //true:需要继续移动,还没有移动到指定位置
				ViewCompat.postInvalidateOnAnimation(this); //专门针对动画的重绘
			}
		}else{
			mainContent.layout(0, 0, mWidth, mHeight);
		}
	}
	
	@Override
	public void computeScroll() {
		super.computeScroll();
		// 持续平滑动画
		if(dragHelper.continueSettling(true)){ //返回true,动画还需要继续执行
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
	
	private void dispathDraEvent(int newLeft) {
		float percent = newLeft * 1.0f / mRange; //移动百分比
		
		Status preStatus = status; //记录上次的状态,当状态改变执行回调
		status = updateStatus(percent); //现在的状态
		if(status != preStatus){
			if(status == Status.Close){ //关闭
				if(dragListener != null) dragListener.onClose();
			}else if(status == Status.Open){
				if(dragListener != null) dragListener.onOpen();
			}
		}
		if(dragListener != null) dragListener.onDraging(percent);
		
		animalViews(percent); //动画
	}

	private Status updateStatus(float percent) {
		if(percent == 0.0f){
			return Status.Close;
		}else if(percent == 1.0f){
			return Status.Open;
		}
		return Status.Draging;
	}

	private void animalViews(float percent) {
		//leftContent.setScaleX(0.5f+0.5f * percent); //缩放;只兼容到11
		//leftContent.setScaleY(0.5f+0.5f * percent);
		// 左面板 ======
		// 缩放动画
		ViewHelper.setScaleX(leftContent, evalute(percent, 0.5f, 1.0f)); //NineOldAndroids的框架;Github上JakeWharton所写,用于解决属性动画向下兼容
		ViewHelper.setScaleY(leftContent, evalute(percent, 0.5f, 1.0f));
		// 平移动画
		ViewHelper.setTranslationX(leftContent, evalute(percent, -mWidth/2.0f, 0));
		// 透明度
		ViewHelper.setAlpha(leftContent, evalute(percent, 0.5f, 1.0f));
		
		// 主面板======
		// 缩放
		ViewHelper.setScaleX(mainContent, evalute(percent, 1.0f, 0.8f));
		ViewHelper.setScaleY(mainContent, evalute(percent, 1.0f, 0.8f));
		
		// 背景======
		getBackground().setColorFilter((int) evaluateColor(percent, Color.BLACK, Color.TRANSPARENT), Mode.SRC_OVER); //仅对图片有效,纯色无效
	}
	
	private Float evalute(float fraction, Number startValue, Number endValue){
		// 估值器 [fraction百分比;startValue开始;endValue;结束]
		float startFloat = startValue.floatValue();
		return startFloat + fraction * (endValue.floatValue() - startFloat);
	}
	
	private Object evaluateColor(float fraction, Object startValue, Object endValue) {
    	// 颜色变化过度 [fraction百分比;startValue开始;endValue;结束]
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB))));
    }
	
	private int mHeight;
	private int mWidth;
	private int mRange;
	
	//public boolean dispatchTouchEvent(android.view.MotionEvent ev) {}; //分发
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) { //拦截
		return dragHelper.shouldInterceptTouchEvent(ev); //dragHelper判断是否拦截
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try{
			dragHelper.processTouchEvent(event); //dragHelper处理touch事件
		}catch(Exception e){ e.printStackTrace(); }
		return true;
	}
	
	@Override
	protected void onFinishInflate() {
		// xml填充结束后调用,所有孩子已经被添加
		super.onFinishInflate();
		
		// 容错性检查
		if(getChildCount() < 2){ throw new IllegalStateException("布局至少有两个子view. Your ViewGroup must have two children at least."); } //非法状态异常
		if(!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup)){ throw new IllegalStateException("子View必须是ViewGroup的子类. Your children must be an instance of ViewGroup."); } //非法参数异常
		
		leftContent = (ViewGroup) getChildAt(0);
		mainContent = (ViewGroup) getChildAt(1);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mHeight = getMeasuredHeight(); //当前屏幕高度
		mWidth = getMeasuredWidth();
		
		mRange = (int) (mWidth * 0.6f);
	}
	
	/**
	 * 设置状态改变监听
	 */
	public void setDragStatusListener(onDragStatusListener dragListener){
		this.dragListener = dragListener;
	}
	
	public interface onDragStatusListener{
		/**
		 * 其他状态变成关闭状态回调
		 */
		void onClose();
		/**
		 * 其他状态变成打开状态回调
		 */
		void onOpen();
		/**
		 * 有滑动事件都回调
		 * @param percent
		 */
		void onDraging(float percent);
	}

	/**
	 * 获取当前状态信息
	 * @return Close/Open/Draging
	 */
	public Status getStatus() {
		return status;
	}

}
