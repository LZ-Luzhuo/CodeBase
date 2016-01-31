package com.example.chart.bean;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2016-1-7 下午2:34:00
 * 
 * 描述:各分类下的数据总数
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class DataCount {
	public String classify;
	public long count;
	
	public DataCount() {
		super();
	}
	
	public DataCount(String classify, long count) {
		super();
		this.classify = classify;
		this.count = count;
	}
}
