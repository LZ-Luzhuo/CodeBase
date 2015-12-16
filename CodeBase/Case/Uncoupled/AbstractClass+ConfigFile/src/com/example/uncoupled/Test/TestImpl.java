package com.example.uncoupled.Test;

import android.view.View;
import android.widget.TextView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-12 下午5:24:09
 * 
 * 描述:Test抽象类的实现类
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class TestImpl extends Test {

	@Override
	public void show(View v) {
		((TextView)v).append("TestImpl");
	}
}
