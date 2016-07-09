package me.luzhuo.mvp.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import me.luzhuo.mvp.R;
import me.luzhuo.mvp.presenter.SplashPersenter;
import me.luzhuo.mvp.ui.view.ISplashView;

/**
 * MVP架构
 * @author Luzhuo
 */
public class SplashActivity extends Activity implements ISplashView {
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
		System.out.println("showLoadingDialog:1");
	}

	@Override
	public void startNextActivity() {
		System.out.println("startNextActivity:2:1");
	}

	@Override
	public void showNextWorkError() {
		System.out.println("showNextWorkError:2:2");
	}

	@Override
	public void hideLoadingDialog() {
		System.out.println("hideLoadingDialog:3");
	}

}
