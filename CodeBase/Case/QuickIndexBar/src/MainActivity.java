package com.example.quickindexbar;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quickindexbar.adapter.HaoHanAdapter;
import com.example.quickindexbar.bean.Person;
import com.example.quickindexbar.utils.Cheeses;
import com.example.quickindexbar.widget.QuickIndexBar;
import com.example.quickindexbar.widget.QuickIndexBar.OnLetterUpdateListener;

public class MainActivity extends ActionBarActivity {
	private QuickIndexBar bar;
	private ListView lv_main;
	private ArrayList<Person> persons;
	private TextView tv_center;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		bar = (QuickIndexBar) findViewById(R.id.bar);
		lv_main = (ListView) findViewById(R.id.lv_main);
		tv_center = (TextView) findViewById(R.id.tv_center);
	}
	
	private void initData() {
		bar.setLetterUpdateListener(new OnLetterUpdateListener() {
			
			@Override
			public void onLetterUpdate(String letter) {
				//Utils.showQuickToast(MainActivity.this, letter);
				showLetter(letter);
				
				// 根据字母定位ListView, 找到集合中第一个以letter为拼音首字母的对象,得到索引
				for (int i = 0; i < persons.size(); i++) {
					Person person = persons.get(i);
					String l = person.pinyin.charAt(0) + "";
					if(TextUtils.equals(letter, l)){
						// 匹配成功
						lv_main.setSelection(i);
						break;
					}
				}
			}
		});
		
		persons = new ArrayList<Person>();
		fillAndSortData(persons);
		lv_main.setAdapter(new HaoHanAdapter(this, persons));
	}

	private void fillAndSortData(ArrayList<Person> persons) {
		// 填充数据
		for (int i = 0; i < Cheeses.NAMES.length; i++) {
			String name = Cheeses.NAMES[i];
			persons.add(new Person(name));
		}
		// 进行排序
		Collections.sort(persons);
	}

	private Handler mHandler = new Handler();
	private void showLetter(String letter){
		tv_center.setVisibility(View.VISIBLE);
		tv_center.setText(letter);
		
		mHandler.removeCallbacksAndMessages(null);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				tv_center.setVisibility(View.GONE);	
			}
		}, 2000);
	}
}
