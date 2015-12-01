package com.example.sideslippanel;

import java.util.Random;

import com.example.sideslippanel.utils.Cheeses;
import com.example.sideslippanel.utils.Utils;
import com.example.sideslippanel.widget.DragLayout;
import com.example.sideslippanel.widget.DragLayout.onDragStatusListener;
import com.example.sideslippanel.widget.MLinearLayout;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private DragLayout draglayout;
	private ListView lv_left;
	private ListView lv_main;
	private ImageView iv_header;
	private MLinearLayout mlinear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		draglayout = (DragLayout) findViewById(R.id.draglayout);
		lv_left = (ListView) findViewById(R.id.lv_left);
		lv_main = (ListView) findViewById(R.id.lv_main);
		iv_header = (ImageView) findViewById(R.id.iv_header);
		mlinear = (MLinearLayout) findViewById(R.id.mlinear);
	}
	
	private void initData() {
		draglayout.setDragStatusListener(new onDragStatusListener() {
			@Override
			public void onOpen() {
				Utils.showQuickToast(MainActivity.this, "onOpen");
				//随机设置一个条目
				int nextInt = new Random().nextInt(50);
				lv_left.smoothScrollToPosition(nextInt); //平滑的滚动到随机数位置
			}
			@Override
			public void onDraging(float percent) {
				Log.i("onDraging", "onDraging:"+percent);
				ViewHelper.setAlpha(iv_header, 1 - percent);
			}
			@Override
			public void onClose() {
				Utils.showQuickToast(MainActivity.this, "onClose");
				//图标晃动
				//iv_header.setTranslationX(translationX);
				ObjectAnimator animator = ObjectAnimator.ofFloat(iv_header, "translationX", 15.0f);
				animator.setInterpolator(new CycleInterpolator(4));
				animator.setDuration(500);
				animator.start();
			}
		});
		
		mlinear.setDraglayout(draglayout);
		
		lv_left.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView text = (TextView)view;
				text.setTextColor(Color.WHITE);
				return text;
			}
		});
		lv_main.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, Cheeses.NAMES));
	}
}
