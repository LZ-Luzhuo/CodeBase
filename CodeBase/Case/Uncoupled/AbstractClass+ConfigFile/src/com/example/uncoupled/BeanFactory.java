package com.example.uncoupled;

import java.util.Properties;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-12 下午5:16:31
 * 
 * 描述:依据配置文件加载实例的工厂类,配置文件必须放在src目录下.
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class BeanFactory {
	private static Properties properties;
	static{
		try {
			properties=new Properties();
			properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties"));
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * 根据对应的抽象类加载对应的实现类
	 * @param clazz 对应的抽象类
	 * @return 对应的实现类/null
	 */
	@SuppressWarnings("unchecked")
	public static<T> T getImpl(Class<T> clazz) {
		try {
			String key=clazz.getSimpleName();
			String className = properties.getProperty(key);
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
}
