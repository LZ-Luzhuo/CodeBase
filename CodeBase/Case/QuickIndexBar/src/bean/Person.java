package com.example.quickindexbar.bean;

import com.example.quickindexbar.utils.PinyinUtils;

public class Person implements Comparable<Person>{

	public String name;
	public String pinyin;

	public Person(String name) {
		super();
		this.name = name;
		this.pinyin = PinyinUtils.getPinyin(name);
	}

	@Override
	public int compareTo(Person another) { //排序
		return this.pinyin.compareTo(another.pinyin);
	}
}
