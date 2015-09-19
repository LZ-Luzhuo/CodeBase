package com.example.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pulltorefresh.R;
import com.example.view.base.BasePage;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-6 下午4:47:31
 * 
 * 描述:该类表示一条page,只需initView和initData即可,相关逻辑由Page处理
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class ItemPage extends BasePage{
	private static final String TAG = "ItemPage";
	private String url;
//	private View topNewsView;
	@ViewInject(R.id.lv_item_news)
	private PullToRefreshListView ptrLv;
	/**
	 * 一条信息内容,用于点击ListView的ItemView时显示在新的Activity中
	 */
	private ArrayList<ItemContent> data = new ArrayList<ItemContent>();
	/**
	 * 存放已读的内容id集合
	 */
	private HashSet<String> readSet = new HashSet<String>();
	MyAdapter adapter;

	public ItemPage(Context context, String url) {
		super(context);
		//传进来url,访问该url获取网络资源,分析并设置
		this.url = url;
	}
	
	@Override
	protected View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_item_news, null);
		ViewUtils.inject(this, view);
		
//		// 自动滚动的viewpager窗口(黑框+标题+提示点)
//		topNewsView = inflater.inflate(R.layout.layout_roll_view, null);
//		ViewUtils.inject(this, topNewsView);
		
		
		/**
		 * Mode.BOTH：同时支持上拉下拉 Mode.PULL_FROM_START：只支持下拉Pulling Down
		 * Mode.PULL_FROM_END：只支持上拉Pulling Up
		 * 需要设置刷新Listener为OnRefreshListener2，并实现onPullDownToRefresh()、onPullUpToRefresh()两个方法。
		 */
		ptrLv.setMode(Mode.BOTH);
		// 得到实际的ListView,设置Item点击事件
		ptrLv.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO
						//开启一个新内容详情页,获取id标记已读,获取标题,url传给新的Activity,并开启它
						Intent intent = new Intent(context, DetailActivity.class);
						String url = "";
						String title;
						ItemContent itemContent;
						if (ptrLv.getRefreshableView().getHeaderViewsCount() > 0) {
							try{
								itemContent = data.get(position - 2);
							}catch(Exception e){
								return;
							}
						} else {
							itemContent = data.get(position);
						}
						url = itemContent.url;
						if(!itemContent.isRead){
							readSet.add(itemContent.id);
							itemContent.isRead= true;
							
							// 保存已读id
							
						}
						title = itemContent.title;
						intent.putExtra("url", url);
						intent.putExtra("title", title);
						context.startActivity(intent);
					}
				});

		/**
		 * setOnRefreshListener(OnRefreshListener listener):设置刷新监听器；
		 * setOnLastItemVisibleListener(OnLastItemVisibleListenerlistener):设置是否到底部监听器；
		 * setOnPullEventListener(OnPullEventListenerlistener);设置事件监听器；
		 * onRefreshComplete()：设置刷新完成
		 * 
		 * pulltorefresh.setOnScrollListener() //设置滚动监听
		 * SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
		 * SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下） 
		 * SCROLL_STATE_IDLE(0) 停止滚动
		 * 
		 * setOnTouchListener设置touch事件 ，回调函数是onTouchEvent（MotionEvent event）,
		 * 然后通过判断event.getAction()是MotionEvent.ACTION_UP还是ACTION_DOWN还是ACTION_MOVE分别作不同行为
		 */
		ptrLv.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			// 下拉刷新(刷新内容)
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				//刷新新闻(获取新数据)
				// TODO 访问网络获取内容
				//模拟数据(新加)
				ItemContent bean = new ItemContent();
				bean.title="下拉刷新";
				bean.content="我是新数据";
				// 添加在最前面
				data.add(0,bean);
				new FinishRefresh().execute();
				adapter.notifyDataSetChanged();
			}

			//上拉刷新(加载内容)
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				//加载新闻(加载剩余部分)
				//模拟数据(新加)
				Log.i(TAG, "上拉刷新");
				ItemContent bean = new ItemContent();
				bean.title="上拉刷新";
				bean.content="我要加载内容";
				// 添加在末尾
				data.add(bean);
				new FinishRefresh().execute();
				adapter.notifyDataSetChanged();
			}
		});
		
		setLastUpdataTime();
		ILoadingLayout startLabels = ptrLv.getLoadingLayoutProxy(true,false);
		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在载入...");// 刷新时
		startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
	
		ILoadingLayout endLabels = ptrLv.getLoadingLayoutProxy(false,true);
		endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在载入...");// 刷新时
		endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
		
		return view;
	}
	
	public void initData() {
		// TODO
		getData();
		adapter = new MyAdapter(context);
		ptrLv.setAdapter(adapter);
		isLoadSuccess = true;
	}

	private class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return data.size();
		}
	
		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item, null);
				viewHolder.title = (TextView) convertView.findViewById(R.id.title);
				viewHolder.content = (TextView) convertView.findViewById(R.id.content);
	
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
	
			viewHolder.title.setText(data.get(position).title);
			viewHolder.content.setText(data.get(position).content);
	
			return convertView;
		}
	
		class ViewHolder {
			TextView title;
			TextView content;
		}
	}


	/**
	 * 设置最后更新的时间
	 */
	private void setLastUpdataTime() {
		// 获取当前时间并将其格式化为yyyy-MM-dd HH:mm:ss格式
		String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		//设置刷新时间标签
		ptrLv.setLastUpdatedLabel(dateString);
	}
	
	/**
	 * 获取数据(设置数据)
	 * @return
	 */
	private void getData() {
		// 设置HeaderView内容,通常设置为自动滚动Viewpager
		if (ptrLv.getRefreshableView().getHeaderViewsCount() <= 1) {
		Log.i(TAG, "ptrLv.getRefreshableView().getHeaderViewsCount():"+ptrLv.getRefreshableView().getHeaderViewsCount());
			TextView text = new TextView(context);
			text.setText("i am a header view");
			ptrLv.getRefreshableView().addHeaderView(text);
		}
		
		for (int i = 0; i < 10; i++) {
			ItemContent bean = new ItemContent();
			bean.title="item:" + i;
			bean.content="这是内容:"+i;
			data.add(bean);
		}
		// 加载完成,隐藏加载动画
		dismissLoadingView();
	}
	
	private class FinishRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return null;
		}
	
		@Override
		protected void onPostExecute(Void result) {
			ptrLv.onRefreshComplete();
		}
	}
	
	@ViewInject(R.id.loading_view)
	protected View loadingView;
	public void dismissLoadingView() {
		if (loadingView != null)
			loadingView.setVisibility(View.INVISIBLE);
	}
}
