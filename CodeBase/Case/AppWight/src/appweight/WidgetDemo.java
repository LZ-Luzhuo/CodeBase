package com.example.appdemo.weight;

import com.example.appdemo.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-26 下午4:18:56
 * 
 * 描述:桌面Wight案例代码,一般只需在onEnabled开启相关功能,onDisabled关闭相关功能.
 * 
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class WidgetDemo extends AppWidgetProvider {
	private Context context;
	private AppWidgetManager awm;
	private ComponentName provider;
	private RemoteViews views;
	
	public WidgetDemo(){}

	public WidgetDemo(Context context){
		this.context = context;
		awm = AppWidgetManager.getInstance(context);
		provider = new ComponentName(context, WidgetDemo.class); //指定更新哪个组件
		views = new RemoteViews(context.getPackageName(), R.layout.wight);  //远程View对象
		
		initData();
	}
	
	private void initData() {
		// 点击事件的广播,当指定控件被点击时发送广播
		//Intent intent = new Intent().setAction("xxx");
		//PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent,PendingIntent.FLAG_UPDATE_CURRENT); //延期意图,用于描述一个动作,发送广播(getBroadcast)
		//views.setOnClickPendingIntent(R.id.textView1, pendingIntent);
	}

	/*
	 * 改变文字
	 */
	public void changeText(String text){
		views.setTextViewText(R.id.textView1, text);
		updateWight();
	}
	
	private void updateWight(){
		awm.updateAppWidget(provider, views);
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		// 刷新,每次拖放/移除都会调用,且多次调用
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		// Wight更新时调用,每次拖放和Wight更新都会调用
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		// 第一次放置Wight时调用,且只调用一次
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		// 最后一个Wight被移除时调用,且只调用一次
	}
	
}
