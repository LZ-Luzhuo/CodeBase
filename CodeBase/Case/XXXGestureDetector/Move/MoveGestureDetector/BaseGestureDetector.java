package com.example.gesturedetectordemo.MoveGestureDetector;

import android.content.Context;
import android.view.MotionEvent;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-13 下午9:22:05
 * 
 * 描述:XXXGestureDetector
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public abstract class BaseGestureDetector {
	protected boolean mGestureInProgress;
	protected MotionEvent mPreMotionEvent;
	protected MotionEvent mCurrentMotionEvent;
	protected Context mContext;

	public BaseGestureDetector(Context context) {
		mContext = context;
	}

	public boolean onToucEvent(MotionEvent event) {
		if (!mGestureInProgress) {
			handleStartProgressEvent(event);
		} else {
			handleInProgressEvent(event);
		}
		return true;
	}

	protected abstract void handleInProgressEvent(MotionEvent event);

	protected abstract void handleStartProgressEvent(MotionEvent event);

	protected abstract void updateStateByEvent(MotionEvent event);

	protected void resetState() {
		if (mPreMotionEvent != null) {
			mPreMotionEvent.recycle();
			mPreMotionEvent = null;
		}
		if (mCurrentMotionEvent != null) {
			mCurrentMotionEvent.recycle();
			mCurrentMotionEvent = null;
		}
		mGestureInProgress = false;
	}
}
