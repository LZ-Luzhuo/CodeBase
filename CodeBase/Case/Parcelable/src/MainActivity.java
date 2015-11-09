package com.example.luzhuo.parcelabledemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * =================================================
 * <p/>
 * 作者:卢卓
 * <p/>
 * 版本:1.0
 * <p/>
 * 创建日期:2015/11/9 21:39
 * <p/>
 * 描述:这是Parcelable的案例,Parcelable是存在内存中的序列化,用于传递数据.
 * <p/>
 * 修订历史:
 * <p/>
 * <p/>
 * =================================================
 *  **/
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sender(View view){
        Intent intent = new Intent(MainActivity.this,Activity2.class);
        Bean bean = new Bean();
        bean.Age = 10;
        bean.Sexy = true;
        intent.putExtra("bean",bean);
        startActivity(intent);
    }
}
