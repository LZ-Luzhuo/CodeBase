package com.example.lzchat.net;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.Uri;

import com.example.lzchat.GlobalParams;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-25 下午9:24:58
 * 
 * 描述:网络工具,主要用于网络判断
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class NetUtil {

	/**
	 * 检查用户的网络
	 * @return
	 */
	public static boolean checkNet(Context context){
		//判断：WIFI连接
		boolean isWIFI  = isWIFIConnection(context);
		//判断：Mobile连接
		boolean isMOBILE  = isMOBILEConnection(context);
		
		//如果Mobile在连接，判断是哪个APN被选中了
		if(isMOBILE){
		//APN被选中，代理信息是否有内容，如果有wap方式
			readAPN(context);  //判断是哪个APN被选中了
		}
		
		if(!isWIFI&&!isMOBILE){
			return false;
		}
		
		return true;
	}

	/**
	 * APN被选中，代理信息是否有内容，如果有wap方式
	 * 并将值(proxy,port)设置给GlobalParams(全局参数)
	 * @param context
	 */
	private static void readAPN(Context context) {
		Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn"); //4.0模拟器屏蔽掉该权限

		//操作联系人类似
		ContentResolver resolver = context.getContentResolver();
		//判断是哪个APN被选中了
		Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null, null);
		if(cursor!=null&&cursor.moveToFirst()){
			GlobalParams.PROXY = cursor.getString(cursor.getColumnIndex("proxy")); //代理主机
			GlobalParams.PORT = cursor.getInt(cursor.getColumnIndex("port")); //代理端口
		}
	}

	/**
	 * 判断：Mobile连接
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
	 * 判断：WIFI连接
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
	
	/**
	 * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
	 * @param context 上下文
	 * @return 0：无网络 1：WIFI 2：CMWAP 3：CMNET
	 */
	public static int isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return 0;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						NetworkInfo netWorkInfo = info[i];
						if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
							return 1;
						} else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
							String extraInfo = netWorkInfo.getExtraInfo();
							if ("cmwap".equalsIgnoreCase(extraInfo) || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
								return 2;
							}
							return 3;
						}
					}
				}
			}
		}
		return 0;
	}
	
	/**
	 * 获取指定app使用的总流量(所有网络接口流量,<b>从本次开机到现在的统计数据</b>)
	 * @param uid 可以调用AppUtil.getAppInfos(Context)方法获取app的uid
	 * @return Flux 流量类
	 */
	public static Flux getAppFlux(int uid){
		//proc/uid_stat/10086
		long tx = TrafficStats.getUidTxBytes(uid);//某个应用所有网络接口上传的流量byte
		long rx = TrafficStats.getUidRxBytes(uid);//某个应用所有网络接口下载的流量 byte
		
		Flux flux = new Flux();
		flux.TxBytes = tx;
		flux.RxBytes = rx;
		return flux;
	}
	
	/**
	 * 获取手机使用蜂窝的总流量(4g/3g/2g)
	 * @return Flux 流量类
	 */
	public static Flux getMobileFlux(){
		long mobileTxBytes = TrafficStats.getMobileTxBytes();//手机3g/2g上传的总流量
		long mobileRxBytes = TrafficStats.getMobileRxBytes();//手机2g/3g下载的总流量
		
		Flux flux = new Flux();
		flux.TxBytes = mobileTxBytes;
		flux.RxBytes = mobileRxBytes;
		return flux;
	}
	
	/**
	 * 获取手机所有网络接口的总流量(wifi/4g/3g/2g)
	 * @return Flux 流量类
	 */
	public static Flux getTotalFlux(){
		long totalTxBytes = TrafficStats.getTotalTxBytes();//手机全部网络接口 包括wifi，3g、2g上传的总流量
		long totalRxBytes = TrafficStats.getTotalRxBytes();//手机全部网络接口 包括wifi，3g、2g下载的总流量
		
		Flux flux = new Flux();
		flux.TxBytes = totalTxBytes;
		flux.RxBytes = totalRxBytes;
		return flux;
	}
	
	public static class Flux{
		/**
		 * 上传的流量(-1表示不支持流量统计/没有产生流量)
		 */
		public long TxBytes;
		/**
		 * 下载的流量(-1表示不支持流量统计/没有产生流量)
		 */
		public long RxBytes;
	}
}
