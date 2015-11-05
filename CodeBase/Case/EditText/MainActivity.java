package com.example.edittext;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-5 下午2:28:55
 * 
 * 描述:文本输入框案例:该案例自定义了输入框背景;输入文字显示delete按钮.
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MainActivity extends Activity implements OnClickListener, TextWatcher {
	private EditText edittext;
	private Button btn_clear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edittext = (EditText) findViewById(R.id.edittext);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		
		initView();
	}

	private void initView() {
		btn_clear.setVisibility(View.GONE);
		btn_clear.setOnClickListener(this);
		edittext.addTextChangedListener(this);
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String content = edittext.getText().toString().trim();
		if(TextUtils.isEmpty(content)){
			btn_clear.setVisibility(View.GONE);
			return;
		}
		btn_clear.setVisibility(View.VISIBLE);
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void onClick(View v) {
		if(v == btn_clear){
			edittext.setText("");
		}
	}
}
