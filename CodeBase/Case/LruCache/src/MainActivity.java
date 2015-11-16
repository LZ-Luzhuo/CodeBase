package com.example.lrucachedemo;

import java.io.File;

import com.example.lrucachedemo.utils.ImageCache;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView listview;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GloableParams.context = this;
		setContentView(R.layout.activity_main);
		bitmap = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(),"abc.jpg").getPath());
				
		initView();
		initData();
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.listView);
	}
	
	private void initData() {
		listview.setAdapter(new ImageAdapter());
	}
	
	class ImageAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 100;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		class ViewHodler{
			TextView textview;
			ImageView imageview;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageCache.getInstance().put(position, bitmap);
			ViewHodler viewHodler;
			if(convertView==null){
				viewHodler = new ViewHodler();
				convertView = View.inflate(MainActivity.this, R.layout.item, null);
				viewHodler.textview = (TextView) convertView.findViewById(R.id.textview);
				viewHodler.imageview = (ImageView) convertView.findViewById(R.id.imageview);
				convertView.setTag(viewHodler);
			}else{
				viewHodler = (ViewHodler) convertView.getTag();
			}
			
			viewHodler.textview.setText("第"+position+"张");
			viewHodler.imageview.setImageBitmap(ImageCache.getInstance().get(position));
			
			return convertView;
		}
		
	}
	

}
