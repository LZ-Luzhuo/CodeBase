package com.example.demo;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.example.getimage.R;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-26 上午12:35:04
 * 
 * 描述:这是关于从相册/相机获取图片的案例,从相册获取图片,并且裁剪图片
 * 
 * 修订历史:
 * 2015.11.4 添加从相机获取图片代码
 * 2015.11.4 添加调用系统剪辑进行剪辑图片代码
 * 
 * =================================================
 **/

public class MainActivity extends Activity {
	private static final int GET_MEDIASTORE_IMAGE = 0x1;
	private static final int GET_CAMERA_IMAGE = 0x2;
	private static final int GET_CROP_IMAGE = 0x3;
	private ImageView imageView;
	private File sdcardTempFile;
	private File outUrl;
	
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
	private void getImageFromMediaStore(File url,int size){
		// 调用系统照片专辑获取图片,并剪裁图片
		Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        intent.putExtra("output", Uri.fromFile(url));
        
        //以下是剪裁图片代码
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", size);// 输出图片大小(分辨率)
		intent.putExtra("outputY", size);
		
        startActivityForResult(intent, GET_MEDIASTORE_IMAGE);
	}
	
	/**
	 * 调用系统相机获取图片,不能直接剪辑,请调用剪辑代码
	 * @param url 希望保存的路径
	 * @param size 大小(分辨率)
	 */
	private void getImageFromCamera(File url,int size){
		// 调用系统照相机获取图片,并剪裁图片
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra("output", Uri.fromFile(url));
		
		startActivityForResult(intent, GET_CAMERA_IMAGE);
	}
	
	/**
	 * 对图片进行剪裁
	 * @param inputUrl 被剪辑的图片地址
	 * @param outputUrl 剪辑后的图片地址
	 * @param size 压缩
	 */
	private void getCROP(File inputUrl,File outputUrl,int size){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(Uri.fromFile(inputUrl), "image/*");
		intent.putExtra("output", Uri.fromFile(outputUrl));
		
		// 以下是剪裁代码
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", size);// 输出图片大小
		intent.putExtra("outputY", size);
		
		startActivityForResult(intent, GET_CROP_IMAGE);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
        case GET_MEDIASTORE_IMAGE:
        	imageView.setImageURI(Uri.fromFile(sdcardTempFile));
        	return;
        	
        case GET_CAMERA_IMAGE:
        	imageView.setImageURI(Uri.fromFile(sdcardTempFile));
        	return;
        	
        case GET_CROP_IMAGE:
        	imageView.setImageURI(Uri.fromFile(outUrl));
        	return;
		}
	}

	public void onClick1(View v){
		sdcardTempFile = new File(Environment.getExternalStorageDirectory(),"IMG_"+System.currentTimeMillis() + ".jpg");
		//剪裁大小(分辨率)
		int size = 150;
		getImageFromMediaStore(sdcardTempFile, size);
	}
	
	public void onClick2(View v){
		sdcardTempFile = new File(Environment.getExternalStorageDirectory(),"IMG_"+System.currentTimeMillis() + ".jpg");
		//剪裁大小(分辨率)
		int size = 150;
		getImageFromCamera(sdcardTempFile, size);
	}
	
	public void onClick3(View v){
		outUrl = new File(Environment.getExternalStorageDirectory(),"IMG_"+System.currentTimeMillis() + ".jpg");
		//剪裁大小(分辨率)
		int size = 150;
		getCROP(sdcardTempFile, outUrl, size);
	}
}
