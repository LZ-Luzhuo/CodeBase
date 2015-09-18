package com.example.db.dao.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.db.dao.DBHelper;
import com.example.db.dao.annotation.Column;
import com.example.db.dao.annotation.ID;
import com.example.db.dao.annotation.TableName;

public abstract class DAOSupport<M> implements DAO<M>{
	protected Context context;
	protected DBHelper helper;
	protected SQLiteDatabase db;
	
	public DAOSupport(Context context) {
		super();
		this.context = context;
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	@Override
	public long insert(M m) {
		ContentValues values = new ContentValues();
		fillColumn(m,values); //����Դ��һ�������������Ŀ��ڶ�������
		return db.insert(getTableName(), null, values);
	}

	@Override
	public int delete(Serializable id) {
		return db.delete(getTableName(), DBHelper.TABLE_ID+" =?", new String[]{id.toString()});
	}

	@Override
	public int update(M m) {
		ContentValues values = new ContentValues();
		fillColumn(m, values);
		return db.update(getTableName(), values, DBHelper.TABLE_ID+" =?", new String[]{getId(m)});
	}

	/**
	 * ������ѡ��
	 * *��bugδ�޸������ʹ��findAll()
	 * @param columns  ��Ҫ����
	 * @param selection  ѡ������
	 * @param selectionArgs  ѡ��������ֵ
	 * @return
	 */
	public List<M> findByCondition(String[] columns, String selection, String[] selectionArgs){
		return findByCondition(columns, selection, selectionArgs, null, null, null, null);
	}
	
	/**
	 * ������ѯ
	 * *��bugδ�޸������ʹ��findAll()
	 * @param columns  ��Ҫ����
	 * @param selection  ѡ������ (name='xx')
	 * @param selectionArgs  ѡ��������ֵ
	 * @param groupBy  ������� (name)
	 * @param having  ������� (name='xx';name��xx������)
	 * @param orderBy  ���� (_id desc)
	 * @param limit  ��ҳ���� (3:3�����ݣ�2,3:����2����¼ѡ��3����¼)
	 * @return
	 */
	public List<M> findByCondition(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
		List<M> result = null; //List<M>
		Cursor cursor = db.query(getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		if(cursor!=null){
			result = new ArrayList<M>();
			while(cursor.moveToNext()){
				M m = getInstance();
				fillField(cursor,m);
				result.add(m);
			}
			cursor.close();
		}
		return result;
	}
	
	@Override
	public List<M> findAll() {
		List<M> result = null; //List<M>
		Cursor cursor = db.query(getTableName(), null, null, null, null, null, null);
		if(cursor!=null){
			result = new ArrayList<M>();
			while(cursor.moveToNext()){
				M m = getInstance();
				fillField(cursor,m);
				result.add(m);
			}
			cursor.close();
		}
		return result;
	}


	/**
	 * ����һ�������Ļ�ȡ
	 * @return
	 */
	private String getTableName(){
		// ÿ�����Ӧһ������ʵ��
		// ����һ������ܹ���ȡ��ʵ�壬��ȡ��ʵ��ļ����ƣ�����ĸСд
		// ������������ע�⣬ʵ������ݿ������������ϵ
		
		// ��ȡ��ʵ�塪��������
		M m = getInstance();
		// ��ȡʵ���ע�⣬����value�����õ�ֵȷ�����������ݿ��
		
		// �����Ҫ�����е�ʱ���ȡ��ע�����Ϣ�����ô��ʱ��
		TableName tableName = m.getClass().getAnnotation(TableName.class); //annotationType��ע�������
		if(tableName!=null){
			return tableName.value();
		}
		return "";
	}

	/**
	 * ���������ν�ʵ���е����ݣ����ն�Ӧ��ϵ���뵽���ݿ����
	 */
	private void fillColumn(M m, ContentValues values) {
		m.getClass().getFields(); //for all public fields for the class 
		Field[] fields = m.getClass().getDeclaredFields();
		for (Field item : fields) {
			item.setAccessible(true);
			Column column = item.getAnnotation(Column.class);
			if(column!=null){
				String key = column.value();
				//Returns the value of the field in the specified object
				try {
					String value = item.get(m).toString();
					
					// �����field�������������������ģ�������ӵ�������
					ID id = item.getAnnotation(ID.class);
					if(id!=null && id.autoincrement()){
					}else{
						values.put(key, value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	/**
	 * �������������ݱ����е����ݣ����ն�Ӧ��ϵ����ʵ����
	 * @param cursor
	 * @param m
	 */
	private void fillField(Cursor cursor, M m) {
		Field[] fields = m.getClass().getDeclaredFields();
		for (Field item : fields) {
			item.setAccessible(true);
			Column column = item.getAnnotation(Column.class);
			if(column!=null){
				int columnIndex = cursor.getColumnIndex(column.value());
				if(!(columnIndex==-1)){
					String value = cursor.getString(columnIndex);
					// Sets the value of the field in the specified object to the value. 
					try {
						if(item.getType()==int.class){
							item.set(m, Integer.parseInt(value));
						}else{
							item.set(m, value);
						}
					} catch (IllegalAccessException | IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	
	/**
	 * �����ģ���ȷʵ������������ȡ�������з�װ��ֵ
	 */
	private String getId(M m) {
		Field[] fields = m.getClass().getDeclaredFields();
		for (Field item : fields) {
			item.setAccessible(true);
			ID id = item.getAnnotation(ID.class);
			if(id!=null){
				try {
					return item.get(m).toString();
				} catch (IllegalAccessException | IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * �����壺ʵ��Ķ��󴴽�
	 * @return
	 */
	private M getInstance(){
		// ʵ���Ǻ�ʱȷ����
		// ���ĸ����ӵ��ø÷��������ĸ�����������
		Class clazz = getClass(); //��ȡʵ������ʱ����
		// �ڻ�ȡ�ú��ӵĸ���("֧�ַ���"�ĸ���)
		Type superclass = clazz.getGenericSuperclass(); //�ܻ�ȡ������
		// ����ʵ�ֽӿ�(������������),�涨�˷��͵�ͨ�ò���
		if(superclass!=null && superclass instanceof ParameterizedType){
			// �ۻ�ȡ�������еĲ���
			Type[] arguments = ((ParameterizedType)superclass).getActualTypeArguments();
			try {
				return (M) ((Class)arguments[0]).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
