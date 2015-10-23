package com.example.tcp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.tcp.client.ConnectorManager;
import com.example.tcp.client.receiver.PushReceiver;
import com.example.tcp.client.request.Request;
import com.example.tcp.client.request.TextRequest;
import com.example.tcp.client.service.CoreService;
import com.lidroid.xutils.util.LogUtils;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-23 上午12:23:28
 * 
 * 描述:关于长连接的案列,在子线程中进行阻塞,效率低,适合少量用户的链接.如果用户量大,可以考虑使用mina的nio(非阻塞).
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MainActivity extends Activity {
	private EditText et;
	
	/**
	 * 接收CoreService发送过来的广播
	 */
	private PushReceiver receiver = new PushReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			LogUtils.i("receive");

			if (PushReceiver.ACTION_TEXT.equals(action)) {
				String text = intent.getStringExtra(PushReceiver.DATA_KEY);
				
				// TODO 对接收到的数据进行处理
				LogUtils.i("广播接收数据:"+text);
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et = (EditText) findViewById(R.id.et);
		
		// 开启服务(服务里做打开连接,认证用户信息等操作)
		startService(new Intent(this, CoreService.class));
		
		// 动态注册广播接收者
		IntentFilter filter = new IntentFilter();
		filter.addAction(PushReceiver.ACTION_TEXT);
		registerReceiver(receiver, filter);
		
	}
	
	public void sendMessage(View v){
		LogUtils.i("TCP----------");
		
		final String content = et.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			return;
		}

		Request request = new TextRequest(GlobalParams.sender, GlobalParams.token, GlobalParams.receiver, content);
		ConnectorManager.getInstance().putRequest(request);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 注销广播监听
		unregisterReceiver(receiver);
		
		// 关闭服务(如果需要),服务器会关闭连接
		stopService(new Intent(this, CoreService.class));
	}
}
