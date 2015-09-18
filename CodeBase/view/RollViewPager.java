package com.example.luzhuo.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-4 上午10:23:12
 * 
 * 描述:自动滚动的ViewPager广告条,
 * 	   加载的图片采用RGB_565彩色模式,
 *   需要调用以下方法:
 * 		rollview.setUriList(uriList); //网络图片资源(图片地址) 
 *		rollview.setTitle(title, titles); //设置标题 
 *		rollview.startRoll(); //自动滚动
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class RollViewPager extends ViewPager {
	private Context context;
	private int currentItem;
	private ArrayList<String> uriList;
	private ArrayList<View> dots;
	private TextView title;
	private ArrayList<String> titles;
	private int[] resImageIds;
	private int dot_focus_resId;
	private int dot_normal_resId;
	private OnPagerClickCallback onPagerClickCallback;
	private boolean isShowResImage = false;
	MyOnTouchListener myOnTouchListener;
	ViewPagerTask viewPagerTask;
	private BitmapUtils bitmapUtils;
	private PagerAdapter adapter;

	// 触摸时按下的点
	PointF downP = new PointF();
	// 触摸时当前的点
	PointF curP = new PointF();
	private int abc = 1;
	private float mLastMotionX;
	private float mLastMotionY;

	private long start = 0;
	
	/**
	 * 
	 * @param context 上下文
	 * @param dots 点View的集合,用法参考:setDots(ArrayList<View>)
	 * @param dot_focus_resId  焦点图片(圆点)
	 * @param dot_normal_resId  默认图片(圆点)
	 * @param onPagerClickCallback  点击回调
	 */
	public RollViewPager(Context context, ArrayList<View> dots,
			int dot_focus_resId, int dot_normal_resId,
			OnPagerClickCallback onPagerClickCallback) {
		super(context);
		this.context = context;
		this.dots = dots;
		this.dot_focus_resId = dot_focus_resId;
		this.dot_normal_resId = dot_normal_resId;
		this.onPagerClickCallback = onPagerClickCallback;
		viewPagerTask = new ViewPagerTask();
		bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		myOnTouchListener = new MyOnTouchListener();
	}
	
	/**
	 * 左右滑动本控件处理,上下滑动父控件处理
	 */
	public boolean dispatchTouchEvent(MotionEvent ev) {
		final float x = ev.getX();
		final float y = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true); //请求父类不要拦截Touch事件
			abc = 1;
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			if (abc == 1) {
				// 向下滑动
				if (Math.abs(x - mLastMotionX) < Math.abs(y - mLastMotionY)) {
					abc = 0;
					// 将Touch事件交给父类处理(父类进行向上或向下滚动事件)
					getParent().requestDisallowInterceptTouchEvent(false);
				} else {
					getParent().requestDisallowInterceptTouchEvent(true);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		// 当从本空间移到外部控件时,本控件不再处理,交由父类处理
		case MotionEvent.ACTION_CANCEL: //从本控件移到外部控件时触发
			getParent().requestDisallowInterceptTouchEvent(false);
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 判断点击事件并回调OnPagerClickCallback接口内onPagerClick(int)方法
	 * @author Luzhuo
	 *
	 */
	public class MyOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// 当前触摸的x,y点
			curP.x = event.getX();
			curP.y = event.getY();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				start = System.currentTimeMillis();
				handler.removeCallbacksAndMessages(null);
				// 按下的x,y点
				downP.x = event.getX();
				downP.y = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				handler.removeCallbacks(viewPagerTask);
				break;
			case MotionEvent.ACTION_CANCEL:
				startRoll(); //通知handler自动滚动
				break;
			case MotionEvent.ACTION_UP:
				downP.x = event.getX();
				downP.y = event.getY();
				long duration = System.currentTimeMillis() - start;
				// 点击
				if (duration <= 500 && downP.x == curP.x) {
					// 回调OnPagerClickCallback接口内onPagerClick(int)方法,并告知现在是第几个page
					onPagerClickCallback.onPagerClick(currentItem);
				} else {
				}
				startRoll();
				break;
			}
			return true;
		}
	}
	
	/**
	 * 通过Handler实现自动滚动
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			RollViewPager.this.setCurrentItem(currentItem);
			startRoll();
		}
	};
	
	private boolean hasSetAdapter = false;
	
	/**
	 * 开始滚动
	 */
	public void startRoll() {
		if (!hasSetAdapter) {
			hasSetAdapter = true;
			this.setOnPageChangeListener(new MyOnPageChangeListener());
			adapter = new ViewPagerAdapter();
			this.setAdapter(adapter);
		}
		handler.postDelayed(viewPagerTask, 4000);
	}
	
	public class ViewPagerTask implements Runnable {
		@Override
		public void run() {
			currentItem = (currentItem + 1)
					% (isShowResImage ? resImageIds.length : uriList.size());
			handler.obtainMessage().sendToTarget();
		}
	}
	
	/**
	 * page改变监听
	 * @author Luzhuo
	 *
	 */
	class MyOnPageChangeListener implements OnPageChangeListener {
		int oldPosition = 0;

		@Override
		public void onPageSelected(int position) {
			currentItem = position;
			if (title != null)
				title.setText(titles.get(position));
			if (dots != null && dots.size() > 0) {
				dots.get(position).setBackgroundResource(dot_focus_resId);
				dots.get(oldPosition).setBackgroundResource(dot_normal_resId);
			}
			oldPosition = position;
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
	}
	
	class ViewPagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return isShowResImage ? resImageIds.length : uriList.size();
		}

		@Override
		public Object instantiateItem(View container, final int position) {
			View view = View.inflate(context, R.layout.viewpager_item, null);
			((ViewPager) container).addView(view);
			view.setOnTouchListener(myOnTouchListener);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			if (isShowResImage) {
				imageView.setImageResource(resImageIds[position]);
			} else {
				bitmapUtils.display(imageView, uriList.get(position));
			}
			return view;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}
	
	/**
	 * 图片被点击时回调,position第几张图片被点击
	 * @author Luzhuo
	 *
	 */
	public interface OnPagerClickCallback {
		public abstract void onPagerClick(int position);
	}

	/**
	 * 最后的清理工作:清空handler的Callbacks和Messages
	 */
	@Override
	protected void onDetachedFromWindow() {
		handler.removeCallbacksAndMessages(null);
		super.onDetachedFromWindow();
	}


	/**
	 * 网络图片资源(图片地址),如果设置本地图片参考:setResImageIds(int[] resImageIds);
	 * @param uriList
	 */
	public void setUriList(ArrayList<String> uriList) {
		isShowResImage = false;
		this.uriList = uriList;
	}

	/**
	 * 通知adapter数据改变
	 */
	public void notifyDataChange() {
		adapter.notifyDataSetChanged();
	}

	/**
	 * 获取点的集合
	 * @return
	 */
	public ArrayList<View> getDots() {
		return dots;
	}

	/**
	 * 设置点View的集合
	 * 例子:		LinearLayout liear = (LinearLayout) findViewById(R.id.dots_ll); //应定义LinearLayout存放点
	 *			liear.removeAllViews();
	 *			
	 * 			size = 10; //假设有10张图片,添加10个点
	 * 			dots = new ArrayList<View>();
	 *			for (int i = 0; i < size; i++) {
	 *			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(int widthpx, int heightpx); //设置点View的大小
	 *			params.setMargins(5, 0, 5, 0); //左右各5点
	 *			View m = new View(context);
	 *			if (i == 0){
	 *				m.setBackgroundResource(R.drawable.dot_focus);
	 *			}else{
	 *				m.setBackgroundResource(R.drawable.dot_normal);
	 *			}
	 *			m.setLayoutParams(params);
	 *			dots.add(m);
	 *			setDots(dots);
	 *
	 *			liear.addView(m);
	 * @param dots
	 */
	public void setDots(ArrayList<View> dots) {
		this.dots = dots;
	}

	/**
	 * 本地图片资源,如果进行设置.将加载本地图片,如果不设置将从网络获取图片
	 * @param resImageIds
	 */
	public void setResImageIds(int[] resImageIds) {
		isShowResImage = true;
		this.resImageIds = resImageIds;
	}

	/**
	 * 设置标题
	 * @param title TextView显示控件
	 * @param titles 新闻标题集合
	 */
	public void setTitle(TextView title, ArrayList<String> titles) {
		this.title = title;
		this.titles = titles;
		if (title != null && titles != null && titles.size() > 0)
			title.setText(titles.get(0));
	}

}
