package com.example.appdemo.utils;

import android.os.CountDownTimer;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-26 下午3:17:35
 * 
 * 描述:时间日期相关的工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class TimeDataUtil {
	
	/**
	 * 每隔一段时间执行某项任务;totals > intervals 才能执行;
	 * @param totals 总的任务时间
	 * @param intervals 时间间隔
	 * @param task 执行任务的接口
	 */
	public static void intervalTime(long totals,long intervals, final Task task) {
		if(totals < intervals) return;
		CountDownTimer cdt = new CountDownTimer(totals, intervals) { //总执行时间,间隔多久执行onTick()方法
			@Override
			public void onTick(long millisUntilFinished) {
				// 开始就执行,然后间隔执行
				task.onTask(millisUntilFinished);
			}
			@Override
			public void onFinish() {
				// 最后一次执行该方法
				task.onFinish();
			}
		};
		cdt.start();
	}
	
	public interface Task{
		/**
		 * 间隔执行任务
		 * @param millisUntilFinished 执行任务后剩余的时间
		 */
		void onTask(long millisUntilFinished);
		/**
		 * 执行最后一次任务
		 */
		void onFinish();
	}
}
