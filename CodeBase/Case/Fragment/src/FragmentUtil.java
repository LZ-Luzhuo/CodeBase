package com.example.fragmentdemo2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-12 下午1:59:06
 * 
 * 描述:这是fragment管理工具.<br>Fragment适合处理业务操作简单,数据传输安全级别要求不高的场景.
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class FragmentUtil {
	private static FragmentActivity activity;
	private static FragmentUtil instance = new FragmentUtil();
	
	private FragmentUtil(){ }
	
	/**
	 * 初始化工具,请在使用前在FragmentActivity里初始化
	 * @param fragmentactivity
	 */
	public static void initFragmentUtil(FragmentActivity fragmentActivity){
		activity = fragmentActivity;
	}
	
	/**
	 * 获取Fragment管理工具实例
	 * @return
	 */
	public static FragmentUtil getInstance(){
		return instance;
	}
	
	public void changeFragment(int containerId,Fragment target){
		changeFragment(containerId,target, false, null);
	}
	
	public void changeFragment(int containerId,Fragment target, boolean isAddStack){
		changeFragment(containerId,target, isAddStack, null);
	}
	
	public void changeFragment(int containerId,Fragment target, Bundle bundle){
		changeFragment(containerId,target, false, bundle);
	}
	
	/**
	 * 切换Fragment
	 * @param containerId 放Fragment的FrameLayout容器id
	 * @param target 要切换的Fragment
	 * @param isAddStack 是否添加到栈里面,如果添加到栈里面可以使用返回键;不建议将第一个Fragment添加到栈里
	 * @param bundle / null;存储要传输的数据,getBundle(Fragment)获取数据
	 */
	public void changeFragment(int containerId,Fragment target, boolean isAddStack, Bundle bundle){
		if(bundle != null){
			// 存储要传输的数据
			target.setArguments(bundle);
		}
		if(activity == null){
			new RuntimeException("使用前请先调用 initFragmentUtil(FragmentActivity) 进行初始化.");
		}
		
		FragmentTransaction replace = activity.getSupportFragmentManager().beginTransaction().replace(containerId, target);
		if(isAddStack) //是否添加到栈里面,如果添加到栈里面可以使用返回键
			replace = replace.addToBackStack(null);
		replace.commit();
	}
	
	/**
	 * 获取Bundle
	 * @param fragment 与存入Bundle相对应的Fragment
	 * @return Bundle
	 */
	public Bundle getBundle(Fragment fragment){
		return fragment.getArguments();
	}
	
	public void addFragment(int containerId,Fragment target){
		addFragment(containerId,target, false, null);
	}
	
	public void addFragment(int containerId,Fragment target, boolean isAddStack){
		addFragment(containerId,target, isAddStack, null);
	}
	
	public void addFragment(int containerId,Fragment target, Bundle bundle){
		addFragment(containerId,target, false, bundle);
	}
	
	/**
	 * 添加Fragment到FrameLayout容器中,注意:多次调用,Fragment会叠加
	 * @param containerId 放Fragment的FrameLayout容器id
	 * @param target 要添加的Fragment
	 * @param isAddStack 是否添加到栈里面,如果添加到栈里面可以使用返回键;不建议将第一个Fragment添加到栈里
	 * @param bundle / null;存储要传输的数据,getBundle(Fragment)获取数据
	 */
	public void addFragment(int containerId,Fragment target, boolean isAddStack, Bundle bundle){
		if(bundle != null){
			// 存储要传输的数据
			target.setArguments(bundle);
		}
		if(activity == null){
			new RuntimeException("使用前请先调用 initFragmentUtil(FragmentActivity) 进行初始化.");
		}
		
		FragmentTransaction add = activity.getSupportFragmentManager().beginTransaction().add(containerId, target);
		if(isAddStack) //是否添加到栈里面,如果添加到栈里面可以使用返回键
			add = add.addToBackStack(null);
		add.commit();
	}
}
