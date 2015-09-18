package com.example.view;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;

import com.example.view.base.BasePage;
import com.example.viewpagerindicator.R;
import com.viewpagerindicator.TabPageIndicator;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-6 下午1:37:45
 * 
 * 描述:ViewPager的管理类,该了处理了所有相关page的页面改变监听,适配器设置,以及ItemPage的添加
 * 	  
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class Page extends BasePage{
	
	public Page(Context context) {
		super(context);
	}
	
	@Override
	protected View initView(LayoutInflater inflater) {
		contentView = inflater.inflate(R.layout.frag_news, null);
		return contentView;
	}
	
	@Override
	public void initData() {
		initIndicator();
	}
	
	private ArrayList<ItemPage> pages = new ArrayList<ItemPage>();
	private TabPageIndicator indicator;
	private NewsPagerAdapter adapter;
	private ViewPager pager;
	private int curIndex = 0;
	private void initIndicator() {
		indicator = (TabPageIndicator) contentView.findViewById(R.id.indicator);
		pager = (ViewPager) contentView.findViewById(R.id.pager);
		
		pages.clear();
		// 添加内容和标签
		pages.add(new ItemPage(context, "标签a1"," 标签1 ")); 
		pages.add(new ItemPage(context, "标签a2"," 标签2 ")); 
		pages.add(new ItemPage(context, "标签a3"," 标签3 ")); 
		pages.add(new ItemPage(context, "标签a4"," 标签4 ")); 
		pages.add(new ItemPage(context, "标签a5"," 标签5 ")); 
		pages.add(new ItemPage(context, "标签a6"," 标签6 ")); 
		pages.add(new ItemPage(context, "标签a7"," 标签7 ")); 
		pages.add(new ItemPage(context, "标签a8"," 标签8 ")); 
		pages.add(new ItemPage(context, "标签a9"," 标签9 ")); 
		
		adapter = new NewsPagerAdapter(context,pages);
		pager.removeAllViews();
		pager.setAdapter(adapter);
		
		indicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				ItemPage page = pages.get(arg0);
				if(!page.isLoadSuccess){ //如果加载不成功的再次加载,否则不重新加载
					page.initData();
				}
				curIndex = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		pages.get(0).initData();
		indicator.setViewPager(pager);
		indicator.setCurrentItem(curIndex);
		isLoadSuccess = true;
	}

	class NewsPagerAdapter extends PagerAdapter {
		private ArrayList<ItemPage> pages;

		public NewsPagerAdapter(Context ct, ArrayList<ItemPage> pages) {
			this.pages = pages;
		}
		 @Override  
         public void destroyItem(View container, int position, Object object) {  
			 if(position>=pages.size()) return;
             ((ViewPager)container).removeView(pages.get(position).getContentView());  
         }  
		 @Override
			public CharSequence getPageTitle(int position) {
				return pages.get(position % pages.size()).title;
			}
		 @Override  
         public Object instantiateItem(View arg0, int arg1) {  
             ((ViewPager)arg0).addView(pages.get(arg1).getContentView(),0);  
             return pages.get(arg1).getContentView();  
         }  
		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg1 == arg0;
		}
	}


}
