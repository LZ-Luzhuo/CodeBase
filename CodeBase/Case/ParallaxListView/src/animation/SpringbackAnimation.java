package com.example.parallax.animation;

import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-2 下午5:18:42
 * 
 * 描述:回弹动画
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class SpringbackAnimation extends Animation {
	private ImageView image;
	private int startHeight;
	private int endHeight;

	public SpringbackAnimation(ImageView image, int startHeight, int endHeight) {
		this.image = image;
		this.startHeight = startHeight;
		this.endHeight = endHeight;
		setInterpolator(new OvershootInterpolator());
		setDuration(500);
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		Integer evaluate = evaluate(interpolatedTime, startHeight, endHeight);
		image.getLayoutParams().height = evaluate;
		image.requestLayout();
		super.applyTransformation(interpolatedTime, t);
	}
	
    private Integer evaluate(float fraction, Integer startValue, Integer endValue) {
    	// int估值器(TypeEvaluator)
        int startInt = startValue;
        return (int)(startInt + fraction * (endValue - startInt));
    }

}
