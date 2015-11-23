package com.example.appdemo.weight;

import com.example.appdemo.R;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-23 下午8:17:40
 * 
 * 描述:自定义控件案例:设置条目
 * <br>使用:1.布局文件添加命名空间;2.使用对应控件;3.设置属性<br>
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class SettingItemView extends RelativeLayout{
	private TextView tv_desc;
	private TextView tv_title;
	private CheckBox cb_status;
	
	public String DESC_ON = "on";
	public String DESC_OFF = "off";
	public String TITLE = "title";
	
	// 命名空间 格式:http://schemas.android.com/apk/res/[包名]
	private String NAMESPACE = "http://schemas.android.com/apk/res/com.example.appdemo";
	
	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获取自定属性
		String title = attrs.getAttributeValue(NAMESPACE, "titles");
		if(!TextUtils.isEmpty(title)) TITLE = title;
		String desc_on = attrs.getAttributeValue(NAMESPACE, "desc_on");
		if(!TextUtils.isEmpty(desc_on)) DESC_ON = desc_on;
		String desc_off = attrs.getAttributeValue(NAMESPACE, "desc_off");
		if(!TextUtils.isEmpty(desc_off)) DESC_OFF = desc_off;
		
		initView(context);
	}
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {
		View.inflate(context, R.layout.settingitemview, this);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
		cb_status = (CheckBox) this.findViewById(R.id.cb_status);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		
		tv_title.setText(TITLE);
	}
	
	/**
	 * 判断当前组合控件是否被选中。
	 */
	public boolean isChecked(){
		return cb_status.isChecked();
	}
	
	/**
	 * 设置组合控件的勾选状态
	 * @param checked
	 */
	public void setChecked(boolean checked){
		if(checked){
			tv_desc.setText(DESC_ON);
		}else{
			tv_desc.setText(DESC_OFF);
		}
		cb_status.setChecked(checked);
	}
}
