package com.example.demo.bean;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-21 下午8:37:15
 * 
 * 描述:记录未发送数据信息路径的bean
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class BackTask {

	public long id;
	/**
	 * 信息所有者
	 */
	public String owner;
	/**
	 * 网络未发送信息序列化后的路径
	 */
	public String path;
	/**
	 * 状态信息:0:未发送/1:正在发送/2.发送成功
	 */
	public int state;

}
