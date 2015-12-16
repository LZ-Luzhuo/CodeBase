package com.example.cursoradapterdemo.adapter;

import com.example.cursoradapterdemo.R;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-15 下午9:50:39
 * 
 * 描述:CursorAdapter的案例.CursorAdapter最大的优点是当数据库改变时会自动获取并刷新数据.
 * <br>传入的cursor必须含有id列.
 * <br>自动刷新:查询代码中添加:cursor.setNotificationUri(context.getContentResolver(), uri); //给游标结果集设置一个通知的uri
 * <br>添加/删除/更新代码中添加:context.getContentResolver().notifyChange(uri, null);// 通知uri("content://dao/person"), 数据改变了, 它会执行重新查询操作, 更新数据
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class DemoAdapter extends CursorAdapter {
	private ViewHolder viewHolder;
	private ListView listview;
	
	public DemoAdapter(Context context, Cursor c, ListView listview) {
		super(context, c, true);
		this.listview = listview;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = View.inflate(context, R.layout.cursor_item, null);
		viewHolder = new ViewHolder();
		viewHolder.name = (TextView) view.findViewById(R.id.name);
		viewHolder.sex = (TextView) view.findViewById(R.id.sex);
		viewHolder.age = (TextView) view.findViewById(R.id.age);
		view.setTag(viewHolder);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		viewHolder = (ViewHolder) view.getTag();
		// 获取数据
		int id = cursor.getInt(0); // CursorAdapter的Cursor中一定要含有id,否则异常
		String name = cursor.getString(1);
		String sex = cursor.getString(2);
		String age = cursor.getString(3);
		
		// 设置数据
		viewHolder.name.setText(name);
		viewHolder.sex.setText(sex);
		viewHolder.age.setText(age+"岁");
	}
	
	public class ViewHolder{
		public TextView name;
		public TextView sex;
		public TextView age;
	}
	
	@Override
	protected void onContentChanged() {
		// 当Cursor改变时回调
		super.onContentChanged();
		
		listview.setSelection(listview.getCount());
	}
}
