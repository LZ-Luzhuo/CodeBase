package com.example.lotterydemo;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public interface ConstantValue {
	String ENCONDING="UTF-8";
	String AGENTERID="889931"; //����id
	String SOURCE = "ivr"; //��Ϣ��Դ
	String COMPRESS = "DES"; //body��ݼ����㷨
	//�Ӵ����̵���Կ(.so) JNI
	String AGENTER_PASSWORD = "9ab62a694d8bf6ced1fab6acd48d02f8";
	String DES_PASSWORD = "9b2648fcdfbad80f"; //des��������Կ
	/**
	 * ��������ַ
	 */
	String LOTTERY_URI = "http://192.168.0.100:80/ZCWService/Entrance";// 10.0.2.2ģ���������Ҫ��PC��ͨ��127.0.0.1
//	String LOTTERY_URI = "http://192.168.0.100:80/Entrance";
	
	int VIEW_FIRST=1;
	int VIEW_SECOND=2;
	
	/**
	 * 购彩大厅
	 */
	int VIEW_HALL=10;
	/**
	 * 双色球选号界面
	 */
	int VIEW_SSQ=15;
	/**
	 * 购物车
	 */
	int VIEW_SHOPPING=20;
	/**
	 * 追期和倍投的设置界面
	 */
	int VIEW_PREBET=25;
	/**
	 * 用户登录界面
	 */
	int VIEW_LOGIN=30;
	/**
	 * 双色球标识
	 */
	int SSQ=118;
	/**
	 * 服务器返回成功状态码
	 */
	String SUCCESS="0";
	/**
	 * 			XmlPullParser parser = Xml.newPullParser();
			try {
				parser.setInput(is, ConstantValue.ENCONDING);
				int eventType = parser.getEventType();
				String name;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if("".equals(name)){
							
						}
						break;
					}
					eventType = parser.next();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	 * 
	 */
	
	
}
