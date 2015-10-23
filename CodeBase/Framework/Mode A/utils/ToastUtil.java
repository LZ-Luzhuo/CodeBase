package com.example.chat.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-18 下午11:05:25
 * 
 * 描述:自定义Toast工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ToastUtil {

	public static void show(Context context, String content) {
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

	public static void show(Context context, int contentId) {
		Toast.makeText(context, contentId, Toast.LENGTH_SHORT).show();
	}
}