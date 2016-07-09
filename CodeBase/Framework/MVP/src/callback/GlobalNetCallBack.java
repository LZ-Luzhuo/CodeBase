package me.luzhuo.mvp.callback;

/**
 * =================================================
 * <p/>
 * Author: 卢卓
 * <p/>
 * Version: 1.0
 * <p/>
 * Creation Date: 2016/4/7 11:48
 * <p/>
 * Description: 全局网络回调接口
 * <p/>
 * Revision History:
 * <p/>
 * Copyright:
 * <p/>
 * =================================================
 **/
public interface GlobalNetCallBack {

    /**
     * 网络数据
     * @param data 网络数据
     */
    void netData(String data);

}
