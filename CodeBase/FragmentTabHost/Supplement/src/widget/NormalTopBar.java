package com.example.chat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chat.R;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-18 下午11:07:04
 * 
 * 描述:组合式控件布局:顶部title
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class NormalTopBar extends RelativeLayout {
	private ImageView ivBack;
	private TextView tvTitle;
	private TextView tvAction;

	public NormalTopBar(Context context) {
		this(context, null);
	}

	public NormalTopBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		View.inflate(context, R.layout.bar_normal, this);
		ivBack = (ImageView) findViewById(R.id.bar_back);
		tvTitle = (TextView) findViewById(R.id.bar_title);
		tvAction = (TextView) findViewById(R.id.bar_action);
	}

	/**
	 * 设置返回键是否显示
	 * @param show true:显示/false:隐藏
	 */
	public void setBackVisibility(boolean show) {
		ivBack.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
	}

	public void setOnBackListener(OnClickListener listener) {
		ivBack.setOnClickListener(listener);
	}

	public void setOnActionListener(OnClickListener listener) {
		tvAction.setOnClickListener(listener);
	}

	/**
	 * 设置标题
	 * @param title 标题
	 */
	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	/**
	 * 设置标题(资源id)
	 * @param titleId 资源id
	 */
	public void setTitle(int titleId) {
		tvTitle.setText(titleId);
	}

	/**
	 * 设置右侧活动文字
	 * @param text 活动文字
	 */
	public void setActionText(String text) {
		tvAction.setText(text);
	}

	/**
	 * 设置右侧活动文字(资源id)
	 * @param textId 资源id
	 */
	public void setActionText(int textId) {
		tvAction.setText(textId);
	}

	/**
	 * 设置右侧活动是否显示(默认:隐藏)
	 * @param visibility true:显示/false:隐藏
	 */
	public void setActionTextVisibility(boolean visibility) {
		tvAction.setVisibility(visibility ? View.VISIBLE : View.GONE);
	}

	/**
	 * 获取返回键对象
	 * @return ImageView
	 */
	public ImageView getBackView() {
		return ivBack;
	}

	/**
	 * 获取标题对象
	 * @return TextView
	 */
	public TextView getTitleView() {
		return tvTitle;
	}

	/**
	 * 获取活动对象
	 * @return View
	 */
	public View getActionView() {
		return tvAction;
	}
}
