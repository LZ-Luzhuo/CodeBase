package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.demo.bean.BackTask;
import com.example.demo.bean.NetTask;
import com.example.demo.service.BackgroundService;
import com.example.demo.utils.DirUtil;
import com.example.demo.utils.SerializableUtil;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		//未联网时数据后台发送案例****************************************//
		// 路径信息
		String url = "http://192.168.1.101:8080/ChatServer/user/name";
		// 头信息
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("account", "aaa");
		headers.put("token", "123");
		// 参数信息
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", "zhangsan");

		// 将请求加入到后台任务
		// 1.封装成Bean
		NetTask request = new NetTask();
		request.url = url;
		request.method = 0;
		request.headers = headers;
		request.parametes = parameters;

		// 2.序列化
		String outPath = DirUtil.getTaskDir(this) + "/" + System.currentTimeMillis();
		try {
			SerializableUtil.write(request, outPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// TODO 3.记录未发送路径到数据库
		BackTask task = new BackTask();
		task.owner = "aaa";
		task.path = outPath;
		task.state = 0;
		//TODO 添加到数据库
		//new BackTaskDao(getActivity()).addTask(task);

		// 4.开启服务(连接状态发送,非连接状态不处理)
		startService(new Intent(this, BackgroundService.class));
	}
}
