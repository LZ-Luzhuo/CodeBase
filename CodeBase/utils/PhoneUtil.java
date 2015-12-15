package com.example.sms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.AsyncQueryHandler;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.PhoneLookup;
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
	 * 发送短信(无任何通知)
	 * <br>权限"android.permission.SEND_SMS"
	 * @param number 短信号码
	 * @param text 短信内容
	 */
	public static void sendMessage(String number, String text){
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> divideMessage = smsManager.divideMessage(text); //分割短信70字符
		for (String sms : divideMessage) {
			smsManager.sendTextMessage(number, null, sms, null, null);
		}
	}
	
	private static OnSendSmsState onSendSmsState;
	/**
	 * 发送短信,并回调告知是否发送成功,并将短信内容写入发件箱
	 * <br>权限"android.permission.SEND_SMS"
	 * <pre>
	 * 		<receiver android:name="com.example.sms.utils.PhoneUtil$SendSmsBroadcastReceive">
     *     		<intent-filter >
     *         		<action android:name="SendSmsBroadcastReceive"/>
     *     		</intent-filter>
     * 		</receiver>
     * </pre>
	 * @param number 短信号码
	 * @param text 短信内容
	 */
	public static void sendMessage(Context context, String number, String text, OnSendSmsState onSendSmsState){
		PhoneUtil.onSendSmsState = onSendSmsState;
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> divideMessage = smsManager.divideMessage(text);
		
		// 发送是否成功的广播接收者
		Intent intent = new Intent("SendSmsBroadcastReceive");
		PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		
		for (String sms : divideMessage) {
			smsManager.sendTextMessage(number, null, sms, sentIntent, null);
		}
		
		// 插入到数据库
		writeMessage(context, number, text, 2);
	}
	
	/**
	 * 添加短信内容到短信发件箱数据库
	 * @param context
	 * @param number
	 * @param text
	 */
	public static void writeSendSmsMessage(Context context, String number, String text) {
		writeMessage(context, number, text, 2);
	}
	
	/**
	 * 添加短信内容到短信收件箱数据库
	 * @param context
	 * @param number
	 * @param text
	 */
	public static void writeReceiveMessage(Context context, String number, String text) {
		writeMessage(context, number, text, 1);
	}
	
	/**
	 * 添加短信内容到短信数据库
	 * @param context
	 * @param number
	 * @param text 
	 * @param type 接收1/发送2
	 */
	public static void writeMessage(Context context, String number, String text, int type){
		ContentValues values = new ContentValues();
		values.put("address", number);
		values.put("type", type); //接收1;发送2
		values.put("body", text);
		
		context.getContentResolver().insert(Uri.parse("content://sms/"), values);
	}
	
	public interface OnSendSmsState{
		/**
		 * 短信的发送状态
		 * @param state true成功/false失败
		 */
		public void onSendSmsState(boolean state);
	}
	
	/**
	 * 发送短信是否成功的广播接受者.
	 * @author Luzhuo
	 */
	public static class SendSmsBroadcastReceive extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int resultCode = getResultCode();
			if(resultCode == Activity.RESULT_OK) {
				// 短信发送成功
				if (onSendSmsState != null) onSendSmsState.onSendSmsState(true);
			} else {
				if (onSendSmsState != null) onSendSmsState.onSendSmsState(false);
			}
		}
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
	
	/**
	 * 根据号码获取联系人的姓名
	 * <br>权限:android:name="android.permission.READ_CONTACTS"
	 * @param address 号码
	 * @return String/null
	 */
	public static String getContactName(Context context, String address) {
		// content://com.android.contacts/phone_lookup/95556
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
		
		Cursor cursor = context.getContentResolver().query(uri, new String[]{"display_name"}, null, null, null);
		if(cursor != null && cursor.moveToFirst()) {
			String contactName = cursor.getString(0);
			cursor.close();
			return contactName;
		}
		return null;
	}
	
	/**
	 * 根据联系人的号码查询联系人的头像
	 * <br>权限:android:name="android.permission.READ_CONTACTS"
	 * @param context
	 * @param address
	 * @return Bitmap/null
	 */
	public static Bitmap getContactIcon(Context context, String address) {
		// 1.根据号码取得联系人的id
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
		
		Cursor cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);
		if(cursor != null && cursor.moveToFirst()) {
			long id = cursor.getLong(0);
			cursor.close();
			
			// 2.根据id获取联系人的头像
			uri = ContentUris.withAppendedId(Contacts.CONTENT_URI, id);
			InputStream is = Contacts.openContactPhotoInputStream(resolver, uri);
			return BitmapFactory.decodeStream(is);
		}
		return null;
	}
	
	/**
	 * 查询(异步)短信条目(组)
	 * <br>查询的信息有:_id:id,body:最新数据,count:一个条目包含的条数,date:时间戳,address:手机号码
	 * <br>权限:android:name="android.permission.READ_SMS"
	 * @param context
	 * @param onAsyncQuerySmsItemListener 监听
	 */
	public static void AsyncQuerySmsItem(Context context, final OnAsyncQuerySmsItemListener onAsyncQuerySmsItemListener){
		String[] projection = { 
				"sms.thread_id AS _id", //id
				"sms.body AS body", //最新数据
				"groups.msg_count AS count", //一个条目包含的条数
				"sms.date AS date", //时间戳
				"sms.address AS address"  };  //手机号码
		
		// AsyncQueryHandler 异步查询类
		AsyncQueryHandler asyncQuery = new AsyncQueryHandler(context.getContentResolver()) {
			@Override
			protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
				super.onQueryComplete(token, cookie, cursor);
				// 查询完成
				if(onAsyncQuerySmsItemListener != null)
					onAsyncQuerySmsItemListener.onCompleteQuerySmsItem(token, cookie, cursor);
			}
		};
		// 开始异步查询数据 startQuery(token:任意数字;Object:任意对象;uri:Uri;projection:列;selection:选择语句,selectionArgs:选择条件,orderBy:排序)
		asyncQuery.startQuery(0, null, Uri.parse("content://sms/conversations"), projection, null, null, "date desc");
	}
	
	public interface OnAsyncQuerySmsItemListener{
		/**
		 * 查询短信条目数据完成
		 */
		public void onCompleteQuerySmsItem(int token, Object cookie, Cursor cursor);
	}
	
	/**
	 * 根据id删除某组短信
	 * <br>id集合通过{@link PhoneUtil#AsyncQuerySmsItem(Context, OnAsyncQuerySmsItemListener)}方法获得
	 * <br>权限:android:name="android.permission.WRITE_SMS"
	 * @param deletelist id集合
	 */
	public static void deleteSmsItem(Context context, List<Integer> deletelist, OnDeleteSmsItem onDeleteSmsItem){
		Iterator<Integer> iterator = deletelist.iterator();
		int max = deletelist.size();
		int progress = 0;
		int thread_id;
		String where;
		String[] selectionArgs;
		// 开始
		onDeleteSmsItem.onPrepareDeleteSmsItem(max);
		while(iterator.hasNext()) {
			thread_id = iterator.next();
			where = "thread_id = ?";
			selectionArgs = new String[]{String.valueOf(thread_id)};
			context.getContentResolver().delete(Uri.parse("content://sms/"), where, selectionArgs);
			
			// 更新进度条
			progress ++;
			onDeleteSmsItem.onDeleteSmsItem(progress);
		}
		//完成
		deletelist.clear();
		onDeleteSmsItem.onCompleteDeleteSmsItem();
	}
	
	public interface OnDeleteSmsItem{
		/**
		 * 准备删除
		 */
		public void onPrepareDeleteSmsItem(int max);
		/**
		 * 正在删除
		 */
		public void onDeleteSmsItem(int progress);
		/**
		 * 删除完成
		 */
		public void onCompleteDeleteSmsItem();
	}
	
	/**
	 * 异步获取<b>指定条目组的id</b>获取短信详情
	 * <br>获取的内容:_id:id;body:内容;date:时间;address:号码;type:1.接收 2.发送
	 * @param groupId 通过{@link PhoneUtil#AsyncQuerySmsItem(Context, com.example.sms.utils.PhoneUtil.OnAsyncQuerySmsItemListener)}获得
	 */
	public static void AsyncQuerySmsDetails(Context context,int groupId, final OnAsyncQuerySmsItemListener onAsyncQuerySmsItemListener){
		final String[] projection = {
				"_id",
				"body", // 内容
				"date", // 时间
				"address", // 号码
				"type" // 1.接收 2.发送
		};
		String selection = "thread_id = ?";
		String[] selectionArgs = { String.valueOf(groupId) };
		
		AsyncQueryHandler asyncQuery = new AsyncQueryHandler(context.getContentResolver()) {
			@Override
			protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
				super.onQueryComplete(token, cookie, cursor);
				// 查询完成
				if(onAsyncQuerySmsItemListener != null)
					onAsyncQuerySmsItemListener.onCompleteQuerySmsItem(token, cookie, cursor);
			}
		};
		asyncQuery.startQuery(0, null, Uri.parse("content://sms/"), projection, selection, selectionArgs, "date");
	}
	
	/**
	 * 获取指定组的所有短信,比如收件箱的所有短信.
	 * <br>_id:id;address:号码;date:时间戳;body:内容
	 * @param context
	 * @param SmsGroupId 0所有短信/1收件箱/2发件箱/3已发送/4草稿箱
	 */
	public static void AsyncQuerySms(Context context,int SmsGroupId, final OnAsyncQuerySmsItemListener onAsyncQuerySmsItemListener){
		if(SmsGroupId > 4) return;
		Uri[] uri = new Uri[]{Uri.parse("content://sms/"), 
				Uri.parse("content://sms/inbox"),  //收件箱的uri
				Uri.parse("content://sms/outbox"), //发件箱的uri
				Uri.parse("content://sms/sent"), //已发送的uri
				Uri.parse("content://sms/draft") }; //草稿箱的uri
		
		final String[] projection = {
				"_id",
				"address",
				"date",
				"body",
				"type" // 1.接收 2.发送
		};
		
		AsyncQueryHandler asyncQuery = new AsyncQueryHandler(context.getContentResolver()) {
			@Override
			protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
				super.onQueryComplete(token, cookie, cursor);
				// 查询完成
				if(onAsyncQuerySmsItemListener != null)
					onAsyncQuerySmsItemListener.onCompleteQuerySmsItem(token, cookie, cursor);
			}
		};
		asyncQuery.startQuery(0, null, uri[SmsGroupId], projection, null, null, "date desc");
	}
	
	/**
	 * 将Cursor转换成HashMap集合,
	 * @param cursor
	 * @param closeCursor 是否关闭游标集,默认不关闭
	 * @return HashMap<String, String>/null
	 */
	public static ArrayList<HashMap<String, String>> onCursorToHashMap(Cursor cursor,boolean closeCursor){
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
		if(cursor != null && cursor.getCount() > 0) {
			String columnName;
			String columnValue;
			while(cursor.moveToNext()) {
				HashMap<String, String> hashmap = new HashMap<String, String>();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					columnName = cursor.getColumnName(i);
					columnValue = cursor.getString(i);
					hashmap.put(columnName, columnValue);
				}
				arrayList.add(hashmap);
			}
			if(closeCursor) cursor.close();
			return arrayList;
		}
		return null;
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
