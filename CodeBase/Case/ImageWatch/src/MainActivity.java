package com.luzhuo.imagewatchdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.luzhuo.imagewatchdemo.imagewatch.ImagePagerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, TestDatas.getDatas()); // 图片地址集合 ArrayList<String> imageUrls
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 5); // 展示第几个 0开始
        startActivity(intent);
    }
}
