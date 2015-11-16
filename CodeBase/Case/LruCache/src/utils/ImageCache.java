package com.example.lrucachedemo.utils;

import java.io.File;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.example.lrucachedemo.ConstantValue;
import com.example.lrucachedemo.GloableParams;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-12 下午3:46:50
 * 
 * 描述:图片缓存管理,管理范围包括内存和磁盘缓存.<br>如果获取不到数据,说明数据已经被清除,需要重新从网络获取
 * <br>清除内存缓存策略:先添加先清除,后添加后清除
 * 
 * 修订历史:<br>有些手机本身就没有sd卡功能,需要修改磁盘缓存路径
 * 
 * 
 * =================================================
 **/
public class ImageCache {
	// 使用最大可用内存值的1/5作为缓存的大小。
	private static final int MAXSIZE = (int) (Runtime.getRuntime().maxMemory() / 5);
	// 使用磁盘100M空间
	private static final int DIS_CACHE_SIZE = 1024 * 1024 * 100;

	private static ImageCache cache = new ImageCache();

	public static ImageCache getInstance() {
		return cache;
	}

	// LruCache<标记, 图片>
	private LruCache<Object, Bitmap> lrucache;// 图片的缓存;设置的Value必须能够计算出所占有的内存的大小
	private DiskLruCache diskLruCache;// 将图片缓存在SDCard上

	private ImageCache() {
		lrucache = new LruCache<Object, Bitmap>(MAXSIZE) {

			@Override
			protected int sizeOf(Object key, Bitmap value) {
				System.out.println("内存已缓存:"+lrucache.size()+";总用于该空间:"+MAXSIZE);
				// size的变动代表该bitmap占用的内存大小
				return getSize(value);
			}

			@Override
			protected void entryRemoved(boolean evicted, Object key,
					Bitmap oldValue, Bitmap newValue) {
				// evicted 为true表示MAXSIZE不够用
				if (evicted) {
					Log.d("memoryLruCache", "remove:" + key.toString());
				}
				super.entryRemoved(evicted, key, oldValue, newValue);
			}
		};

		// sd卡是否挂在状态(有些手机可能没有SD卡,就使用系统缓存目录)
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File externalStorageDirectory = Environment.getExternalStorageDirectory();
			String path = externalStorageDirectory.getAbsolutePath() + ConstantValue.IMAGE_PATH;
			
			diskLruCache = DiskLruCache.openCache(GloableParams.context, new File(path), DIS_CACHE_SIZE);
		}else{
			File cacheDir = GloableParams.context.getCacheDir();
			String path = cacheDir.getAbsolutePath() + ConstantValue.IMAGE_PATH;
			
			diskLruCache = DiskLruCache.openCache(GloableParams.context, new File(path), DIS_CACHE_SIZE);
		}
	}

	/**
	 * 获取图片占用内存的大小
	 * @param value
	 * @return
	 */
	@SuppressLint("NewApi")
	private int getSize(Bitmap value) {
		// 如果当前版本 >= API12
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return value.getByteCount();
		}
		return value.getRowBytes() * value.getHeight();
	}

	/**
	 * 添加图片,如果添加的图片超过了设置的最大内存缓存,将清除一部分旧的图片
	 * @param key
	 * @param value
	 */
	public void put(Object key, Bitmap value) {
		lrucache.put(key, value);

		if (diskLruCache != null) {
			diskLruCache.put(key.toString(), value);
		}
	}

	public Bitmap get(Object key) {
		Bitmap bitmap = lrucache.get(key);

		// 如果内存里已经没有
		if (bitmap == null) {
			if (diskLruCache != null) {
				bitmap = diskLruCache.get(key.toString());
			}
		}
		return bitmap;
	}

	/**
	 * 清空所有缓存,包括内存和磁盘
	 */
	public void clear() {
		lrucache.evictAll();
		if (diskLruCache != null) {
			diskLruCache.clearCache();
		}
	}
}
