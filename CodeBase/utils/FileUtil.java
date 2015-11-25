package com.example.appdemo.utils;


import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-25 下午5:07:56
 * 
 * 描述:文件目录相关的工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class FileUtil {
	
	/**
	 * 获取某个目录的可用空间
	 * @param path 空间路径
	 * @return long 可用空间大小(B)
	 */
	@SuppressWarnings("deprecation")
	public static long getAvailSpace(Context context,String path) {
		StatFs statf = new StatFs(path);
		statf.getBlockCount();// 获取分区的个数
		long size = statf.getBlockSize();// 获取分区的大小
		long count = statf.getAvailableBlocks();// 获取可用的区块的个数
		return size * count;
	}
	
	/**
	 * 获取手机SD卡的可用空间
	 * @return long 可用空间大小(B)/-1
	 */
	public static long getSDAvailSpace(Context context){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return getAvailSpace(context, Environment.getExternalStorageDirectory().getAbsolutePath());
		return -1;
	}
	
	/**
	 * 获取手机内部存储空间的可用空间
	 * @return long 可用空间大小(B)
	 */
	public static long getDataAvailSpace(Context context){
		return getAvailSpace(context, Environment.getDataDirectory().getAbsolutePath());
	}
}
