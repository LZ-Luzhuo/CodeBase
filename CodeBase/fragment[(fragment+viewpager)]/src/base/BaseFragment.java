package com.example.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	protected View view;
	protected Context context;
	protected SlidingMenu sm;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		sm = ((MainActivity)getActivity()).getSlidingMenu();
	}

	/**
	 * setContentView等价
	 */
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = initView(inflater);
		return view;
	}

	public View getRootView(){
		return view;
	}

	/**
	 * 初始化View
	 * @param inflater
	 * @return
	 */
	public abstract View initView(LayoutInflater inflater);
	
	/**
	 * 初始化View
	 * @param inflater
	 * @return
	 */
	public abstract void initData(Bundle savedInstanceState);

}
