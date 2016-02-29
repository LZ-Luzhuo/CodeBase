package com.example.mvpdemo.model.impl;

import com.example.mvpdemo.model.INetWork;

public class INetWorkImpl implements INetWork {

	@Override
	public boolean isNetWorkOk() {
		// TODO 判断网路是否可用
		return true;
	}

}
