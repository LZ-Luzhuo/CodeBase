package com.example.gesturedetectordemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.gesturedetectordemo.MoveGestureDetector.MoveGestureDetector;

public class BigImageView extends View {
	private MoveGestureDetector mDetector; // 用户move手势

	public BigImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		mDetector = new MoveGestureDetector(getContext(),
				new MoveGestureDetector.SimpleMoveGestureDetector() {
					@Override
					public boolean onMove(MoveGestureDetector detector) {
						int moveX = (int) detector.getMoveX();
						int moveY = (int) detector.getMoveY();
						//手指向左滑,-x;向右滑+x;<br>手指向上滑,-y;向下滑+y;
						System.out.println("moveX" + moveX + ";moveY" + moveY);
						return true;
					}
				});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 触摸事件
		mDetector.onToucEvent(event);
		return true;
	}

}
