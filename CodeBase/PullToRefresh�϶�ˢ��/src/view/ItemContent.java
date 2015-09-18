package com.example.luzhuo.view;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-11 下午1:54:33
 * 
 * 描述:这是表示一条内容,显示于ListView的Item被点击时,显示在新的Activity中
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ItemContent {
	/**
	 * id标识
	 */
	public String id;
	/**
	 * 内容的标题
	 */
	public String title;
	/**
	 * 内容的链接
	 */
	public String url;
	/**
	 * 访问网络获取的你日用
	 */
	public String content;
	/**
	 * 是否已读
	 */
	public boolean isRead;
}
