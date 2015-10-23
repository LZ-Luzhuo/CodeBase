package com.example.chat.lib.callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.example.chat.domain.Account;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-20 下午11:47:38
 * 
 * 描述:ChatManager的网络请求回调接口,泛型必须写(new ObjectCallback<Account>).
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public abstract class ObjectCallback<T> {
	private final Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public ObjectCallback(){
		//通过泛型,获得当前类的类型
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}
	
	public Class<T> getDataClass(){
		return clazz;
	}
	
	/**
	 * 获取数据成功
	 * @param obj 返回解析后的对象
	 */
	public abstract void onSuccess(T obj);
	
	/**
	 * 获取数据失败
	 * @param errorCode 错误码
	 * @param errorString 错误信息
	 */
	public abstract void onFailure(int errorCode, String errorString);
}
