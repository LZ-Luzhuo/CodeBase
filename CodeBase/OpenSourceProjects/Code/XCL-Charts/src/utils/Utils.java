package com.example.chart.utils;

import java.util.ArrayList;

import com.example.chart.bean.DataCount;

public class Utils {
	public static ArrayList<DataCount> getDataCount() {
		ArrayList<DataCount> dataCount = new ArrayList<DataCount>();
		dataCount.add(new DataCount("分类一", 10));
		dataCount.add(new DataCount("分类二", 20));
		dataCount.add(new DataCount("分类三", 30));
		dataCount.add(new DataCount("分类四", 2));
		dataCount.add(new DataCount("分类五", 38));
		return dataCount;
	}
}
