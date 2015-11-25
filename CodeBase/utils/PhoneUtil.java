package com.example.appdemo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Xml;

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
	
	public interface BackUpCallBack {
		/**
		 * @param max 短信条数
		 */
		public void beforeBackup(int max);
		/**
		 * @param progress 已备份进度
		 */
		public void onSmsBackup(int progress);
	}
	
	/**
	 * 备份短信(请在辅助线程中运行)
	 * 权限:"android.permission.WRITE_SMS",
	 * "android.permission.READ_SMS"
	 * @param file 文件保存目录(不含文件名,如:Environment.getExternalStorageDirectory())
	 * @param callBack 备份进度回调
	 */
	public static void backupSms(Context context,File file, BackUpCallBack callBack) throws Exception {
		ContentResolver resolver = context.getContentResolver();
		File files = new File(file, "backupSms.xml");
		FileOutputStream fos = new FileOutputStream(files);
		
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(fos, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "smss");
		
		Uri uri = Uri.parse("content://sms/");
		Cursor cursor = resolver.query(uri, new String[] { "body", "address", "type", "date" }, null, null, null);
		int max = cursor.getCount();
		callBack.beforeBackup(max);
		serializer.attribute(null, "max", max + "");
		int process = 0;
		while (cursor.moveToNext()) {
			String body = cursor.getString(0);
			String address = cursor.getString(1);
			String type = cursor.getString(2);
			String date = cursor.getString(3);
			serializer.startTag(null, "sms");

			serializer.startTag(null, "body");
			serializer.text(body);
			serializer.endTag(null, "body");

			serializer.startTag(null, "address");
			serializer.text(address);
			serializer.endTag(null, "address");

			serializer.startTag(null, "type");
			serializer.text(type);
			serializer.endTag(null, "type");

			serializer.startTag(null, "date");
			serializer.text(date);
			serializer.endTag(null, "date");

			serializer.endTag(null, "sms");
			process++;
			callBack.onSmsBackup(process);
		}
		cursor.close();
		serializer.endTag(null, "smss");
		serializer.endDocument();
		fos.close();
	}
	
	/**
	 * 还原短信(请在辅助线程中运行)
	 * @param flag 是否删除原来的短信
	 * @param file 短信备份文件的目录(不含文件名,如:Environment.getExternalStorageDirectory())
	 * @param callBack 是否删除原来的短信
	 * @throws Exception 
	 */
	public static void restoreSms(Context context, boolean flag,File file, BackUpCallBack callBack) throws Exception {
		Uri uri = Uri.parse("content://sms/");
		File files = new File(file, "backupSms.xml");
		FileInputStream fis = new FileInputStream(files);
		if (flag) {
			context.getContentResolver().delete(uri, null, null);
		}

		int process = 0;
		ContentValues values = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(fis, "utf-8");
		int type = parser.getEventType();
		while(type != XmlPullParser.END_DOCUMENT){
			String tagName = parser.getName();
			switch (type) {
			case XmlPullParser.START_TAG:
				if("smss".equals(tagName)){
					callBack.beforeBackup(Integer.parseInt(parser.getAttributeValue(null, "max")));
				}else if("sms".equals(tagName)){
					values = new ContentValues();
				}else if("body".equals(tagName)){
					values.put("body", parser.nextText());
				}else if("address".equals(tagName)){
					values.put("address", parser.nextText());
				}else if("type".equals(tagName)){
					values.put("type", parser.nextText());
				}else if("date".equals(tagName)){
					values.put("date", parser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if("sms".equals(tagName)){
					context.getContentResolver().insert(uri, values);
					process ++;
					callBack.onSmsBackup(process);
				}
				break;
			default:
				break;
			}
			type=parser.next();
		}
		fis.close();
	}
	
	
}
