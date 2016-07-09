package me.luzhuo.mvp.presenter;

import android.text.TextUtils;

import me.luzhuo.mvp.callback.GlobalNetCallBack;
import me.luzhuo.mvp.callback.impl.BaseGlobalNetCallBack;
import me.luzhuo.mvp.model.INetWork;
import me.luzhuo.mvp.model.impl.INetWorkImpl;
import me.luzhuo.mvp.ui.view.ISplashView;

/**
 * =================================================
 * <p>
 * Author: Luzhuo
 * <p>
 * Version: 1.0
 * <p>
 * Creation Date: 2016/7/8 16:46
 * <p>
 * Description: 控制层, 处理model和ui之间的逻辑
 * <p>
 * Revision History:
 * <p>
 * Copyright: Copyright 2016 Luzhuo. All rights reserved.
 * <p>
 * =================================================
 **/
public class SplashPersenter {
	private INetWork mINetWork;
	private ISplashView mISplashView;
	
	public SplashPersenter(ISplashView iSplashView){
		super();
		mINetWork = new INetWorkImpl();
		mISplashView = iSplashView;
	}
	
	public void doUILogic(){
		mISplashView.showLoadingDialog();
		mINetWork.getNetData(callback);
	}

	GlobalNetCallBack callback = new BaseGlobalNetCallBack(){
		@Override
		public void netData(String data) {
			if(!TextUtils.isEmpty(data)) mISplashView.startNextActivity();
			else mISplashView.showNextWorkError();

			mISplashView.hideLoadingDialog();
		}
	};
}
