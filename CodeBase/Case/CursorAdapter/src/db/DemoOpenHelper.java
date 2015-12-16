package com.example.cursoradapterdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-15 下午10:36:08
 * 
 * 描述:数据库帮助类,代码中的被注释的代码在升级数据库版本的时候使用.
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class DemoOpenHelper extends SQLiteOpenHelper {
	private static DemoOpenHelper openHelper;
	
	private static final String DATABASENAME = "demo.db";
	private static final int START_VERSION = 1;
	//private static final int HISTORY_VERSION_1 = 1; //历史版本
	//private static final int CURRENT_VERSION = 1; //现在版本
	
	public static final String TABLE_ID = "_id";
	
	public static final String TABLE_PERSONS_TABLENAME = "persons"; //表明
	public static final String TABLE_PERSONS_NAME = "name";
	public static final String TABLE_PERSONS_SEX = "sex";
	public static final String TABLE_PERSONS_AGE = "age";

	private DemoOpenHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	}
	
	public static DemoOpenHelper getInstance(Context context){
		// 单例模式获取数据库帮助类对象
		if(openHelper == null){
			synchronized (DemoOpenHelper.class){
				if(openHelper == null){
					openHelper = new DemoOpenHelper(context, DATABASENAME, null, START_VERSION);
				}
			}
		}
		return openHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建表
		String sql = "create table "+TABLE_PERSONS_TABLENAME+" ("+TABLE_ID+" integer primary key, "+TABLE_PERSONS_NAME+" varchar(30), "+TABLE_PERSONS_SEX+" varchar(30), "+TABLE_PERSONS_AGE+" varchar(30) ); ";
		db.execSQL(sql);
		
		// 更新表
		//onUpgrade(db, START_VERSION, CURRENT_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		switch (oldVersion) {
//		case START_VERSION:
//			db.execSQL("");
//		case HISTORY_VERSION_1:
//			db.execSQL("");
//		case 3:
//			break;
//		}
	}
}
