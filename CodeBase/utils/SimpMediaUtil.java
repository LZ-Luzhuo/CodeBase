package com.example.appdemo.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

public class SimpMediaUtil {

	/**
	 * 播放声音
	 * @param context
	 * @param r 资源文件 如:R.raw.aaa
	 * @param isLoop true/false 循环播放
	 * @return MediaPlayer 可以调用stop暂停
	 */
	public static MediaPlayer playerMP3(Context context,int r,boolean isLoop){
		MediaPlayer mediaplayer = MediaPlayer.create(context, r);
		mediaplayer.setVolume(1.0f, 1.0f);
		mediaplayer.setLooping(isLoop);
		mediaplayer.start();
		return mediaplayer;
	}
	
	/**
	 * 振动
	 * <br>权限:"android.permission.VIBRATE"
	 * @param s 振动时长(秒)
	 */
	public static void vibrator(Context context,double s){
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate((long)(s*1000));
		//vibrator.vibrate(new long[]{100,200,100,300,50,200}, 1);//有规律的振动
	}
}
