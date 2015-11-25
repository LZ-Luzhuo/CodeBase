package com.example.appdemo.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
	
	/**
	 * 卸载当前应用
	 * @param context
	 */
	public static void uninstallApk(Context context){
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DELETE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:"+context.getPackageName()));
		context.startActivity(intent);
	}
	
	/**
	 * 获取所有的安装的应用程序信息(耗时操作,请在辅助线程进行)
	 * @return List<AppInfo>
	 */
	public static List<AppInfo> getAppInfos(Context context){
		PackageManager pm = context.getPackageManager();
		//所有的安装在系统上的应用程序包信息。
		List<PackageInfo> packInfos = pm.getInstalledPackages(0);
		List<AppInfo> appInfos = new ArrayList<AppInfo>();
		for(PackageInfo packInfo : packInfos){
			AppInfo appInfo = new AppInfo();
			//packInfo  相当于一个应用程序apk包的清单文件
			String packname = packInfo.packageName;
			Drawable icon = packInfo.applicationInfo.loadIcon(pm);
			String name = packInfo.applicationInfo.loadLabel(pm).toString();
			int flags = packInfo.applicationInfo.flags;//应用程序信息的标记
			if((flags&ApplicationInfo.FLAG_SYSTEM)==0){
				appInfo.userApp=true;
			}else{
				appInfo.userApp=false;
			}
			if((flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)==0){
				//手机的内存
				appInfo.inRom=true;
			}else{
				//手机外存储设备
				appInfo.inRom=false;
			}
			appInfo.packname=packname;
			appInfo.icon=icon;
			appInfo.name=name;
			appInfos.add(appInfo);
		}
		return appInfos;
	}
	
	public static class AppInfo {
		public Drawable icon;
		public String name;
		public String packname;
		public boolean inRom; //安装位置:true手机内存;falseSD卡
		public boolean userApp; //用户应用:true用户应用;false系统
	}
}
