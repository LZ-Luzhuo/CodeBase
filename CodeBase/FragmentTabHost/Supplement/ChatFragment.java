package com.example.chat.fragment;



import com.example.chat.R;
import com.example.chat.widget.NormalTopBar;

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
 * 描述:ChatFragment,NormalTopBar的使用案例
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ChatFragment extends Fragment {
	private NormalTopBar mTopBar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fra_chat, container, false);
		
		//NormalTopBar的使用
		mTopBar = (NormalTopBar) view.findViewById(R.id.chat_top_bar);
		mTopBar.setBackVisibility(true);
		mTopBar.setTitle("Chat");
		
		return view;
	}
}
