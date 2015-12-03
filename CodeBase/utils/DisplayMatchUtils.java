package com.example.demo;

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
	
	/**
	 * 根据手机的dpi将 px(像素)单位 转成为 dip单位
	 * @param context 上下文
	 * @param pxValue 像素值
	 * @return dip值
	 */
	public static int pxTodip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 获取屏幕的宽高,宽高封装在DispalyHW里
	 * @param activity 上下文的子类activity
	 * @return 封装了宽高的DispalyHW
	 */
	public static DispalyHW getDisplayHeightWidth(Activity activity){
		// 获取屏幕的宽高
		DisplayMetrics displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		DispalyHW hw = new DispalyHW();
		hw.width = displayMetrics.widthPixels;
		hw.height = displayMetrics.heightPixels;
		return hw;
	}
	
	/**
	 * 封装了宽高的DispalyHW
	 * @author Luzhuo
	 */
	private static class DispalyHW {
		int width;
		int height;
	}
	
	/**
	 * 获取指定缩放比例的LinearLayout.LayoutParams.
	 * 案例:Button.setLayoutParams(DisplayMatchUtils.setParamsScale(0.5f,null));
	 * @param widthScale 设置指定比例如0.5f(50％) 或者null(MATCH_PARENT),值[0f,1f]
	 * @param heightScale 设置指定比例如0.5f(50％) 或者null(MATCH_PARENT),值[0f,1f]
	 * @return LinearLayout.LayoutParams
	 */
	public static LinearLayout.LayoutParams setParamsScale(Activity activity, Float widthScale, Float heightScale){
		// 获取屏幕宽高
		DispalyHW hw = getDisplayHeightWidth(activity);
		int width = hw.width;
		int height = hw.height;
		// 设置LayoutParams的长宽值
		int widthParams = LayoutParams.MATCH_PARENT;
		int heightParams = LayoutParams.MATCH_PARENT;
		if(!(widthScale == null))
			widthParams = (int) (width*widthScale + 0.5f);
		if(!(heightScale == null))
			heightParams = (int) (height*heightScale + 0.5f);
		return new LinearLayout.LayoutParams(widthParams, heightParams);
		
		// 宽占50％(+ 0.5f是为了减少精度损失)
		//LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (width*0.5f + 0.5f), LayoutParams.MATCH_PARENT);
		//new Button(this).setLayoutParams(params);
	}
	
	/** 获取状态栏高度
	 * @param v
	 * @return
	 */
	public static int getStatusBarHeight(View v) {
		if (v == null) {
			return 0;
		}
		Rect frame = new Rect();
		v.getWindowVisibleDisplayFrame(frame);
		return frame.top;
	}
}
