package com.example.cursoradapterdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.cursoradapterdemo.bean.Person;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-15 下午11:04:26
 * 
 * 描述:数据库实际操作类
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class Dao {
	private DemoOpenHelper helper;
	private SQLiteDatabase db;
	private Context context;
	private Uri uri = Uri.parse("content://dao/person");

	public Dao(Context context) {
		this.context = context;
		helper = DemoOpenHelper.getInstance(context);
		db = helper.getWritableDatabase();
	}
	
	/**
	 * 插入数据
	 * @param person
	 * @return 返回插入行/-1插入失败
	 */
	public long insert(Person person) {
		if(db.isOpen()) {
			ContentValues values = new ContentValues();
			values.put("name", person.name);
			values.put("sex", person.sex);
			values.put("age", person.age);
			
			long id = db.insert(DemoOpenHelper.TABLE_PERSONS_TABLENAME, null, values);
			// 通知uri("content://dao/person"), 数据改变了, 它会执行重新查询操作, 更新数据
			context.getContentResolver().notifyChange(uri, null);
			return id;
		}
		return -1;
	}
	
	/**
	 * 查询所有数据
	 * @return Cursor/null
	 */
	public Cursor findAll() {
		if(db.isOpen()) {
			Cursor cursor = db.query(DemoOpenHelper.TABLE_PERSONS_TABLENAME, null, null, null, null, null, null);
			cursor.setNotificationUri(context.getContentResolver(), uri); //给游标结果集设置一个通知的uri
			return cursor;
		}
		return null;
	}
}
