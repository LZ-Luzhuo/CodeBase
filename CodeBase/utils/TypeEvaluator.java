package com.example.parallax.utils;

import android.graphics.Rect;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-12-2 下午5:20:43
 * 
 * 描述:类型估值器
 * 
 * 修订历史:
 * 		(copy:android.animation.TypeEvaluator)
 * 
 * =================================================
 **/
public class TypeEvaluator {

	/**
	 * 颜色渐变估值器
	 * @param fraction 0.0f-->1.0f的百分比
	 * @param startValue 开始颜色(如:Color.Black)
	 * @param endValue 结束颜色
	 * @return 估值后的颜色
	 */
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB))));
    }
    
    /**
     * FLoat类型估值器
     * @param fraction 0.0f-->1.0f的百分比
     * @param startValue 开始值
     * @param endValue 结束值
     * @return 评估值
     */
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
    
    /**
     * Int类型估值器
     * @param fraction 0.0f-->1.0f的百分比
     * @param startValue 开始值
     * @param endValue 结束值
     * @return 评估值
     */
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int)(startInt + fraction * (endValue - startInt));
    }
    
    /**
     * 矩形类型估值器
     * @param fraction 0.0f-->1.0f的百分比
     * @param startValue 开始的形状
     * @param endValue 开始的形状
     * @return 估值的形状
     */
    public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
        return new Rect(startValue.left + (int)((endValue.left - startValue.left) * fraction),
                startValue.top + (int)((endValue.top - startValue.top) * fraction),
                startValue.right + (int)((endValue.right - startValue.right) * fraction),
                startValue.bottom + (int)((endValue.bottom - startValue.bottom) * fraction));
    }
}
