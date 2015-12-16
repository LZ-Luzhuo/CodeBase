package com.example.cursoradapterdemo;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cursoradapterdemo.adapter.DemoAdapter;
import com.example.cursoradapterdemo.bean.Person;
import com.example.cursoradapterdemo.db.Dao;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-16 上午1:46:15
 * 
 * 描述:查询/插入数据库时,使用异步的方式稍微优于使用原生的方式.
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MainActivity extends Activity {
	private TextView name, sex, age;
	private ListView listview;
	private Dao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}
	
	private void initView() {
		name = (TextView) findViewById(R.id.name);
		sex = (TextView) findViewById(R.id.sex);
		age = (TextView) findViewById(R.id.age);
		listview = (ListView) findViewById(R.id.listview);
	}

	private void initData() {
		// 查询数据
//		dao = new Dao(this);
//		Cursor cursor = dao.findAll();
//		listview.setAdapter(new DemoAdapter(this, cursor, listview));
		
		
		// 通过异步查询类,使用内容访问者方式获取数据
		AsyncQueryHandler asyncQuery = new AsyncQueryHandler(getContentResolver()) {
			@Override
			protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
				super.onQueryComplete(token, cookie, cursor);
				// 查询完成
				listview.setAdapter(new DemoAdapter(MainActivity.this, cursor, listview));
			}
		};
		asyncQuery.startQuery(0, null, Uri.parse("content://com.cursoradapterdemo.provider.DaoContentProvider/persons"), null, null, null, null);
	}

	public void onClick(View v){
		if(TextUtils.isEmpty(name.getText().toString().trim()) || TextUtils.isEmpty(sex.getText().toString().trim()) || TextUtils.isEmpty(age.getText().toString().trim()))
			return;

		// 插入数据
//		Person person = new Person();
//		person.name = name.getText().toString().trim();
//		person.sex = sex.getText().toString().trim();
//		person.age = age.getText().toString().trim();
//		dao.insert(person);
			
		// 通过异步插入类,使用内容访问者插入数据
		AsyncQueryHandler asyncQuery = new AsyncQueryHandler(getContentResolver()) { };
		
		ContentValues initialValues = new ContentValues();
		initialValues.put("name", name.getText().toString().trim());
		initialValues.put("sex", sex.getText().toString().trim());
		initialValues.put("age", age.getText().toString().trim());
		asyncQuery.startInsert(0, null, Uri.parse("content://com.cursoradapterdemo.provider.DaoContentProvider/persons/insert"), initialValues);
	}
}
