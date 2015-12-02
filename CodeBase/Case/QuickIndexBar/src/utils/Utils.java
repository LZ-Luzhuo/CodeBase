package com.example.quickindexbar.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Utils {

	private static Toast quickToast;

	/**
	 * 快速显示的吐司
	 * @param mContext
	 * @param msg
	 */
	public static void showQuickToast(Context context, String msg) {
		if(quickToast == null)
			quickToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		quickToast.setText(msg);
		quickToast.show();
	}
	
	/**
	 * 根据手机的dpi将 dip单位 转为 px(像素)单位
	 * @param context 上下文
	 * @param dpValue dip值
	 * @return px像素值
	 */
	public static int dipTopx(Context context, float dipValue) {
		// 获取屏幕的dpi(密度)
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
