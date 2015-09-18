package com.example.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.view.base.BasePage;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-6 下午4:47:31
 * 
 * 描述:该类表示一条page,只需initView和initData即可,相关逻辑由Page处理
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ItemPage extends BasePage{
	private String url;
	public String title;

	public ItemPage(Context context, String url,String title) {
		super(context);
		this.title = title;
		this.url = url;
	}
	
	@Override
	protected View initView(LayoutInflater inflater) {
		contentView = new TextView(context);
		return contentView;
	}

	public void initData() {
		((TextView)contentView).setText(url);
		isLoadSuccess = true;
	}

}
