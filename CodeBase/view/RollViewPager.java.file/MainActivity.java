package com.example.demo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demo.RollViewPager.OnPagerClickCallback;

/**
 * 这是RollViewPager.java的示范代码
 * @author Luzhuo
 *
 */
public class MainActivity extends Activity {

	protected static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		LinearLayout liear = (LinearLayout) findViewById(R.id.dots_ll);
		liear.removeAllViews();
		
		ArrayList<View> dots;
		int size = 5; // 假设有10张图片,添加10个点
		dots = new ArrayList<View>();
		for (int i = 0; i < size; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					9, 9);  //点View的大小
			params.setMargins(5, 0, 5, 0); // 左右各5点
			View m = new View(this);
			if (i == 0){
				m.setBackgroundResource(R.drawable.dot_focus);
			}else{
				m.setBackgroundResource(R.drawable.dot_normal);
			}
			m.setLayoutParams(params);
			dots.add(m);
			liear.addView(m);
		}
		RollViewPager rollview = new RollViewPager(this, dots,
				R.drawable.dot_focus, R.drawable.dot_normal, new OnPagerClickCallback() {

					@Override
					public void onPagerClick(int position) {
						Log.i(TAG, position+"我被点击了");

					}
				});
		ArrayList<String> uriList = new ArrayList<>();
		uriList.add("http://p1.so.qhimg.com/t01a3b98fdd50d240db.jpg");
		uriList.add("http://d.hiphotos.baidu.com/image/pic/item/cefc1e178a82b901392d7bb7708da9773812efb6.jpg");
		uriList.add("http://h.hiphotos.baidu.com/image/pic/item/54fbb2fb43166d2210c1f78b442309f79052d27f.jpg");
		uriList.add("http://b.hiphotos.baidu.com/image/pic/item/dbb44aed2e738bd49a236490a48b87d6277ff915.jpg");
		uriList.add("http://g.hiphotos.baidu.com/image/pic/item/2e2eb9389b504fc223730913e6dde71190ef6dfc.jpg");
		
		TextView title = (TextView) findViewById(R.id.text);
		ArrayList<String> titles = new ArrayList<>();
		titles.add("美女1号");
		titles.add("美女2号");
		titles.add("美女3号");
		titles.add("美女4号");
		titles.add("美女5号");
		
		rollview.setUriList(uriList); //网络图片资源(图片地址) 
		rollview.setTitle(title, titles); //设置标题 
		rollview.startRoll(); //自动滚动
		
		FrameLayout frame = (FrameLayout) findViewById(R.id.fram);
		frame.addView(rollview);
		
	}

}
