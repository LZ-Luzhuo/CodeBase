package com.example.mvpdemo.presenter;

import com.example.mvpdemo.model.INetWork;
import com.example.mvpdemo.model.impl.INetWorkImpl;
import com.example.mvpdemo.view.ISplashView;

/**
 * 处理ui显示逻辑
 * @author Luzhuo
 */
public class SplashPersenter {
	INetWork mINetWork;
	ISplashView mISplashView;
	
	public SplashPersenter(ISplashView iSplashView){
		super();
		mINetWork = new INetWorkImpl();
		mISplashView = iSplashView;
	}
	
	public void doUILogic(){
		mISplashView.showLoadingDialog();
		if(mINetWork.isNetWorkOk()){
			mISplashView.startNextActivity();
		}else{
			mISplashView.showNextWorkError();
		}
		mISplashView.hideLoadingDialog();
	}
}
