package com.example.appdemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-23 下午2:37:13
 * 
 * 描述:Stream相关的工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class StreamUtil {
	/**
	 * 接收字节输入流转为字符串
	 * @param is 字节输入流
	 * @return String 字符串
	 * @throws IOException 
	 */
	public static String inputStreamToString(InputStream is) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = is.read(buffer))!=-1){
			baos.write(buffer, 0, len);
		}
		is.close();
		String result = baos.toString();
		baos.close();
		return result;
	}
}
