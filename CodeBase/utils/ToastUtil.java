package com.example.appdemo.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdemo.R;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-24 下午11:50:14
 * 
 * 描述:Toast工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ToastUtil {
	
	/**
	 * 使用系统的吐司
	 * @param context
	 * @param text
	 */
	public static void showToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 使用自定义的吐司(显示 布局/时长/位置 自己控制)
	 * <br>权限:"android.permission.SYSTEM_ALERT_WINDOW"
	 * @param view 自定义布局  View view = View.inflate(context, R.layout.toast, null);
	 * @param x 位置px
	 * @param y 位置px
	 * @return WindowManager 调用removeView(view)取消显示
	 */
	public static WindowManager showToast1(Context context,View view,int x,int y){
		//View view = View.inflate(context, R.layout.toast, null);
		//TextView textView = (TextView) view.findViewById(R.id.textview);
		//textView.setText(text);
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		//params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = x; //px Toast的位置
		params.y = y;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON; //| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE //打开Touch 
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE; //显示在所有窗体的前面,并处理触摸事件,需要权限
		
		wm.addView(view, params);
		//wm.removeView(view); //去掉Toast
		return wm;
	}
	
	/**
	 * 自定义吐司
	 * @param context
	 * @param text
	 */
	public static void showToastDiy(Context context,String text){
		View view = View.inflate(context, R.layout.toast, null);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(text);
		
		Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		toast.setView(view);
		toast.show();
	}
	
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
