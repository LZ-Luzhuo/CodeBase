package com.example.appdemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
public class TimeDateUtil {
	
	/**
	 * 一定时间内间隔性执行;在一段时间内每隔一定时间执行某项任务;totals > intervals 才能执行;
	 * @param totals 总的任务时间
	 * @param intervals 时间间隔
	 * @param task 执行任务的接口;<b>注意只调用接口里的onTask(long) 和 onFinish()方法</b>;{@link SimpleTaskImple}是Task接口的简单实现类
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
	
	/**
	 * 周期性执行;延迟delay秒之后每隔period秒周期性的执行某项任务,调用{@link #stopPeriodTime(Object[])}取消任务,如需更新界面参考{@link android.app.Activity#runOnUiThread(Runnable)}
	 * @param task 任务接口,<b>注意:只调用接口里的onTask()方法;</b>;{@link SimpleTaskImple}是Task接口的简单实现类
	 * @param delay 延迟
	 * @param period 每隔多少秒(周期性)
	 * @return Object[] 对象数组,可传给{@link #stopPeriodTime(Object[])}方法将其停止.
	 */
	public static Object[] periodTime(final Task task,int delay,int period) {
		Timer timer = new Timer();
		TimerTask timetask = new TimerTask() {
			@Override
			public void run() {
				task.onTask();
			}
		};
		timer.schedule(timetask, 0, 3000); //执行的任务/第一次执行时的延迟/每隔多久执行
		
		Object[] obj = new Object[]{timer,timetask};
		return obj;
	}
	
	/**
	 * 停止周期性时间任务,与{@link #periodTime(Task, int, int)}配合使用
	 * @param obj
	 */
	public static void stopPeriodTime(Object[] obj){
		if(obj == null) return;
		((Timer) obj[0]).cancel(); //取消执行
		((TimerTask) obj[1]).cancel();
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
		/**
		 * 周期性执行的任务
		 */
		void onTask();
	}
	
	public static class SimpleTaskImple implements Task{
		public void onTask(long millisUntilFinished) {}
		public void onFinish() {}
		public void onTask() {}
	}
	
	/**
	 * 格式化时间(格式:"上次更新: yyyy-MM-dd")
	 * @param dates
	 * @return
	 */
	public static String getStringDate(long dates){
		Date date = new Date(dates);
		SimpleDateFormat sdf = new SimpleDateFormat("上次更新: yyyy-MM-dd");
		return sdf.format(date);
	}
}
