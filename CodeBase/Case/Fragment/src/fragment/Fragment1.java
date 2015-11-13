package com.example.fragmentdemo2.fragment;

import com.example.fragmentdemo2.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-7 下午10:04:02
 * 
 * 描述:</h3>生命周期:<br>切换Fragment:onCreate() --> onCreateView --> onStart() --> onResume | --> onPause --> onStop --> onDestory
 *				    <br>主页键:Fragment:onCreate() --> onCreateView --> onStart() --> onResume | --> onPause --> onStop
 * 				    <br>隐藏Fragment:onCreate() --> onCreateView --> onStart() --> onResume |
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class Fragment1 extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		System.out.println("onCreateView");
		View view = inflater.inflate(R.layout.fragment1, null);
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("onCreate");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		System.out.println("onStart");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		System.out.println("onResume");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		System.out.println("onPause");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		System.out.println("onStop");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("onDestroy");
	}
}
