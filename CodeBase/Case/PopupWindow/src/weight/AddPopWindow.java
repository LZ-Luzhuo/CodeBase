package com.example.appdemo.demo;

import com.example.appdemo.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-22 下午10:23:01
 * 
 * 描述:PopWindow
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class AddPopWindow extends PopupWindow {
	private View inflate;

	public AddPopWindow(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate = inflater.inflate(R.layout.popup_item, null);

		this.setContentView(inflate);
		this.setWidth(LayoutParams.WRAP_CONTENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.update();
		this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	/**
	 * 展示pop
	 * @param view
	 */
	public void show(View view) {
        if (!this.isShowing()) {
        	//显示在view的下方
        	this.showAsDropDown(view, 0, 0);
        	
        	//显示在窗口的指定位置
        	//int[] location = new int[2];
            //view.getLocationInWindow(location); //获取view的坐标  Gravity.LEFT | Gravity.TOP
			//this.showAtLocation(view, Gravity.LEFT | Gravity.TOP, location[0], location[1]-10); //在哪个位置显示,距屏幕的距离px
        } else {
            this.dismiss();
        }
	}
}
