package com.example.appdemo.demo;

import com.example.appdemo.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-23 下午3:22:29
 * 
 * 描述:Dialog对话框使用案例
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class Dialog {
	/**
	 * 系统的对话框
	 */
	public void showDialog(Context context) {
		AlertDialog.Builder builder = new Builder(context);
		//builder.setCancelable(false); // 强制显示,必须点击选择
		builder.setTitle("标题"); //标题
		builder.setMessage("内容"); // 内容
		
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO 监听取消(返回/点击控件外 取消)
			}
		});
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 点击确认
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 点击取消
			}
		});
		builder.show(); // 显示
	}
	
	
	private AlertDialog dialog;
	/**
	 * 自定义的对话框
	 */
	public void showDialogDiy(Context context) {
		// 以下是自定义布局的设置
		View view = View.inflate(context, R.layout.dialog_diy, null);
		Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		bt_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 取消
				dialog.dismiss();
			}
		});
		bt_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 确定
				dialog.dismiss();
			}
		});
		
		AlertDialog.Builder builder = new Builder(context);
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO 监听取消(返回/点击控件外 取消)
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}
}

