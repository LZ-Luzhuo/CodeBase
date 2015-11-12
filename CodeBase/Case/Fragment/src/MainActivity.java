package com.example.fragmentdemo2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.fragmentdemo2.fragment.Fragment1;
import com.example.fragmentdemo2.fragment.Fragment2;
import com.example.fragmentdemo2.fragment.Fragment3;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentUtil.initFragmentUtil(this);
	}

	public void fragment1(View v) {
		// 切换Fragment
		Fragment1 fragment1 = new Fragment1();
		
		FragmentUtil.getInstance().changeFragment(R.id.fragment, fragment1);
	}
	
	public void fragment2(View v) {
		// 传递数据
		Fragment2 fragment2 = new Fragment2();
		Bundle bundle = new Bundle();
		bundle.putString("String", "Hello,World!");
		
		FragmentUtil.getInstance().changeFragment(R.id.fragment, fragment2, bundle);
	}
	
	public void fragment3(View v) {
		// 返回键
		Fragment3 fragment3 = new Fragment3();
		
		FragmentUtil.getInstance().changeFragment(R.id.fragment, fragment3, true);
	}
}
