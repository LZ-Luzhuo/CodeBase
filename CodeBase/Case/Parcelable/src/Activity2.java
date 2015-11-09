package com.example.luzhuo.parcelabledemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * =================================================
 * <p/>
 * 作者:卢卓
 * <p/>
 * 版本:1.0
 * <p/>
 * 创建日期:2015/11/9 21:48
 * <p/>
 * 描述:
 * <p/>
 * 修订历史:
 * <p/>
 * <p/>
 * =================================================
 **/
public class Activity2 extends Activity{
    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        initView();
    }

    private void initView() {
        textview = (TextView) findViewById(R.id.text);
        Intent intent = getIntent();
        Bean bean = intent.getParcelableExtra("bean");
        textview.setText(bean.Age+":"+bean.Sexy);
    }
}
