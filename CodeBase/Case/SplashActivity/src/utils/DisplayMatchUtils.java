package com.example.splashactivity.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-15 上午8:07:38
 * 
 * 描述:手机屏幕显示适配类,这是用于手机屏幕显示的匹配工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class DisplayMatchUtils {
	
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
