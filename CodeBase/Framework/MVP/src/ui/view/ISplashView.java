package com.example.mvpdemo.view;

/**
 * 定义可能有的ui操作
 * @author Luzhuo
 */
public interface ISplashView {
	void showLoadingDialog();
	void startNextActivity();
	void showNextWorkError();
	void hideLoadingDialog();
}
