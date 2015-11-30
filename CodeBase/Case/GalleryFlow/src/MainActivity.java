package com.example.galleryflowdemo;

import com.example.galleryflowdemo.widgt.GalleryFlow;
import com.example.galleryflowdemo.widgt.ImageAdapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-30 下午7:14:37
 * 
 * 描述:相册浏览3D效果的Gallery
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MainActivity extends ActionBarActivity {
	int[] images = { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,R.drawable.i};
	private GalleryFlow galleryFlow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		galleryFlow = (GalleryFlow) findViewById(R.id.gallery_flow);
	}

	private void initData() {
		ImageAdapter adapter = new ImageAdapter(this, images);
		adapter.createReflectedImages();
		galleryFlow.setAdapter(adapter);
	}

}
