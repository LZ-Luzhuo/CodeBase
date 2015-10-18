package com.example.chat.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-18 下午3:05:40
 * 
 * 描述:ChatFragment
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ChatFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView textview = new TextView(getActivity());
		textview.setText("ChatFragment:ChatFragment测试");
		return textview;
	}
}
