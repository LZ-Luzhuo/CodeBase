package com.luzhuo.demo02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        ((ImageView) findViewById(R.id.circle_iv)).setImageResource(R.mipmap.image0);
        ((ImageView) findViewById(R.id.roundedrectangle_iv)).setImageResource(R.mipmap.image0);
        ((ImageView) findViewById(R.id.raw_iv)).setImageResource(R.mipmap.image0);
    }
}
