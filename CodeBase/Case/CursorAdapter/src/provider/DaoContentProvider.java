package com.example.cursoradapterdemo.provider;

import com.example.cursoradapterdemo.db.DemoOpenHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-16 上午12:39:19
 * 
 * 描述:内容提供者
 * <br>清单文件配置:<pre>
 * <provider
 * 		android:name="com.example.cursoradapterdemo.provider.DaoContentProvider"
 * 		android:authorities="com.cursoradapterdemo.provider.DaoContentProvider" >
 * </provider>
 * </pre>
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class DaoContentProvider extends ContentProvider{
	private DemoOpenHelper openHelper;
	private static final String AUTHORITY = "com.cursoradapterdemo.provider.DaoContentProvider";
	private static final int PERSON_QUERY_ALL = 0; // 匹配码
	private static final int PERSON_INSERT = 1;
	private static final int PERSON_DELETE = 2;
	private static final int PERSON_UPDATE = 3;
	private static UriMatcher uriMatcher;
	private final String TABLENAME = DemoOpenHelper.TABLE_PERSONS_TABLENAME;	// 表名
	
	private static final Uri cursorUri = Uri.parse("content://com.cursoradapterdemo.provider.DaoContentProvider.cursor"); // 通知数据库改变的uri
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		// content://com.cursoradapterdemo.provider.DaoContentProvider/persons
		uriMatcher.addURI(AUTHORITY, "persons", PERSON_QUERY_ALL);
		// content://com.cursoradapterdemo.provider.DaoContentProvider/persons/insert
		uriMatcher.addURI(AUTHORITY, "persons/insert", PERSON_INSERT);
		uriMatcher.addURI(AUTHORITY, "persons/delete", PERSON_DELETE);
		uriMatcher.addURI(AUTHORITY, "persons/update", PERSON_UPDATE);
	}

	@Override
	public boolean onCreate() {
		openHelper = DemoOpenHelper.getInstance(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		switch (uriMatcher.match(uri)) {
		case PERSON_QUERY_ALL:		// 查询所有的群组数据
			if(db.isOpen()) {
				Cursor cursor = db.query(TABLENAME, projection, selection, selectionArgs, null, null, sortOrder);
				// 给游标结果集设置一个通知的uri
				cursor.setNotificationUri(getContext().getContentResolver(), cursorUri);
				return cursor;
			}
			break;
		default:
			throw new IllegalArgumentException("UnKnow Uri : " + uri);
		}
		return null;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case PERSON_INSERT:
			if(db.isOpen()) {
				long id = db.insert(TABLENAME, null, values);
				// 通知cursorUri, 数据改变了, 它会执行重新查询操作, 更新数据
				getContext().getContentResolver().notifyChange(cursorUri, null);
				return ContentUris.withAppendedId(uri, id);
			}
			break;
		default:
			throw new IllegalArgumentException("UnKnow Uri : " + uri);
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		switch (uriMatcher.match(uri)) {
		case PERSON_DELETE: // 当前删除单个群组
			SQLiteDatabase db = openHelper.getWritableDatabase();
			if(db.isOpen()) {
				long group_id = ContentUris.parseId(uri);
				String where = "_id = " + group_id;
				int count = db.delete(TABLENAME, where, null);
				
				// 通知Sms.GROUPS_QUERY_ALL_URI, 数据改变了, 它会执行重新查询操作, 更新数据
				getContext().getContentResolver().notifyChange(cursorUri, null);
				return count;
			}
			break;
		default:
			throw new IllegalArgumentException("UnKnow Uri : " + uri);
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
		switch (uriMatcher.match(uri)) {
		case PERSON_UPDATE:	// 更新群组表的操作
			SQLiteDatabase db = openHelper.getWritableDatabase();
			if(db.isOpen()) {
				int count = db.update(TABLENAME, values, selection, selectionArgs);
				
				// 通知Sms.GROUPS_QUERY_ALL_URI, 数据改变了, 它会执行重新查询操作, 更新数据
				getContext().getContentResolver().notifyChange(cursorUri, null);
				return count;
			}
			break;
		default:
			throw new IllegalArgumentException("UnKnow Uri : " + uri);
		}
		return 0;
	}
}
