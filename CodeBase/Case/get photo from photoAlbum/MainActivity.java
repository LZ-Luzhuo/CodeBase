package com.example.caitu;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-26 上午12:35:04
 * 
 * 描述:这是关于从相册获取图片的案例,从相册获取图片,并且裁剪图片
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/

public class MainActivity extends Activity {
	private static final int GET_PHONE = 0x1;
	private ImageView imageView;
	private File sdcardTempFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.image);
	}
	
	/**
	 * 调用系统照片专辑获取图片,并剪裁图片
	 * @param url 希望保存的路径
	 * @param size 大小(分辨率)
	 */
	private void getImage(File url,int size){
		// 调用系统照片专辑获取图片,并剪裁图片
		Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        
        //以下是剪裁图片代码
		intent.putExtra("output", Uri.fromFile(url));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", size);// 输出图片大小(分辨率)
		intent.putExtra("outputY", size);
		
        startActivityForResult(intent, GET_PHONE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
        case GET_PHONE:
        	Log.i("Phone", "获取并剪裁图片成功!");
        	imageView.setImageURI(Uri.fromFile(sdcardTempFile));
		}
	}

	public void onClick(View v){
		sdcardTempFile = new File(Environment.getExternalStorageDirectory(),"IMG_"+System.currentTimeMillis() + ".jpg");
		//剪裁大小(分辨率)
		int size = 150;
		getImage(sdcardTempFile, size);
	}
}
