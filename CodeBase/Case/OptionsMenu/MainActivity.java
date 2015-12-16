package com.example.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-16 下午3:23:58
 * 
 * 描述:手机菜单键的使用案例.
 * <br>{@link MainActivity#onOptionsItemSelected(MenuItem)}经常不被回调,所以不建议使用手机自带的menu功能.
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MainActivity extends Activity {
	private TextView textview;
	private static final int SEARCH_ID = 0, EDIT_ID = 1, CANCEL_EDIT_ID = 2;
	private boolean edit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		textview = (TextView) findViewById(R.id.textview);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 超高压吧规划吧options菜单调用,只会被调用一次
		menu.add(0, SEARCH_ID, 0, "搜索");
		menu.add(0, EDIT_ID, 0, "编辑");
		menu.add(0, CANCEL_EDIT_ID, 0, "取消编辑");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// 当菜单将要显示在屏幕上时.回调此方法
		if(edit){
			menu.findItem(SEARCH_ID).setVisible(false);
			menu.findItem(EDIT_ID).setVisible(true);
			menu.findItem(CANCEL_EDIT_ID).setVisible(true);
		}else{
			menu.findItem(SEARCH_ID).setVisible(true);
			menu.findItem(EDIT_ID).setVisible(false);
			menu.findItem(CANCEL_EDIT_ID).setVisible(false);
		}
		edit = !edit;
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 当options菜单被选中时回调
		switch (item.getItemId()) {
		case SEARCH_ID:
			textview.setText("SEARCH_ID");
			break;
		case EDIT_ID:
			textview.setText("EDIT_ID");
			break;
		case CANCEL_EDIT_ID:
			textview.setText("CANCEL_EDIT_ID");
			break;
		default:
			textview.setText("UnKnown");
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
