package com.example.chat.lib;

import java.util.Map;

import android.content.Context;

import com.example.chat.lib.callback.ObjectCallback;
import com.example.chat.lib.future.HttpFuture;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-20 下午11:24:23
 * 
 * 描述:对应用一些常用方法的封装(参考)
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ChatManager {
	private static ChatManager instance;
	private Context context;
	
	// 采用单例模式
	public static ChatManager getInstance(Context context) {
		if (instance == null) {
			synchronized (ChatManager.class) {
				if (instance == null) {
					instance = new ChatManager(context);
				}
			}
		}
		return instance;
	}

	private ChatManager(Context context) {
		this.context = context;
	}
	
	/**
	 * 发送请求(Post)
	 * @param url URL地址
	 * @param paramters 参数(非头信息)/null
	 * @param callback 请求完成回调接口
	 * @return HttpFuture 发送数据后连接的管理类
	 */
	@SuppressWarnings("rawtypes")
	public HttpFuture sendRequest(String url, Map<String, String> paramters, final ObjectCallback callback){
		 // 创建 访问端
		 HttpUtils http = new HttpUtils();
		
		 // 请求参数
		 RequestParams params = new RequestParams();
		 if(paramters != null){
			 for(Map.Entry<String, String> pm : paramters.entrySet()){
				 params.addBodyParameter(pm.getKey(), pm.getValue());
			 }
		 }
		 
		// 调用post方法访问网络
		HttpHandler<String> handler = http.send(HttpMethod.POST, url, params,  new RequestCallBack<String>(){

			@Override
			public void onFailure(HttpException error, String msg) {
				 // 这里的错误表示服务器连接的错误
				 LogUtils.i("服务器连接错误onFailure:" + error.getExceptionCode() + ":" + msg);
				 if(callback != null){
					 callback.onFailure(Error.ERROR_SERVER, "服务器连接问题!");
				 }
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// 服务器返回的数据
				String result = responseInfo.result;
				LogUtils.i("服务器返回onSuccess:"+result);
				
				// TODO 网络拿到的Json字符串,进行解析,数据正确状态码返回对象,错误状态码返回错误信息
				 String dataObject = null;
				 // TODO 将数据解析成对象,并返回对象
				 @SuppressWarnings("unchecked")
				Object obj = new Gson().fromJson(dataObject,callback.getDataClass());
				 // TODO 数据正确状态码返回对象,错误状态码返回错误信息
				 boolean flag = false;
				 if (flag) {
					 // 解析正确
					 // 让调用者知道拿到数据了
					 if(callback != null){
						 callback.onSuccess(obj); 
					 }
				 } else {
					 // 这里的错误是服务器返回的数据中数据表示的错误
					 // TODO 错误码
					 int errorCode = 0;
					 // TODO 错误信息
					 String errorString = null;
					 // 让调用者知道出错了,返回错误码和错误信息
					 if(callback != null){
						 callback.onFailure(errorCode,errorString);
					 }
				 }
				 
			}});
		return new HttpFuture(handler);
	}

}
