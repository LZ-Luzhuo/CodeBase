package me.luzhuo.mvp.ui.view;

/**
 * 定义可能有的ui操作
 * @author Luzhuo
 */
public interface ISplashView {
	/**
	 * 显示加载对话框
	 */
	void showLoadingDialog();

	/**
	 * 开启下个界面
	 */
	void startNextActivity();

	/**
	 * 显示错误信息
	 */
	void showNextWorkError();

	/**
	 * 关闭加载对话框
	 */
	void hideLoadingDialog();
}
