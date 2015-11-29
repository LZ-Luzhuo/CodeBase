package com.luzhuo.waterfall;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
	private int images[] = new int[] { R.drawable.a, R.drawable.b,R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g };
	private ListView lv1;
	private ListView lv2;
	private ListView lv3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		lv1 = (ListView) findViewById(R.id.lv1);
		lv2 = (ListView) findViewById(R.id.lv2);
		lv3 = (ListView) findViewById(R.id.lv3);
	}
	
	private void initData() {
		lv1.setAdapter(new MyAdapter1());
		lv2.setAdapter(new MyAdapter1());
		lv3.setAdapter(new MyAdapter1());
	}
	
	class MyAdapter1 extends BaseAdapter {

		@Override
		public int getCount() {
			return 3000;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = (ImageView) View.inflate(getApplicationContext(),R.layout.lv_item, null);
			int resId = (int) (Math.random() * 4);
			iv.setImageResource(images[resId]);
			return iv;
		}
	}
}
