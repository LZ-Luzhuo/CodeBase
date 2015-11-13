package com.example.bitmapregiondecoderdemo;

import java.io.InputStream;

import com.example.bitmapregiondecoderdemo.widget.BigImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-13 下午5:16:44
 * 
 * 描述:这是关于加载巨幅图片的案例: <br>
 * 图片加载策略:<br>
 * 1.图片显示:根据显示图片控件的大小对图片进行压缩;<br>
 * 2.图片数量多:使用LruCache等缓存机制; <br>
 * 3.图片巨大且不允许压缩(缩小式压缩):局部加载显示(<em>本案例对图片质量按RGB_565进行压缩</em>)
 * <pre>
 * 		 // 实现原理:
 * 		 // 从assets目录获取文件
 * 		 InputStream inputStream = getAssets().open("a.jpg");
 * 		 // 获取图片的宽,高
 * 		 BitmapFactory.Options options = new BitmapFactory.Options();
 * 		 options.inJustDecodeBounds = true;
 * 		 BitmapFactory.decodeStream(inputStream, null, options);
 * 		 int width = options.outWidth;
 * 		 int height = options.outHeight;
 * 		
 * 		 InputStream inputStream1 = getAssets().open("a.jpg");
 * 		 // 设置显示图片的显示区域
 * 		 BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream1, false);
 * 		 //只支持jpeg/png格式图片
 * 		 BitmapFactory.Options options2 = new BitmapFactory.Options();
 * 		 options2.inPreferredConfig = Bitmap.Config.RGB_565;
 * 		 Bitmap bitmap = bitmapRegionDecoder.decodeRegion(new Rect(width/2-100, height/2-100, width/2+100, height/2+100), options2); //Rect显示区域,改变该值显示不同的区域
 * 		 // 设置图片
 * 		 imageview.setImageBitmap(bitmap);
 * </pre>
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class MainActivity extends Activity {
	// private ImageView imageview;
	private BigImageView bigImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		// imageview = (ImageView) findViewById(R.id.imageview);
		bigImageView = (BigImageView) findViewById(R.id.bigImageView);
	}

	private void initData() {
		try {
			// 设置图片
			InputStream inputStream = getAssets().open("qingmingshanghetu.jpg");
			bigImageView.setInputStream(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
