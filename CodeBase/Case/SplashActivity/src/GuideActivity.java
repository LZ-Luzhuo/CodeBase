package com.example.splashactivity;

import java.util.ArrayList;

import com.example.splashactivity.utils.DisplayMatchUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * =================================================
 * <p/>
 * Author: 卢卓
 * <p/>
 * Version: 1.0
 * <p/>
 * Creation Date: 2016-2-24 下午6:47:48
 * <p/>
 * Description:新手引导界面
 * <p/>
 * Revision History:
 * <p/>
 * Copyright:
 * <p/>
 * =================================================
 **/
public class GuideActivity extends Activity {

	private static final int[] mImageIds = new int[] {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
	private ViewPager vpGuide;
	private ArrayList<ImageView> mImageViewList;

	private LinearLayout llPointGroup;// 引导圆点的父控件
	private View viewRedPoint;// 小红点
	private Button btnStart;// 开始体验
	
	private int mPointWidth;// 圆点间的距离

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
		setContentView(R.layout.activity_guide);
		
		initView();
		initData();
	}

	private void initView() {
		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
		viewRedPoint = findViewById(R.id.view_red_point);
		btnStart = (Button) findViewById(R.id.btn_start);
	}
	
	
	@SuppressWarnings("deprecation")
	private void initData() {
		btnStart.setOnClickListener(onclick);

		initGuide();
		vpGuide.setAdapter(new GuideAdapter());
		vpGuide.setOnPageChangeListener(new GuidePageListener());
	}
	
	OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO 更新sp, 表示已经展示了新手引导
			SharedPreferences sp = getSharedPreferences("config",Context.MODE_PRIVATE);
			sp.edit().putBoolean("is_user_guide_showed", true).commit();

			// TODO 跳转主页面
			startActivity(new Intent(GuideActivity.this, MainActivity.class));
			finish();
		}
	};

	/**
	 * 初始化指导界面
	 */
	private void initGuide() {
		mImageViewList = new ArrayList<ImageView>();

		// 初始化引导页的3个页面
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(mImageIds[i]);// 设置引导页背景
			mImageViewList.add(image);
		}

		// 初始化引导页的小圆点
		for (int i = 0; i < mImageIds.length; i++) {
			View point = new View(this);
			point.setBackgroundResource(R.drawable.shape_point_gray);// 设置引导页默认圆点

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayMatchUtils.dipTopx(this, 10), DisplayMatchUtils.dipTopx(this, 10)); // 黑圆点大小
			if (i > 0) {
				params.leftMargin = DisplayMatchUtils.dipTopx(this, 10);// 设置黑圆点间隔
			}

			point.setLayoutParams(params);// 设置圆点的大小

			llPointGroup.addView(point);// 将圆点添加给线性布局
		}

		// 获取视图树, 对layout结束事件进行监听
		llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			// 当layout执行结束后回调此方法
			@Override
			public void onGlobalLayout() {
				llPointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// 获取圆点间的距离
				mPointWidth = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
			}
		});
	}

	/**
	 * ViewPager数据适配器
	 * 
	 * @author Kevin
	 * 
	 */
	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageIds.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mImageViewList.get(position));
			return mImageViewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	/**
	 * viewpager的滑动监听
	 * @author Luzhuo
	 */
	class GuidePageListener implements OnPageChangeListener {

		// 滑动事件
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// 当前位置:position; 百分比:positionOffset;移动距离(像素):positionOffsetPixels
			// 红色圆点的移动距离,红色圆点之间的距离*百分比+位置的距离
			int len = (int) (mPointWidth * positionOffset) + position * mPointWidth;
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewRedPoint .getLayoutParams();// 获取当前红点的布局参数
			params.leftMargin = len;// 设置左边距

			viewRedPoint.setLayoutParams(params);// 重新给小红点设置布局参数
		}

		// 某个页面被选中
		@Override
		public void onPageSelected(int position) {
			if (position == mImageIds.length - 1) {// 最后一个页面
				btnStart.setVisibility(View.VISIBLE);// 显示开始体验的按钮
			} else {
				btnStart.setVisibility(View.INVISIBLE);
			}
		}

		// 滑动状态发生变化
		@Override
		public void onPageScrollStateChanged(int state) {

		}
	}
}
