package com.example.sensor.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-12 下午9:08:23
 * 
 * 描述:传感器工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class SensorUtil {
	private static SensorManager manager;
	private static SensorEventListener listener;
	static float lastX, lastY, lastZ;
	static long lasttime, duration = 100;
	static float total, switchValue = 200;// 判断手机摇晃的阈值

	/**
	 * 摇一摇;
	 * <br>获取三个轴的加速度值，记录，当过一段时间之后再次获取三个轴的加速度值，计算增量，将相邻两个点的增量进行汇总，当达到设置好的阈值回调{@link OnShakeLinstener#onShake()}
	 * <br>{@link SensorUtil#unOnShakeLinstener()}注销监听
	 * <br>清单文件限制横竖屏android:screenOrientation="portrait"
	 */
	public static void Shake(Context context, final OnShakeLinstener onShakeLinstener) {
		manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		listener = new SensorEventListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onSensorChanged(SensorEvent event) {
				// 判断：是否是第一个点
				if (lasttime == 0) {
					lastX = event.values[SensorManager.DATA_X];
					lastY = event.values[SensorManager.DATA_Y];
					lastZ = event.values[SensorManager.DATA_Z];

					lasttime = System.currentTimeMillis();
				} else {
					long currenttime = System.currentTimeMillis();
					// 尽可能屏蔽掉不同手机差异
					if ((currenttime - lasttime) >= duration) {
						// 第二点及以后
						float x = event.values[SensorManager.DATA_X];
						float y = event.values[SensorManager.DATA_Y];
						float z = event.values[SensorManager.DATA_Z];

						// 屏蔽掉微小的增量
						float dx = Math.abs(x - lastX);
						float dy = Math.abs(y - lastY);
						float dz = Math.abs(z - lastZ);
						if (dx < 1) { dx = 0; }
						if (dy < 1) { dy = 0; }
						if (dz < 1) { dz = 0; }
						 //极个别的手机，静止某个轴的增量大于1,10以上100以上
						 if(dx==0||dy==0||dz==0) { init(); }
						
						// 一点和二点总增量
						float shake = dx + dy + dz;

						if (shake == 0) { init(); }
							total += shake;

						if (total >= switchValue) {
							onShakeLinstener.onShake();
							init();
						} else {
							lastX = event.values[SensorManager.DATA_X];
							lastY = event.values[SensorManager.DATA_Y];
							lastZ = event.values[SensorManager.DATA_Z];
							lasttime = System.currentTimeMillis();
						}
					}
				}
			}
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) { }
		};
		manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	private static void init(){
		lastX = 0;
		lastY = 0;
		lastZ = 0;
		lasttime = 0;
		total = 0;
	}
	
	/**
	 * 注销摇一摇监听
	 */
	public static void unOnShakeLinstener(){
		manager.unregisterListener(listener);
	}
	
	public interface OnShakeLinstener{
		/**
		 * 摇一摇
		 */
		public void onShake();
	}
}
