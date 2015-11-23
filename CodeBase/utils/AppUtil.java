package com.example.appdemo.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-23 下午2:30:45
 * 
 * 描述:App相关的工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class AppUtil {
	/**
	 * 获取当前应用程序的版本号
	 * <pre> android:versionCode="1"  //int类型
     * android:versionName="1.0.1"//String类型,当前获取的是该信息</pre>
	 * @return 版本号/""
	 */
	public static String getVersion(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			// 代表的就是清单文件的信息。
			PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 安装apk文件
	 * @param file 文件路径
	 */
	public static void installApk(Context context,File file) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}
