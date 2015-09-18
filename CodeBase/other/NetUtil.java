package com.example.lotterydemo.net;

import com.example.lotterydemo.GlobalParams;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class NetUtil {

	/**
	 * ����û�������
	 * @return
	 */
	public static boolean checkNet(Context context){
		//�жϣ�WIFI����
		boolean isWIFI  = isWIFIConnection(context);
		//�жϣ�Mobile����
		boolean isMOBILE  = isMOBILEConnection(context);
		
		//���Mobile�����ӣ��ж����ĸ�APN��ѡ����
		if(isMOBILE){
		//APN��ѡ�У�������Ϣ�Ƿ������ݣ������wap��ʽ
			readAPN(context);  //�ж����ĸ�APN��ѡ����
		}
		
		if(!isWIFI&&!isMOBILE){
			return false;
		}
		
		return true;
	}

	/**
	 * APN��ѡ�У�������Ϣ�Ƿ������ݣ������wap��ʽ
	 * ����ֵ(proxy,port)���ø�GlobalParams(ȫ�ֲ���)
	 * @param context
	 */
	private static void readAPN(Context context) {
		Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn"); //4.0ģ�������ε���Ȩ��

		//������ϵ������
		ContentResolver resolver = context.getContentResolver();
		//�ж����ĸ�APN��ѡ����
		Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null, null);
		if(cursor!=null&&cursor.moveToFirst()){
			GlobalParams.PROXY = cursor.getString(cursor.getColumnIndex("proxy")); //��������
			GlobalParams.PORT = cursor.getInt(cursor.getColumnIndex("port")); //����˿�
		}
	}

	/**
	 * �жϣ�Mobile����
	 * @param context
	 * @return
	 */
	private static boolean isMOBILEConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(networkInfo != null){
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * �жϣ�WIFI����
	 * @param context
	 * @return
	 */
	private static boolean isWIFIConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(networkInfo != null){
			return networkInfo.isConnected();
		}
		return false;
	}
}
