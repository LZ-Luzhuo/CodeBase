package com.example.fragmentdemo2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragmentdemo2.FragmentUtil;
import com.example.fragmentdemo2.R;

public class Fragment2 extends Fragment {
	private TextView textview;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment2, null);
		initView(view);
		initData();
		return view;
	}

	private void initView(View view) {
		textview = (TextView) view.findViewById(R.id.textview);
	}
	
	private void initData() {
		String string  = FragmentUtil.getInstance().getBundle(this).getString("String");;
		textview.setText(string);
	}
}
