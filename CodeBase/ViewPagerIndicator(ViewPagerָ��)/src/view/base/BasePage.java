package com.example.view.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-6 下午4:45:02
 * 
 * 描述:Page的根类
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public abstract class BasePage {
	protected Context context;
	protected View contentView;
	public boolean isLoadSuccess=false; //是否已经被加载
	
	public BasePage(Context context) {
		this.context = context;
		contentView = initView((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		
	}
	public View getContentView() {
		return contentView;
	}
	
	protected abstract View initView(LayoutInflater inflater);

	public abstract void initData();

}
