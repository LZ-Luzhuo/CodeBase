package com.luzhuo.imagewatchdemo;

import java.util.ArrayList;

/**
 * =================================================
 * <p/>
 * Author: 卢卓
 * <p/>
 * Version: 1.0
 * <p/>
 * Creation Date: 2016/4/20 14:09
 * <p/>
 * Description:
 * <p/>
 * Revision History:
 * <p/>
 * Copyright:
 * <p/>
 * =================================================
 **/
public class TestDatas {
    public static ArrayList<String> getDatas(){
        ArrayList<String> datas = new ArrayList<>();
        for(int x = 0; x < 100; x++){
            datas.add("http://pic27.nipic.com/20130125/7264483_172143500196_2.jpg");
        }
        return datas;
    }
}
