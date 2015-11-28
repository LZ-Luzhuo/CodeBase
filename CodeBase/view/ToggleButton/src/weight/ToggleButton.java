package com.luzhuo.viewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-28 下午4:35:51
 * 
 * 描述:开关按钮控件;可以拖动/点击控制;<b>控件的大小由设置的图片决定</b>
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ToggleButton extends View implements OnClickListener{
	private String NAMESPACE = "http://schemas.android.com/apk/res/com.luzhuo.viewdemo";
	private Bitmap backgroudBitmap;
	private Bitmap slideButton;
	private Paint paint;
	private float slide_left; //slide按钮左边界
	private boolean currentState = false; //开关状态
	private State state; //状态接口

	public ToggleButton(Context context) {
		super(context);
		initView();
		initData();
	}
	
	public ToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获取自定属性
		int toggleBackground = attrs.getAttributeResourceValue(NAMESPACE, "toggleBackground", 0);
		if(toggleBackground != 0) backgroudBitmap = BitmapFactory.decodeResource(getResources(), toggleBackground);
		int slideButtonAttrs = attrs.getAttributeResourceValue(NAMESPACE, "slideButton", 0);
		if(slideButtonAttrs != 0) slideButton = BitmapFactory.decodeResource(getResources(), slideButtonAttrs);
		currentState = attrs.getAttributeBooleanValue(NAMESPACE, "state", false);
		
		initView();
		initData();
	}
	
	public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
		initData();
	}
	
	private void initView() {
		if(backgroudBitmap == null) backgroudBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.togglebutton_switchbackground);
		if(slideButton == null)  slideButton = BitmapFactory.decodeResource(getResources(), R.drawable.togglebutton_slide); // 滑动的图片
	}
	
	private void initData() {
		paint = new Paint();
		paint.setAntiAlias(true); //抗锯齿
		
		setOnClickListener(this);
		
		maxLeft = backgroudBitmap.getWidth() - slideButton.getWidth();
		repairSlideLeftUP(maxLeft);
	}
	
	private boolean isDrag = false; //是否拖动,若是拖动就不响应onClick事件
	@Override
	public void onClick(View v) {
		if(!isDrag){
			currentState = !currentState;
			changeSlideButton(); //改变滑动按钮
			
			if(state != null) state.onState(currentState); //回调状态改变
		}
	}

	private void changeSlideButton() {
		if(currentState){
			slide_left = backgroudBitmap.getWidth()-slideButton.getWidth();
		}else{
			slide_left = 0;
		}
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// View 的大小
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(backgroudBitmap.getWidth(), backgroudBitmap.getHeight()); //设置view的大小px
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// 改变View的位置,这里没用到
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制控件
		super.onDraw(canvas);
		
		canvas.drawBitmap(backgroudBitmap, 0, 0, paint); //绘制背景
		canvas.drawBitmap(slideButton, slide_left, 0, paint); //绘制可滑动的按钮;
	}
	
	private int firstx;
	private int lastx;
	private int maxLeft;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 触摸事件
		super.onTouchEvent(event);
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstx = lastx = (int) event.getX();
			isDrag = false;
			break;
		case MotionEvent.ACTION_MOVE:
			if(Math.abs(event.getX()-firstx) > 5) isDrag = true;
			
			int dis = (int) (event.getX()-lastx);
			lastx = (int) event.getX();
			slide_left = slide_left + dis;
			
			repairSlideLeftMove(maxLeft);
			break;
		case MotionEvent.ACTION_UP:
			if(isDrag){
				// slide_left被移动时,处理状态
				currentState = slide_left > maxLeft/2 ? true : false;
				repairSlideLeftUP(maxLeft);
				
				if(state != null) state.onState(currentState); //回调状态改变
			}
			break;
		}
		return true;
	}

	private void repairSlideLeftMove(int maxLeft) {
		// 修正slide_left的值
		slide_left = slide_left > 0 ? slide_left : 0;
		slide_left = slide_left < maxLeft ? slide_left : maxLeft;
		invalidate();
	}
	
	private void repairSlideLeftUP(int maxLeft) {
		slide_left = currentState == true ? maxLeft : 0;
		invalidate();
	}
	
	/**
	 * 设置状态监听
	 * @param state
	 */
	public void setStateListener(State state){
		this.state = state;
	}
	
	public interface State{
		public void onState(boolean state);
	}
	
	/**
	 * 改变状态
	 * @param state
	 */
	public void changeState(boolean state){
		currentState = state;
		repairSlideLeftUP(maxLeft);
	}
	
	/**
	 * 获取当前状态
	 * @return 当前状态
	 */
	public boolean getState(){
		return currentState;
	}
	
	/**
	 * 设置按钮背景图片
	 * @param id 资源id
	 */
	public void setBackgroudBitmap(int id){
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
		if(bitmap != null) backgroudBitmap = bitmap;
	}
	
	/**
	 * 设置按钮图片
	 * @param id 资源id
	 */
	public void setSlideButton(int id){
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
		if(bitmap != null) slideButton = bitmap;
	}
}
