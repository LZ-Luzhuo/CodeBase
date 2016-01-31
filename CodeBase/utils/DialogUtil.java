package com.example.utils.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.View;

/**
 * =================================================
 * <p/>
 * Author: 卢卓
 * <p/>
 * Version: 1.0
 * <p/>
 * Creation Date: 2016-1-31 下午4:20:07
 * <p/>
 * Description:对话框工具
 * <p/>
 * Revision History:
 * <p/>
 * Copyright:
 * <p/>
 * =================================================
 **/
public class DialogUtil {
	/**
	 * 显示Dialog(不可取消),调用{@link AlertDialog#dismiss()}关闭对话框
	 * <br>提示:View menu_clean = View.inflate(getContext(), R.layout.menu_dialog_clean, null);
	 */
	public static AlertDialog showDialog(Context context,View view) {
		return showDialog(context, view, false);
	}
	
	/**
	 * 显示Dialog
	 * @param cancelable 是否可取消,ture:是;false:否
	 */
	public static AlertDialog showDialog(Context context,View view,boolean cancelable) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setCancelable(cancelable);
		AlertDialog dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		return dialog;
	}
}
