package com.example.demo.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-21 下午4:02:48
 * 
 * 描述:封装网络请求的Bean,需要实现序列化接口
 * 
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class NetTask implements Serializable {

	/**
	 * URL
	 */
	public String url;
	/**
	 * 请求方法:0.POST 1.GET
	 */
	public int method;
	/**
	 * 请求头集合
	 */
	public Map<String, String> headers = new HashMap<String, String>();
	/**
	 * 请求参数集合
	 */
	public Map<String, String> parametes = new HashMap<String, String>();

}
