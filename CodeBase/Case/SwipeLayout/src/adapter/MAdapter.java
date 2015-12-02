package com.example.swipelayout.adapter;

import static com.example.swipelayout.utils.Cheeses.NAMES;

import java.util.ArrayList;

import com.example.swipelayout.R;
import com.example.swipelayout.widget.SwipeLayout;
import com.example.swipelayout.widget.SwipeLayout.OnSwipeLayoutListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MAdapter extends BaseAdapter implements ListAdapter {
	private Context context;
	private ArrayList<SwipeLayout> opendItems;

	public MAdapter(Context context) {
		this.context = context;
		opendItems = new ArrayList<SwipeLayout>();
	}

	@Override
	public int getCount() {
		return NAMES.length;
	}

	@Override
	public Object getItem(int position) {
		return NAMES[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(convertView == null){
			view = View.inflate(context, R.layout.swipelayout, null);
		}
		ViewHolder mHolder = ViewHolder.getHolder(view);
		mHolder.tv_name.setText(NAMES[position]);
		
		SwipeLayout sl = (SwipeLayout)view;
		sl.setSwipeLayoutListener(new OnSwipeLayoutListener() {
			@Override
			public void onStartOpen(SwipeLayout mSwipeLayout) {
				// 要去开启时,先遍历所有已打开条目, 逐个关闭
				for (SwipeLayout layout : opendItems) {
					layout.close(true);
				}
				opendItems.clear();
			}
			@Override
			public void onStartClose(SwipeLayout mSwipeLayout) {
			}
			@Override
			public void onOpen(SwipeLayout mSwipeLayout) {
				// 添加进集合
				opendItems.add(mSwipeLayout);
			}
			@Override
			public void onDraging(SwipeLayout mSwipeLayout) {
			}
			@Override
			public void onClose(SwipeLayout mSwipeLayout) {
				// 移除集合
				opendItems.remove(mSwipeLayout);
			}
		});
		return view;
	}
	
	static class ViewHolder {
		TextView tv_call;
		TextView tv_del;
		TextView tv_name;
		public static ViewHolder getHolder(View view) {
			Object tag = view.getTag();
			if(tag == null){
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.tv_call = (TextView)view.findViewById(R.id.tv_call);
				viewHolder.tv_del = (TextView)view.findViewById(R.id.tv_del);
				viewHolder.tv_name = (TextView)view.findViewById(R.id.tv_name);
				tag = viewHolder;
				view.setTag(tag);
			}
			return (ViewHolder)tag;
		}
	}
}
