package com.example.gesturedetectordemo.MoveGestureDetector;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-13 下午9:58:04
 * 
 * 描述:Move移动手势代码<br>手指向左滑,-x;向右滑+x;<br>手指向上滑,-y;向下滑+y;
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MoveGestureDetector extends BaseGestureDetector {
	private PointF mCurrentPointer;
	private PointF mPrePointer;
	// 仅仅为了减少创建内存
	private PointF mDeltaPointer = new PointF();
	// 用于记录最终结果，并返回
	private PointF mExtenalPointer = new PointF();
	private OnMoveGestureListener mListenter;

	/**
	 * 移动手势
	 * @param context
	 * @param listener Move监听
	 */
	public MoveGestureDetector(Context context, OnMoveGestureListener listener) {
		super(context);
		mListenter = listener;
	}

	@Override
	protected void handleInProgressEvent(MotionEvent event) {
		int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
		switch (actionCode) {
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mListenter.onMoveEnd(this);
			resetState();
			break;
		case MotionEvent.ACTION_MOVE:
			updateStateByEvent(event);
			boolean update = mListenter.onMove(this);
			if (update) {
				mPreMotionEvent.recycle();
				mPreMotionEvent = MotionEvent.obtain(event);
			}
			break;
		}
	}

	@Override
	protected void handleStartProgressEvent(MotionEvent event) {
		int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
		switch (actionCode) {
		case MotionEvent.ACTION_DOWN:
			resetState();// 防止没有接收到CANCEL or UP ,保险起见
			mPreMotionEvent = MotionEvent.obtain(event);
			updateStateByEvent(event);
			break;
		case MotionEvent.ACTION_MOVE:
			mGestureInProgress = mListenter.onMoveBegin(this);
			break;
		}
	}

	protected void updateStateByEvent(MotionEvent event) {
		final MotionEvent prev = mPreMotionEvent;
		mPrePointer = caculateFocalPointer(prev);
		mCurrentPointer = caculateFocalPointer(event);
		boolean mSkipThisMoveEvent = prev.getPointerCount() != event
				.getPointerCount();
		mExtenalPointer.x = mSkipThisMoveEvent ? 0 : mCurrentPointer.x - mPrePointer.x;
		mExtenalPointer.y = mSkipThisMoveEvent ? 0 : mCurrentPointer.y - mPrePointer.y;
	}

	/**
	 * 根据event计算多指中心点
	 * 
	 * @param event
	 * @return
	 */
	private PointF caculateFocalPointer(MotionEvent event) {
		final int count = event.getPointerCount();
		float x = 0, y = 0;
		for (int i = 0; i < count; i++) {
			x += event.getX(i);
			y += event.getY(i);
		}
		x /= count;
		y /= count;
		return new PointF(x, y);
	}

	public float getMoveX() {
		return mExtenalPointer.x;
	}

	public float getMoveY() {
		return mExtenalPointer.y;
	}

	public interface OnMoveGestureListener {
		public boolean onMoveBegin(MoveGestureDetector detector);

		public boolean onMove(MoveGestureDetector detector);

		public void onMoveEnd(MoveGestureDetector detector);
	}

	public static class SimpleMoveGestureDetector implements
			OnMoveGestureListener {
		@Override
		public boolean onMoveBegin(MoveGestureDetector detector) {
			return true;
		}

		@Override
		public boolean onMove(MoveGestureDetector detector) {
			return false;
		}

		@Override
		public void onMoveEnd(MoveGestureDetector detector) {
		}
	}
}
