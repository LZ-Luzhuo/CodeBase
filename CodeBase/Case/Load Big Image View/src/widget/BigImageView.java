package com.example.bitmapregiondecoderdemo.widget;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.bitmapregiondecoderdemo.GestureDetector.MoveGestureDetector;

public class BigImageView extends View {
	private BitmapRegionDecoder brDecoder;
	private int mImageWidth, mImageHeight; // 图片的宽度和高度
	private volatile Rect mRect = new Rect(); // 绘制的区域
	private static final BitmapFactory.Options options = new BitmapFactory.Options();
	private MoveGestureDetector mDetector; //用户move手势
	
	static {
		options.inPreferredConfig = Bitmap.Config.RGB_565;
	}

	public BigImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 设置图片
	 * @param inputStream 图片的输入流
	 */
	public void setInputStream(InputStream inputStream) {
		try {
			// 解析流,获取图片的宽高
			brDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
			BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
			tmpOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(inputStream, null, tmpOptions);
			mImageWidth = tmpOptions.outWidth;
			mImageHeight = tmpOptions.outHeight;
			// 通知调整区域并重新绘制
			requestLayout();
			invalidate();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) 
					inputStream.close();
			} catch (Exception e) { }
		}
	}

	public void init() {
		mDetector = new MoveGestureDetector(getContext(),
				new MoveGestureDetector.SimpleMoveGestureDetector() {
					@Override
					public boolean onMove(MoveGestureDetector detector) {
						int moveX = (int) detector.getMoveX();
						int moveY = (int) detector.getMoveY();
						if (mImageWidth > getWidth()) {
							// 图片宽度-移动距离x
							mRect.offset(-moveX, 0);
							checkWidth();
							invalidate();
						}
						if (mImageHeight > getHeight()) {
							// 图片高度-移动距离y
							mRect.offset(0, -moveY);
							checkHeight();
							invalidate();
						}
						return true;
					}
				});
	}

	/**
	 * 调整图片的宽和View的宽的关系
	 */
	private void checkWidth() {
		Rect rect = mRect;
		int imageWidth = mImageWidth;
		if (rect.right > imageWidth) {
			rect.right = imageWidth;
			rect.left = imageWidth - getWidth();
		}
		if (rect.left < 0) {
			rect.left = 0;
			rect.right = getWidth();
		}
	}

	/**
	 * 调整图片的高和View的高的关系
	 */
	private void checkHeight() {
		Rect rect = mRect;
		int imageHeight = mImageHeight;
		if (rect.bottom > imageHeight) {
			rect.bottom = imageHeight;
			rect.top = imageHeight - getHeight();
		}
		if (rect.top < 0) {
			rect.top = 0;
			rect.bottom = getHeight();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 触摸事件
		mDetector.onToucEvent(event);
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 解析对应区域,并且绘制
		Bitmap bm = brDecoder.decodeRegion(mRect, options);
		canvas.drawBitmap(bm, 0, 0, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int imageWidth = mImageWidth;
		int imageHeight = mImageHeight;
		// 显示图片的中心区域
		//mRect.left = imageWidth / 2 - width / 2;
		//mRect.top = imageHeight / 2 - height / 2;
		//mRect.right = mRect.left + width;
		//mRect.bottom = mRect.top + height;
		// 显示图片的最左边区域
		mRect.left = 0;
		mRect.top = imageHeight / 2 - height / 2;
		mRect.right = width;
		mRect.bottom = mRect.top + height;
		
	}
}
