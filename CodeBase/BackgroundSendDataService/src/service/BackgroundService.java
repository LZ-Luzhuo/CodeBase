package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.bean.NetTask;
import com.example.demo.lib.HttpManaer;
import com.example.demo.utils.SerializableUtil;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;


/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-21 下午4:35:14
 * 
 * 描述:IntentService执行在子线程中,并且可以被start不限次数(排队执行)
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class BackgroundService extends IntentService {

	// 使用无参构造,否则报错
	public BackgroundService() {
		super("background");
	}

	// 此方法执行在子线程中
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO 判断当前用户是否存在,不存在不执行
		//Account account = ((ChatApplication)getApplication()).getCurrentAccount();
		//if(account == null)
		//	return;
		
		// TODO 从数据库取数据
		//BackTaskDao dao = new BackTaskDao(this);
		//Cursor cursor = dao.query(account.getAccount(), 0);
		
		// 存储到 map中
		Map<Long, String> map = new HashMap<Long, String>();

		//if (cursor != null) {
		//	while (cursor.moveToNext()) {
		//		// 读取id和路径,存到map集合中
		//		final long id = cursor.getLong(cursor.getColumnIndex(DB.BackTask.COLUMN_ID));
		//		String filePath = cursor.getString(cursor.getColumnIndex(DB.BackTask.COLUMN_PATH));
		//
		//		map.put(id, filePath);
		//	}
		//	cursor.close();
		//}
		
		// 遍历Map集合(获取路径)
		for (Map.Entry<Long, String> me : map.entrySet()) {
			try {
				final Long id = me.getKey();
				String filePath = me.getValue();

				NetTask task = (NetTask) SerializableUtil.read(filePath);
				// TODO: 发送请求
				// TODO 改变状态值(正在处理)
				//dao.updateState(id, 1);

				String url = task.url; 
				Map<String, String> headers = task.headers;
				Map<String, String> paramaters = task.parametes;
				boolean result = HttpManaer.getInstance().post(url, headers, paramaters);

				if (result) {
					System.out.println("处理信息成功!");
					// TODO 改变状态值(处理成功)
					//dao.updateState(id, 2);
				} else {
					System.out.println("处理信息失败!");
					// TODO 改变状态值(处理失败)
					//dao.updateState(id, 0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
