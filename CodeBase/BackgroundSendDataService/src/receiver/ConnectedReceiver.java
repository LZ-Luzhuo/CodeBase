package com.example.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.demo.net.NetUtil;
import com.example.demo.service.BackgroundService;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-21 下午4:18:48
 * 
 * 描述:监听网络改变的监听器,
 * 		需要在清单文件里配置
 *      <receiver android:name=".receiver.ConnectedReceiver" >
 *          <intent-filter>
 *               <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
 *          </intent-filter>
 *      </receiver>
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ConnectedReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// 判断网络是否连接
		if(!NetUtil.checkNet(context))
			return;
		context.startService(new Intent(context,BackgroundService.class));
	}

}
