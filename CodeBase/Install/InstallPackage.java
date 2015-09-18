package com.example.install3.utils;

import java.io.File;
import java.io.PrintStream;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-3 下午1:58:57
 * 
 * 描述:安装工具类:普通安装(有界面)
 * 			       静默安装(无界面,root权限)
 * 			       静默安装(无界面,无root权限)--[不通用.已去除]
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class InstallPackage {
	/**
	 * 静默安装(无界面,需要root权限)
	 * 将apk文件重命名为 libcom.so 放在libs目录下的 armeabi 目录下.(只支持安装一个)
	 * @param context  上下文
	 */
	public static void rootSilentInstall(final Context context){
		new Thread(){
			private PrintStream ps;
			private Process p;
			public void run() {
				super.run();
				try{
					p = Runtime.getRuntime().exec("su"); //申请root权限
					ps = new PrintStream(p.getOutputStream());
					ps.println("export LD_LIBRAY_PATH=/vendor/lib:/system/lib");
					ps.println("pm install -r /data/data/"+context.getPackageName()+"/lib/libcom.so"); //非libcom.so文件名会出现不可知的安装失败
				}catch(Exception e){
					e.printStackTrace();
					ps.close();
				}finally{
					ps.flush();
					ps.close();
				}
			};
		}.start();
	}
	
	/**
	 * 普通安装(有界面)
	 * @param context  上下文
	 * @param filePath  apk路径
	 */
	public static void install(Context context,String filePath){
	 String path = filePath.trim();
	  //安装apk
	  Intent intent = new Intent();
	  intent.setAction("android.intent.action.VIEW");
	  intent.addCategory("android.intent.category.DEFAULT");
	  intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
	  context.startActivity(intent);
	}
}
