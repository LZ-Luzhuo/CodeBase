package com.example.quickindexbar.widget;

import com.example.quickindexbar.utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-1 下午10:08:49
 * 
 * 描述:快速索引条;用于字母快速定位联系人
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class QuickIndexBar extends View {
	private static final String[] LETTERS = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L","M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	private Paint paint;
	private float cellHeight;
	private int cellWidth;
	private OnLetterUpdateListener listener;

	public QuickIndexBar(Context context) {
		this(context, null);
	}

	public QuickIndexBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initData();
	}

	private void initData() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画字母
		for (int i = 0; i < LETTERS.length; i++) {
			String text = LETTERS[i];
			// 计算坐标
			int x = (int) (cellWidth / 2.0f - paint.measureText(text) / 2.0f);
			// 获取单个文字的高度
			Rect bounds = new Rect();// 矩形
			paint.setTextSize(Utils.dipTopx(getContext(), 13));
			paint.getTextBounds(text, 0, text.length(), bounds);
			int textHeight = bounds.height();
			int y = (int) (cellHeight / 2.0f + textHeight / 2.0f + i * cellHeight);
			
			// 根据按下的字母, 设置画笔颜色
			paint.setColor(touchIndex == i ? Color.GRAY : Color.WHITE);
			
			// 绘制文本A-Z
			canvas.drawText(text, x, y, paint);
		}
	}
	
	int touchIndex = -1;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int index = -1;
		switch (MotionEventCompat.getActionMasked(event)) {
		case MotionEvent.ACTION_DOWN:
			// 获取当前触摸到的字母索引
			index = (int) (event.getY() / cellHeight);
			if(index >= 0 && index < LETTERS.length){
				// 判断是否跟上一次触摸到的一样
				if(index != touchIndex) {
					if(listener != null){ listener.onLetterUpdate(LETTERS[index]); } //监听回调
					touchIndex = index;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			index = (int) (event.getY() / cellHeight);
			if(index >= 0 && index < LETTERS.length){
				// 判断是否跟上一次触摸到的一样
				if(index != touchIndex){
					if(listener != null){ listener.onLetterUpdate(LETTERS[index]); } //监听回调
					touchIndex = index;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			touchIndex = -1;
			break;
		default:
			break;
		}
		invalidate();
		return true;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 获取单个字母框的宽和高
		cellWidth = getMeasuredWidth();
		int height = getMeasuredHeight();
		cellHeight = height * 1.0f / LETTERS.length;
	}
	
	public interface OnLetterUpdateListener{
		void onLetterUpdate(String letter);
	}
	
	/**
	 * 设置更新监听
	 */
	public void setLetterUpdateListener(OnLetterUpdateListener listener) {
		this.listener = listener;
	}

}
