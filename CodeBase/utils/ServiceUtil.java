package com.example.appdemo.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-24 下午9:38:45
 * 
 * 描述:服务相关工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ServiceUtil {

	/**
	 * 判断服务是否在运行״̬
	 * @param context
	 * @param serviename 服务名称(全类名"com.example.appdemo.service.ServiceDemo")
	 * @return boolean true:在运行/false:已经停止
	 */
	public static boolean isServiceRunning(Context context,String serviename){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> infos = am.getRunningServices(100);
		for(RunningServiceInfo info :infos){
			String classname = info.service.getClassName();
			if(serviename.equals(classname)){
				return true;
			}
		}
		return false;
	}
}
