package com.example.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-18 下午4:51:21
 * 
 * 描述:组合式控件布局
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class TabIndicatorView extends RelativeLayout{
	private ImageView ivTabIcon;
	private TextView tvTabHint;
	private TextView tvTabUnRead;
	
	private int normalIconId;
	private int focusIconId;

	public TabIndicatorView(Context context) {
		this(context, null);
	}

	public TabIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 将布局文件和我们的代码进行绑定
		View.inflate(context, R.layout.tab_indicator, this);
		
		ivTabIcon = (ImageView) findViewById(R.id.tab_indicator_icon);
		tvTabHint = (TextView) findViewById(R.id.tab_indicator_hint);
		tvTabUnRead = (TextView) findViewById(R.id.tab_indicator_unread);
		
		setTabUnreadCount(0);
	}
	
	/**
	 * 设置tab标题
	 * @param title tab标题
	 */
	public void setTabTitle(String title){
		tvTabHint.setText(title);
	}
	
	/**
	 * 设置tab标题
	 * @param titleId 资源文件id
	 */
	public void setTabTitle(int titleId){
		tvTabHint.setText(titleId);
	}
	
	/**
	 * 设置图标
	 * @param normalIconId 普通图标
	 * @param focusIconId 选中图标
	 */
	public void setTabIcon(int normalIconId, int focusIconId){
		this.normalIconId = normalIconId;
		this.focusIconId = focusIconId;
		
		// 默认选中normalIconId
		ivTabIcon.setImageResource(normalIconId);
	}
	
	/**
	 * 设置未读信息数量
	 * @param unreadCount 未读信息数
	 */
	public void setTabUnreadCount(int unreadCount){
		if(unreadCount <= 0){
			tvTabUnRead.setVisibility(View.GONE);
		}else{
			if(unreadCount <= 99){
				tvTabUnRead.setText(unreadCount+"");
			}else{
				tvTabUnRead.setText(unreadCount+"99+");
			}
			tvTabUnRead.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 设置选中
	 * @param selected true:选中;false:不选中
	 */
	public void setTabSelected(boolean selected){
		if(selected){
			ivTabIcon.setImageResource(focusIconId);
		}else{
			ivTabIcon.setImageResource(normalIconId);
		}
	}

}
