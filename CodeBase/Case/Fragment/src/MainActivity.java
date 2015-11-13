package com.example.fragmentdemo2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.fragmentdemo2.fragment.Fragment1;
import com.example.fragmentdemo2.fragment.Fragment2;
import com.example.fragmentdemo2.fragment.Fragment3;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-13 下午4:09:57
 * 
 * 描述:Fragment使用案例,推荐使用FragmentUtils里的{@link FragmentUtil#changeFragment(int, Fragment, Fragment)}方法
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MainActivity extends FragmentActivity {
	private int containerId = R.id.fragment;
	private Fragment[] fragments;
	private int current;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentUtil.initFragmentUtil(this);
		initData();
	}

	private void initData() {
		// changeFragment推荐使用数组管理
		Fragment1 fragment1 = new Fragment1();
		Fragment2 fragment2 = new Fragment2();
		Fragment3 fragment3 = new Fragment3();
		fragments = new Fragment[]{fragment1,fragment2,fragment3};
		FragmentUtil.getInstance().addFragment(containerId, fragments[0]); //显示第一个
	}

	public void fragment1(View v) {
		// 切换Fragment
//		Fragment1 fragment1 = new Fragment1();
//		FragmentUtil.getInstance().replaceFragment(containerId, fragment1);
		
		//------
		FragmentUtil.getInstance().changeFragment(containerId,fragments[0], fragments[current]);
		current = 0;
	}
	
	public void fragment2(View v) {
		// 传递数据
//		Fragment2 fragment2 = new Fragment2();
//		Bundle bundle = new Bundle();
//		bundle.putString("String", "Hello,World!");
//		FragmentUtil.getInstance().replaceFragment(containerId, fragment2, bundle);
		
		//------不支持传递数据
		FragmentUtil.getInstance().changeFragment(containerId,fragments[1], fragments[current]);
		current = 1;
	}
	
	public void fragment3(View v) {
		// 返回键
//		Fragment3 fragment3 = new Fragment3();
//		FragmentUtil.getInstance().replaceFragment(containerId, fragment3, true);
		
		//------
		FragmentUtil.getInstance().changeFragment(containerId,fragments[2], fragments[current]);
		current = 2;
	}
}
