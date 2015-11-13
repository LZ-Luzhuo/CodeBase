package com.example.asynctaskdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-13 下午3:08:22
 * 
 * 描述:线程池案例
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MainActivity extends Activity {
	private String uri = "";
	private TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}
	
	private void initView() {
		textview = (TextView) findViewById(R.id.textview);
	}

	public void onClick(View v){
		// 线程池
		//new DataTask().execute(params);//如果操作线程过多,等待
		new DataTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);//不用等待
	}
	
	/**
	 * 线程池
	 * @author Luzhuo
	 * 
	 */
	class DataTask extends AsyncTask<String, Void, Bitmap>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// 1.处理任务前,ThreadUI调用
			textview.setText("任务即将处理");
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// 2.运行在辅助线程中,负责耗时任务,可调用publishProgress(values)更新任务进度
			SystemClock.sleep(1000);
			publishProgress();
			SystemClock.sleep(1000);
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			// 3.ThreadUI调用,结果通过该方法传递给UI
			textview.setText("任务完成处理");
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
			// 当调用publishProgress(values)方法时执行该方法,ThreadUI执行,展示界面
			textview.setText("任务正在处理");
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
			// 当ThreadUI中调用onCancelled()方法时调用,取消线程操作
		}
	}
}
