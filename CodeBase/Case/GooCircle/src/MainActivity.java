package com.example.goocircle;

import static com.example.goocircle.utils.Cheeses.NAMES;

import com.example.goocircle.utils.Utils;
import com.example.goocircle.widget.GooViewListener;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.listview);
	}

	private void initData() {
		listview.setAdapter(new SimpleAdapter());
	}

	class SimpleAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return NAMES.length;
		}
		@Override
		public Object getItem(int position) {
			return NAMES[position];
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		class ViewHolder{
			TextView names;
			TextView point;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView == null){
				viewHolder = new ViewHolder();
				convertView = View.inflate(MainActivity.this, R.layout.list_item, null);
				viewHolder.names = (TextView) convertView.findViewById(R.id.names);
				viewHolder.point = (TextView) convertView.findViewById(R.id.point);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.names.setText(NAMES[position]);
			viewHolder.point.setText(position+"");
			viewHolder.point.setVisibility(View.VISIBLE);
			
			viewHolder.point.setTag(position);
			GooViewListener gooViewListener = new GooViewListener(MainActivity.this, viewHolder.point){
				@Override
				public void onDisappear(PointF mDragCenter) {
					super.onDisappear(mDragCenter);
					Utils.showQuickToast(MainActivity.this, "onDisappear");
				}
			};
			viewHolder.point.setOnTouchListener(gooViewListener);
			
			return convertView;
		}
	}
}
