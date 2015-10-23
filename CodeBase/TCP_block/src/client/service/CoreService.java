package com.example.tcp.client.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.tcp.GlobalParams;
import com.example.tcp.client.Connector;
import com.example.tcp.client.ConnectorManager;
import com.example.tcp.client.ConnectorManager.ConnectorListener;
import com.example.tcp.client.receiver.PushReceiver;
import com.example.tcp.client.request.AuthRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-21 下午11:13:16
 * 
 * 描述:长连接服务,
 * 		需配置清单文件:<service android:name=".service.CoreService"/>
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class CoreService extends Service implements ConnectorListener {
	private ConnectorManager connectorManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		connectorManager = ConnectorManager.getInstance();

		new Thread(new Runnable() {

			@Override
			public void run() {
				connectorManager.setConnectorListener(CoreService.this);

				//连接,发送认证信息
				AuthRequest request = new AuthRequest(GlobalParams.sender, GlobalParams.token);
				connectorManager.connnect(request);
			}
		}).start();
	}

	/**
	 * 将收到的数据发送广播
	 */
	@Override
	public void pushData(String data) {
		// 获得Connector从服务器获得的信息
		LogUtils.i("coreService_data : " + data);

		Intent intent = new Intent();
		intent.setAction(PushReceiver.ACTION_TEXT);
		intent.putExtra(PushReceiver.DATA_KEY, data);
		sendBroadcast(intent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtils.i("disconnect");
		connectorManager.disconnect();
	}

}
