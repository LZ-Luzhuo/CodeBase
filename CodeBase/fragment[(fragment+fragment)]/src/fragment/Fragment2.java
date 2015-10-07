package com.example.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 测试Fragment2
 * @author Luzhuo
 *
 */
public class Fragment2 extends BaseFragment {

	@Override
	public View initView(LayoutInflater inflater) {
		TextView textView = new TextView(getActivity());
		textView.setText(Fragment2.class.getSimpleName());
		return textView;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

}
