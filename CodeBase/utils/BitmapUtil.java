package com.example.utilsdemo.utils;

import java.io.File;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-12 下午5:10:13
 * 
 * 描述:图片处理工具类
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class BitmapUtil {
	
	
	/**
	 * 从文件路径获取图片
	 * @param filePath 文件路径
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromFile(File filePath){
		Bitmap bitmap = BitmapFactory.decodeFile(filePath.getPath());
		return bitmap;
	}
	
	/**
	 * 获取Bitmap占用内存的大小
	 * @param bitmap
	 * @return long
	 */
	@SuppressLint("NewApi")
	public static long getBitmapsize(Bitmap bitmap){
		//如果当前版本 >= Android API（12）
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1)
			return bitmap.getByteCount();
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}
