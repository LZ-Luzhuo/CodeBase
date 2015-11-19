package com.example.sildingfinishdemo.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.sildingfinishdemo.R;
import com.example.sildingfinishdemo.activity.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewActivity extends BaseActivity {
	private List<String> list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

		ListView mListView = (ListView) findViewById(R.id.listView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListViewActivity.this, android.R.layout.simple_list_item_1, list) {

			@Override
			public int getCount() {
				return super.getCount();
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return super.getView(position, convertView, parent);
			}
		};
		mListView.setAdapter(adapter);

		for (int i = 0; i <= 30; i++) {
			list.add("测试数据:" + i);
		}

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startActivity(new Intent(ListViewActivity.this, NormalActivity.class));
			}
		});
	}
}
