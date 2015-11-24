package com.example.appdemo.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-24 下午3:52:11
 * 
 * 描述:电话相关的工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class PhoneUtil {
	
	/**
	 * 发送短信
	 * <br>权限"android.permission.SEND_SMS"
	 * @param number 短信号码
	 * @param text 短信内容
	 */
	public static void sendMessage(String number, String text){
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(number, null, text, null, null);
	}

	/**
	 * 获取手机Sim卡序列号(单卡)
	 * <br>权限:"android.permission.READ_PHONE_STATE"
	 * @param context
	 * @return String/null
	 */
	public static String getSimSerialNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSimSerialNumber();
	}
	
	/**
	 * 获取手机联系人信息
	 * <br>权限:"android.permission.READ_CONTACTS"
	 * @param context
	 * @return
	 */
	public static List<Contact> getContactInfo(Context context) {
		List<Contact> list = new ArrayList<Contact>();
		
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri uriData = Uri.parse("content://com.android.contacts/data");
		
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(uri, new String[] {"contact_id"}, null, null, null);

		while (cursor.moveToNext()) {
			String contact_id = cursor.getString(0);
			if (contact_id != null) {
				Contact contact = new Contact();

				Cursor dataCursor = resolver.query(uriData, new String[] {"data1", "mimetype"}, "contact_id=?", new String[] {contact_id}, null);
				while (dataCursor.moveToNext()) {
					String data1 = dataCursor.getString(0);
					String mimetype = dataCursor.getString(1);
					if ("vnd.android.cursor.item/name".equals(mimetype)) {
						contact.name = data1;
					} else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
						contact.phone = data1;
					}
				}
				list.add(contact);
				dataCursor.close();
			}
		}
		cursor.close();
		return list;
	}
	
	public static class Contact{
		public String name; // 姓名
		public String phone; // 电话
	}
}
