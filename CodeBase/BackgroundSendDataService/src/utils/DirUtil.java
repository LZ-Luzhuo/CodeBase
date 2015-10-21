package com.example.demo.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-18 下午11:06:21
 * 
 * 描述:文件目录的管理工具,比如缓存目录位置
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class DirUtil {

	public static String getTaskDir(Context context) {
		String dir = getDir(context, "task");
		System.out.println("dir : " + dir);
		return dir;
	}

	public static String getIconDir(Context context) {
		String dir = getDir(context, "icon");
		System.out.println("dir : " + dir);
		return dir;
	}

	private static String getDir(Context context, String path) {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(state)) {
			File root = Environment.getExternalStorageDirectory();

			File dir = new File(root, "Android/data/" + context.getPackageName() + "/" + path);

			if (!dir.exists()) {
				dir.mkdirs();
			}
			return dir.getAbsolutePath();
		} else {
			File dir = new File(context.getFilesDir(), path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return dir.getAbsolutePath();
		}
	}

}