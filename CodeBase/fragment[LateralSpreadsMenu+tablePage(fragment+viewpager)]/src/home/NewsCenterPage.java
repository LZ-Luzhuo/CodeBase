package com.example.news.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.news.base.BasePage;

/**
 * page页测试
 * @author Luzhuo
 *
 */
public class NewsCenterPage extends BasePage{

	public NewsCenterPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(LayoutInflater inflater) {
		TextView textView = new TextView(context);
		textView.setText("我是新闻中心");
		return textView;
	}

	@Override
	public void initData() {
		
	}

}
