package com.example.mvpdemo;

import com.example.mvpdemo.presenter.SplashPersenter;
import com.example.mvpdemo.view.ISplashView;

import android.app.Activity;
import android.os.Bundle;
/**
 * MVP架构
 * @author Luzhuo
 */
public class SplashActivity extends Activity implements ISplashView{
	SplashPersenter mSplashPersenter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSplashPersenter = new SplashPersenter(this);
	}
	
	@Override
	protected void onResume() {
		mSplashPersenter.doUILogic();
		super.onResume();
	}
	
	/**
	 * UI具体展示
	 */
	@Override
	public void showLoadingDialog() {
		System.out.println("showLoadingDialog");
	}

	@Override
	public void startNextActivity() {
		System.out.println("startNextActivity");
	}

	@Override
	public void showNextWorkError() {
		System.out.println("showNextWorkError");
	}

	@Override
	public void hideLoadingDialog() {
		System.out.println("hideLoadingDialog");
	}

}
