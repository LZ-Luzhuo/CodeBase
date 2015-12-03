package com.example.goocircle.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.goocircle.R;
import com.example.goocircle.utils.Utils;
import com.example.goocircle.widget.gooview.BubbleLayout;
import com.example.goocircle.widget.gooview.GooView;
import com.example.goocircle.widget.gooview.GooView.OnDisappearListener;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-3 下午8:03:59
 * 
 * 描述:只需设置使用该类就可实现粘性圆点.[在listview的adapter里设置即可使用]
 * <br>参考代码:<pre>
 *   viewHolder.point.setTag(position);
   GooViewListener gooViewListener = new GooViewListener(MainActivity.this, viewHolder.point);
   viewHolder.point.setOnTouchListener(gooViewListener);
   </pre>
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class GooViewListener implements OnTouchListener, OnDisappearListener {

	private WindowManager mWm;
	private WindowManager.LayoutParams mParams;
	private GooView mGooView;
	private View point;
	private int number;
	private final Context mContext;

	private Handler mHandler;

	public GooViewListener(Context mContext, View point) {
		this.mContext = mContext;
		this.point = point;
		this.number = (Integer) point.getTag();

		mGooView = new GooView(mContext);

		mWm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mParams = new WindowManager.LayoutParams();
		mParams.format = PixelFormat.TRANSLUCENT;
		
		mHandler= new Handler(mContext.getMainLooper());
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int action = MotionEventCompat.getActionMasked(event);
		if (action == MotionEvent.ACTION_DOWN) {
			// 当按下时，将自定义View添加到WindowManager中
			
			ViewParent parent = v.getParent();
			// 请求其父级View不拦截Touch事件
			parent.requestDisallowInterceptTouchEvent(true);

			point.setVisibility(View.INVISIBLE);

			// 初始化当前点击的item的信息，数字及坐标
			mGooView.setStatusBarHeight(Utils.getStatusBarHeight(v));
			mGooView.setNumber(number);
			mGooView.initCenter(event.getRawX(), event.getRawY());
			mGooView.setOnDisappearListener(this);

			// 执行添加方法
			mWm.addView(mGooView, mParams);
		}
		// 将所有touch事件转交给GooView处理
		mGooView.onTouchEvent(event);
		return true;
	}

	@Override
	public void onDisappear(PointF mDragCenter) {
		if (mWm != null && mGooView.getParent() != null) {
			mWm.removeView(mGooView);
			
			//播放气泡爆炸动画
			ImageView imageView = new ImageView(mContext);
			imageView.setImageResource(R.drawable.anim_bubble_pop);
			AnimationDrawable mAnimDrawable = (AnimationDrawable) imageView
					.getDrawable();

			final BubbleLayout bubbleLayout = new BubbleLayout(mContext);
			bubbleLayout.setCenter((int) mDragCenter.x, (int) mDragCenter.y
					- Utils.getStatusBarHeight(mGooView));
			
			bubbleLayout.addView(imageView, new FrameLayout.LayoutParams(
					android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
					android.widget.FrameLayout.LayoutParams.WRAP_CONTENT));

			mWm.addView(bubbleLayout, mParams);
			
			mAnimDrawable.start();

			// 播放结束后，删除该bubbleLayout
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					mWm.removeView(bubbleLayout);
				}
			}, 501);
		}
	}

	@Override
	public void onReset(boolean isOutOfRange) {
		// 当气泡弹回时，去除该View，等下次ACTION_DOWN的时候再添加
		if (mWm != null && mGooView.getParent() != null) {
			mWm.removeView(mGooView);
		}
		point.setVisibility(View.VISIBLE);
	}
};