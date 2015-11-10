package com.example.luzhuo.listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.luzhuo.listview.adapter.ImageAdapter;
import com.example.luzhuo.listview.utils.AsyncImageLoderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;


public class MainActivity extends Activity {
    private ListView listView;
    private String[] listImage = Resource.grilImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUtil();
        initView();
        initData();
    }

    private void initUtil() {
        // 放到Application里初始化,这里暂时放到这里初始化
        AsyncImageLoderUtil.init(this);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listview);
    }

    private void initData() {
        ImageAdapter IAdapter = new ImageAdapter(MainActivity.this,listImage);
        listView.setAdapter(IAdapter);

        // 设置滑动监听 // 3.Fling时暂停加载图片
        listView.setOnScrollListener(new PauseOnScrollListener(AsyncImageLoderUtil.getInstance().getImageLoader(),false,true));

/*        // 项目需要下来刷新或者是滑动加载等功能，必须提供滑动事件的回调参数：
        listView.setOnScrollListener(new PauseOnScrollListener(AsyncImageLoderUtil.getInstance().getImageLoader(), false, true, onScrollListener));*/
    }

/*    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                // TOUCH_SCROLL 状态
                    break;

                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                // FLING 状态
                    break;

                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                // IDLE 状态
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        System.out.println("滚动到了最后一个");
                    }
                    break;
            }
        }
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };*/

}
