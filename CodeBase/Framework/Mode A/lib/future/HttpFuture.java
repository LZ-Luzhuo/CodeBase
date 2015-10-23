package com.example.chat.lib.future;

import com.lidroid.xutils.http.HttpHandler;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-21 下午2:21:42
 * 
 * 描述:发送数据后连接的管理类
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class HttpFuture {
	@SuppressWarnings("rawtypes")
	private HttpHandler handler;

	@SuppressWarnings("rawtypes")
	public HttpFuture(HttpHandler handler){
		this.handler = handler;
	}
	
	/**
	 * 连接是否取消
	 * @return true:已经取消/false:连线
	 */
	public boolean isCancelled(){
		return handler.isCancelled();
	}
	
	/**
	 * 连接是否关闭
	 * @return true:已经关闭/false:连线
	 */
	public boolean isFinished(){
		return handler.isPaused(); //源代码直接 return false;
	}
	
	/**
	 * 打断发送
	 * @param mayInterruptIfRunning true:打断连接/false:不处理
	 */
	public void cancel(boolean mayInterruptIfRunning){
		handler.cancel(mayInterruptIfRunning);
	}
}
