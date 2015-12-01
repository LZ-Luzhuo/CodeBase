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
}
