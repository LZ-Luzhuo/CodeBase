package com.example.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.example.chat.Fragment.ChatFragment;
import com.example.chat.Fragment.ContactFragment;
import com.example.chat.Fragment.DiscoverFragment;
import com.example.chat.Fragment.MeFragment;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-18 下午2:29:57
 * 
 * 描述:首页
 * 
 * 修订历史:v4包
 * 
 * 
 * =================================================
 **/
public class HomeActivity extends FragmentActivity implements OnTabChangeListener {
	private final static String TAB_CHAT = "chat";
	private static final String TAB_CONTACT = "contact";
	private static final String TAB_DISCOVER = "discover";
	private static final String TAB_ME = "me";
	
	private FragmentTabHost tabhost;
	private TabIndicatorView chatIndicator;
	private TabIndicatorView contactIndicator;
	private TabIndicatorView discoverIndicator;
	private TabIndicatorView meIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_home);

		// 1. 初始化TabHost
		tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabhost.setup(this, getSupportFragmentManager(), R.id.ac_home_container); // 最后一个参数是用来切换fragment
		
		//start:将以下代码拷贝并修改4份,就是4个tab了*************************//
		
		// 2. 新建TabSpec(管理tabwidget)
		TabSpec spec = tabhost.newTabSpec(TAB_CHAT); //标识一个tabwidget
		//实现自定义的tab
		//spec.setIndicator("消息"); //tab文字
		chatIndicator = new TabIndicatorView(this);
		chatIndicator.setTabTitle("Chat");
		chatIndicator.setTabIcon(R.drawable.tab_icon_chat_normal, R.drawable.tab_icon_chat_focus);
		spec.setIndicator(chatIndicator);
		
		// 3. 添加TabSpec
		tabhost.addTab(spec, ChatFragment.class, null);
		
		//end******************************************************//
		
		//end_demo_演示多个tab代码******************************************************//
		spec = tabhost.newTabSpec(TAB_CONTACT);
		contactIndicator = new TabIndicatorView(this);
		contactIndicator.setTabIcon(R.drawable.tab_icon_contact_normal, R.drawable.tab_icon_contact_focus);
		contactIndicator.setTabTitle("通讯录");
		contactIndicator.setTabUnreadCount(10);
		spec.setIndicator(contactIndicator);
		tabhost.addTab(spec, ContactFragment.class, null);

		spec = tabhost.newTabSpec(TAB_DISCOVER);
		discoverIndicator = new TabIndicatorView(this);
		discoverIndicator.setTabIcon(R.drawable.tab_icon_discover_normal, R.drawable.tab_icon_discover_focus);
		discoverIndicator.setTabTitle("发现");
		discoverIndicator.setTabUnreadCount(10);
		spec.setIndicator(discoverIndicator);
		tabhost.addTab(spec, DiscoverFragment.class, null);

		spec = tabhost.newTabSpec(TAB_ME);
		meIndicator = new TabIndicatorView(this);
		meIndicator.setTabIcon(R.drawable.tab_icon_me_normal, R.drawable.tab_icon_me_focus);
		meIndicator.setTabTitle("我");
		meIndicator.setTabUnreadCount(10);
		spec.setIndicator(meIndicator);
		tabhost.addTab(spec, MeFragment.class, null);
		//end_demo******************************************************//
		
		// 去掉分割线(设置为白色)
		tabhost.getTabWidget().setDividerDrawable(android.R.color.white);
		
		// 默认显示第一个
		tabhost.setCurrentTabByTag(TAB_CHAT);
		chatIndicator.setTabSelected(true);
		
		// 选中
		// 监听选中事件
		tabhost.setOnTabChangedListener(this);
	}

	@Override
	public void onTabChanged(String tabId) {
		// 先将所有tab设置为未选中
		chatIndicator.setTabSelected(false);
		contactIndicator.setTabSelected(false);
		discoverIndicator.setTabSelected(false);
		meIndicator.setTabSelected(false);
		
		// 判断点击了哪个tab
		if(TAB_CHAT.equals(tabId)){
			chatIndicator.setTabSelected(true);
		} else if(TAB_CONTACT.equals(tabId)){
			contactIndicator.setTabSelected(true);
		} else if(TAB_DISCOVER.equals(tabId)){
			discoverIndicator.setTabSelected(true);
		} else if(TAB_ME.equals(tabId)){
			meIndicator.setTabSelected(true);
		}
	}

}
