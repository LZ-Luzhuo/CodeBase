package com.example.appdemo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	
	/**
	 * 拷贝文件
	 * @param shujuyuan
	 * @param file
	 * @throws IOException
	 */
	public static void copyFile(File shujuyuan, File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try{
			if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
			if(!file.exists()) file.createNewFile();
			
			bis = new BufferedInputStream(new FileInputStream(shujuyuan));
			bos = new BufferedOutputStream(new FileOutputStream(file));
			
			byte[] bys = new byte[1024];
			int len = 0;
			while((len=bis.read(bys))!=-1){
				bos.write(bys,0,len);
				bos.flush();
			}
		}catch(Exception e){ e.printStackTrace();
		}finally{
			if(bos!=null)
				try {
					bos.close();
				} catch (Exception e) { }
			if(bis!=null)
				try {
					bis.close();
				} catch (Exception e) { }
		}
	}
}
