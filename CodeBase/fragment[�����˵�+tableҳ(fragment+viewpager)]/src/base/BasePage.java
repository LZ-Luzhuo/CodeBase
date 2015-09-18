package com.example.news.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Page页根类
 * @author Luzhuo
 *
 */
public abstract class BasePage {

	protected View view;
	protected Context context;

	/**
	 * 1.画界面
	 * 2.初始化数据
	 */
	public BasePage(Context context) {
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = initView(inflater);
	}
	
	public View getRootView(){
		return view;
	}

	public abstract View initView(LayoutInflater inflater);
	
	public abstract void initData();
}
