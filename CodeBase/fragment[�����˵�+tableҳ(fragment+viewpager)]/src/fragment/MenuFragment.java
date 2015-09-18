package com.example.news.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.news.MainActivity;
import com.example.news.R;

/**
 * 菜单Fragment
 * @author Luzhuo
 *
 */
public class MenuFragment extends BaseFragment implements OnItemClickListener {
	private View view;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment f = null;
		switch (position) {
		case 0:
			f = new Fragment1();
			break;
		case 1:
			f = new Fragment2();
			break;
		}
		switchFragment(f);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.list_view, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		ListView list_view = (ListView) view.findViewById(R.id.list_view);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1,initData());
		list_view.setAdapter(adapter);
		
		list_view.setOnItemClickListener(this);
	}
	
	private List<String> initData() {
		List<String> list = new ArrayList<>();
		list.add("fragment1");
		list.add("fragment2");
		return list;
	}

	private void switchFragment(Fragment f) {
		if(f !=null){
			if(getActivity() instanceof MainActivity){
				((MainActivity)getActivity()).switchFragment(f);
			}
		}
	}

}
