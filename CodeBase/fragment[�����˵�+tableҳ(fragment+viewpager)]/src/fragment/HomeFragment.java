package com.example.news.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.news.R;
import com.example.news.base.BaseFragment;
import com.example.news.base.BasePage;
import com.example.news.home.FunctionPage;
import com.example.news.home.GowAffairsPage;
import com.example.news.home.NewsCenterPage;
import com.example.news.home.SettingPage;
import com.example.news.home.SmartServicePage;
import com.example.news.view.CustomViewPager;
import com.example.news.view.LazyViewPager.OnPageChangeListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class HomeFragment extends BaseFragment {
	private CustomViewPager viewpager;
	private RadioGroup main_radio;
	private int checkedId = R.id.rb_function; //默认加载第一页
	private View view;

	/** 
	 * 1.初始化viewPager通过使用adapter的形式实现
	 */
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.frag_home2, null);
		
		viewpager = (CustomViewPager) view.findViewById(R.id.viewpager);
		main_radio = (RadioGroup) view.findViewById(R.id.main_radio);

		return view;
	}
	
	List<BasePage> list = new ArrayList<>();

	@Override
	public void initData(Bundle savedInstanceState) {
		list.add(new FunctionPage(context));
		list.add(new NewsCenterPage(context));
		list.add(new SmartServicePage(context));
		list.add(new GowAffairsPage(context));
		list.add(new SettingPage(context));
 
		HomePageAdapter adapter = new HomePageAdapter(context,list);
		viewpager.setAdapter(adapter);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//如果位置是0的话,才能出现滑动菜单,如果是其他的tab出现的时候,滑动菜单就给屏蔽掉
				if(0 == position){
					sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else{
					sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
				BasePage page = list.get(position);
				page.initData();
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		
		main_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_function:
					viewpager.setCurrentItem(0, false);
					checkedId = 0;
					break;

				case R.id.rb_news_center:
					viewpager.setCurrentItem(1, false);
					checkedId = 1;
					break;
				case R.id.rb_smart_service:
					viewpager.setCurrentItem(2, false);
					checkedId = 2;
					break;
				case R.id.rb_gov_affairs:
					viewpager.setCurrentItem(3, false);
					checkedId = 3;
					break;
				case R.id.rb_setting:
					viewpager.setCurrentItem(4, false);
					checkedId = 4;
					break;
				}
				
			}
		});
	}
	
	class HomePageAdapter extends PagerAdapter{
		private Context context;
		private List<BasePage> list;

		public HomePageAdapter(Context context, List<BasePage> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((CustomViewPager)container).removeView(list.get(position).getRootView());
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((CustomViewPager)container).addView(list.get(position).getRootView(), 0);
			return list.get(position).getRootView();
		}
	}
}
