package com.example.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-21 下午4:10:41
 * 
 * 描述:序列化工具类(被序列化的类要实现Serializable接口)
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class SerializableUtil {

	/**
	 * 序列化
	 * @param t 对象
	 * @param outPath 路径
	 * @throws Exception
	 */
	public static <T extends Serializable> void write(T t, String outPath)
			throws Exception {
		ObjectOutputStream oos = null;
		try {
			File file = new File(outPath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(t);
		} finally {
			if (oos != null) {
				oos.close();
			}
		}
	}

	/**
	 * 反序列化
	 * @param path 路径
	 * @return 序列化对象/失败null
	 * @throws Exception
	 */
	public static Serializable read(String path) throws Exception {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			Object object = ois.readObject();

			if (object != null) {
				return (Serializable) object;
			}
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
		return null;
	}
}
