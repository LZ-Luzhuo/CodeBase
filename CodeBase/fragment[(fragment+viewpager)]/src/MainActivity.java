package com.example.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

import com.example.news.fragment.Fragment1;
import com.example.news.fragment.HomeFragment;
import com.example.news.fragment.MenuFragment;
import com.example.news.fragment.RightMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	private SlidingMenu sm;
	private MenuFragment menuFragment;

	/**
	 * 1.得到滑动菜单 2.设置滑动菜单在左边出来还是右边出来 3.设置滑动菜单出来之后,内容页,显示的剩余宽度
	 * 4.设置滑动菜单的阴影,设置阴影,阴影需要在开始的时候,特别暗,慢慢的变淡 5.设置阴影的宽度 6.设置活动菜单touch的范围
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
		setBehindContentView(R.layout.menu); // 设置菜单页
		setContentView(R.layout.content);

		sm = getSlidingMenu();
		// 2.设置滑动菜单在左边出来还是右边出来
		sm.setMode(SlidingMenu.LEFT); // 左边
		// sm.setMode(SlidingMenu.LEFT_RIGHT);
		// 3.设置滑动菜单出来之后,内容页,显示的剩余宽度
		sm.setBehindWidthRes(R.dimen.slidingmenu_offset);
		// 4.设置滑动菜单的阴影,设置阴影,阴影需要在开始的时候,特别暗,慢慢的变淡
		sm.setShadowDrawable(R.drawable.shadow);
		// 5.设置阴影的宽度
		sm.setShadowWidth(R.dimen.shadow_width);
		// 6.设置活动菜单touch的范围
		// 参数:TOUCHMODE_FULLSCREEN 全屏;TOUCHMODE_MARGIN 边缘;TOUCHMODE_NONE 不滑动
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 创建fragment
		menuFragment = new MenuFragment();
		getSupportFragmentManager() // 获取fragment的管理者
				.beginTransaction() // 开启事务
				.replace(R.id.menu_frame, menuFragment, "Menu") // 替换
				.commit(); // 提交

		HomeFragment homeFragment = new HomeFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, homeFragment, "Home").commit();

		// 右边出来
		// sm.setSecondaryMenu(R.layout.right_menu);
		// sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		// RightMenuFragment rightMenuFragment = new RightMenuFragment();
		// getSupportFragmentManager().beginTransaction().replace(R.id.right_menu_frame,
		// rightMenuFragment).commit();
	}

	/**
	 * 获取菜单
	 */
	public MenuFragment getMenuFragment(){
		menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag("Menu"); //为了保证代码的健壮,所以不能直接返回new出来的对象
		return menuFragment;
	}

	/**
	 * 方法D 回调
	 * 
	 * @param f
	 */
	public void switchFragment(Fragment f) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, f).commit();
		sm.toggle(); // 自动切换
	}

}
