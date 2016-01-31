package com.example.utilsdemo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
	
	/**
	 * 从assets获取图片
	 * @param context 上下文
	 * @param fileName 资源名
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromAssets(Context context, String fileName) {
		Bitmap image = null;
		AssetManager am = context.getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	/**
	 * 将Drawable保存到文件
	 */
	public static void saveDrawable(Drawable drawable, File file) throws Exception{
		Bitmap bitmap=((BitmapDrawable)drawable).getBitmap(); // 先把Drawable转成Bitmap
		saveBitmap(bitmap, file);
	}
	
	/**
	 * 将Bitmap保存到文件
	 */
	public static void saveBitmap(Bitmap bitmap, File file) throws Exception{
		FileOutputStream fop = new FileOutputStream(file);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fop); // 格式可以为jpg,png,jpg不能存储透明
		fop.close();
	}
}
