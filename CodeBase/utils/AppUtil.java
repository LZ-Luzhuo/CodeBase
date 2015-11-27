package com.example.appdemo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.appdemo.R;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.RemoteException;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-23 下午2:30:45
 * 
 * 描述:App相关的工具
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class AppUtil {
	/**
	 * 获取当前应用程序的版本号
	 * <pre> android:versionCode="1"  //int类型
     * android:versionName="1.0.1"//String类型,当前获取的是该信息</pre>
	 * @return 版本号/""
	 */
	public static String getVersion(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			// 代表的就是清单文件的信息。
			PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 安装apk文件
	 * @param file 文件路径
	 */
	public static void installApk(Context context,File file) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	
	/**
	 * 卸载当前应用
	 * @param context
	 */
	public static void uninstallApk(Context context){
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DELETE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:"+context.getPackageName()));
		context.startActivity(intent);
	}
	
	/**
	 * 获取所有的安装的应用程序信息(耗时操作,请在辅助线程进行)
	 * @return List<AppInfo>
	 */
	public static List<AppInfo> getAppInfos(Context context){
		PackageManager pm = context.getPackageManager();
		//所有的安装在系统上的应用程序包信息。
		List<PackageInfo> packInfos = pm.getInstalledPackages(0);
		List<AppInfo> appInfos = new ArrayList<AppInfo>();
		for(PackageInfo packInfo : packInfos){
			AppInfo appInfo = new AppInfo();
			//packInfo  相当于一个应用程序apk包的清单文件
			String packname = packInfo.packageName;
			Drawable icon = packInfo.applicationInfo.loadIcon(pm);
			String name = packInfo.applicationInfo.loadLabel(pm).toString();
			int flags = packInfo.applicationInfo.flags;//应用程序信息的标记
			int uid = packInfo.applicationInfo.uid;
			if((flags&ApplicationInfo.FLAG_SYSTEM)==0){
				appInfo.userApp=true;
			}else{
				appInfo.userApp=false;
			}
			if((flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)==0){
				//手机的内存
				appInfo.inRom=true;
			}else{
				//手机外存储设备
				appInfo.inRom=false;
			}
			appInfo.packname = packname;
			appInfo.icon = icon;
			appInfo.name = name;
			appInfo.uid = uid;
			appInfos.add(appInfo);
		}
		return appInfos;
	}
	
	public static class AppInfo {
		public Drawable icon;
		public String name;
		public String packname;
		public int uid;
		public boolean inRom; //安装位置:true手机内存;falseSD卡
		public boolean userApp; //用户应用:true用户应用;false系统
	}
	
	/**
	 * 打开一个应用程序
	 * @param context
	 * @param packName 包名("com.example.demo") 
	 * @return 能开启true/不能开启false
	 */
	public static boolean startApp(Context context,String packName) {
		PackageManager pm = context.getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage(packName);
		if (intent != null) {
			context.startActivity(intent);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断一个应用程序是否可以启动
	 * @param context
	 * @param packName 包名("com.example.demo") 
	 * @return 能开启true/不能开启false
	 */
	public static boolean isStartApp(Context context,String packName) {
		PackageManager pm = context.getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage(packName);
		return intent != null ? true : false;
	}
	
	/**
	 * 获取所有可以启动的app
	 * @param context
	 * @return ResolveInfo的集合(包名:resolveInfo.activityInfo.packageName)
	 */
	public static List<ResolveInfo> getStartApp(Context context){
		PackageManager pm = context.getPackageManager();
		 Intent intent = new Intent();
		 intent.setAction("android.intent.action.MAIN");
		 intent.addCategory("android.intent.category.LAUNCHER");
		 List<ResolveInfo> infos = pm.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS); //查询出所有具有启动能力的activity。
		return infos;
	}
	
	/**
	 * 分享:使用系统自带的分享功能.(市场上有专门分享的sdk,一般不用这个)
	 */
	public static void share(Context context, String text) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.SEND");
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, text);
		context.startActivity(intent);
	}
	
	/**
	 * 创建快捷图标(每次调用都会创建)
	 * 权限:"com.android.launcher.permission.INSTALL_SHORTCUT"
	 * @param appName app名称
	 * @param className 启动界面的全类名("com.example.appdemo.MainActivity")
	 * @param icon app位图图标(R.drawable.ic_launcher)
	 */
	public static void installShortCut(Context context,String appName,String className, int icon) {
		Intent intent = new Intent();
		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(context.getResources(), icon));
		Intent shortcutIntent = new Intent();
		shortcutIntent.setAction("android.intent.action.MAIN");
		shortcutIntent.addCategory("android.intent.category.LAUNCHER");
		shortcutIntent.setClassName(context.getPackageName(), className);
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		context.sendBroadcast(intent);
	}
	
	/**
	 * 获取正在运行的进程
	 * @param context 上下文
	 * @return RunningAppProcessInfo的集合
	 */
	public static List<RunningAppProcessInfo> getRunningProcess(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
		return infos;
	}
	
	/**
	 * 获取手机的可用内存
	 * @param context 上下文
	 * @return 可用空间
	 */
	public static long getAvailMem(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		return outInfo.availMem;
	}
	
	/**
	 * 获取手机的总内存
	 * @param context 上下文
	 * @return long 总空间 B/0
	 */
	@SuppressLint("NewApi")
	public static long getTotalMem(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    		// apk16才可获取
    		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    		MemoryInfo outInfo = new MemoryInfo();
    		am.getMemoryInfo(outInfo);
    		return outInfo.totalMem;
        }else{
    		try {
				File file = new File("/proc/meminfo");
				FileInputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String line = br.readLine();
				//MemTotal:         513000 kB
				StringBuilder sb = new StringBuilder();
				for(char c: line.toCharArray()){
					if(c>='0'&&c<='9'){
						sb.append(c);
					}
				}
				br.close();
				return Long.parseLong(sb.toString())*1024;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
        }
	}
	
	/**
	 * 获取所有的进程信息(耗时操作)
	 * @param context 上下文
	 * @return
	 */
	public static List<TaskInfo> getTaskInfos(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		PackageManager pm = context.getPackageManager();
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		List<TaskInfo> taskInfos = new ArrayList<TaskInfo>();
		for(RunningAppProcessInfo processInfo : processInfos){
			TaskInfo taskInfo = new TaskInfo();
			//应用程序的包名。
			String packname = processInfo.processName;
			taskInfo.packname = packname;
			android.os.Debug.MemoryInfo[] memoryInfos = am.getProcessMemoryInfo(new int[]{processInfo.pid});
			long memsize = memoryInfos[0].getTotalPrivateDirty()*1024l;
			taskInfo.memsize = memsize;
			try {
				ApplicationInfo applicationInfo = pm.getApplicationInfo(packname, 0);
				Drawable icon = applicationInfo.loadIcon(pm);
				taskInfo.icon = icon;
				String name = applicationInfo.loadLabel(pm).toString();
				taskInfo.name = name;
				if((applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM) == 0){
					//用户进程
					taskInfo.userTask = true;
				}else{
					//系统进程
					taskInfo.userTask = false;
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
				taskInfo.icon = (context.getResources().getDrawable(R.drawable.ic_default));
				taskInfo.name = packname;
			}
			taskInfos.add(taskInfo);
		}
		return taskInfos;
	}
	
	public static class TaskInfo{
		public Drawable icon;
		public String name;
		public String packname;
		public long memsize; //占用内存大小
		public boolean userTask; //是否是用户进行
	}
	
	/**
	 * 终止其他进程(不可终止自身)
	 * 权限:"android.permission.KILL_BACKGROUND_PROCESSES"
	 * @param packName 包名("com.example.demo") 
	 */
	public static void stopTask(Context context,String packName) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		am.killBackgroundProcesses(packName);
	}
	
	/**
	 * 获取用户正在操作的App
	 * 权限:"android.permission.GET_TASKS"
	 * @param context
	 * @return 用户正在操作的App的包名("com.example.appdemo")
	 */
	public static String getCurrentRunApp(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> infos = am.getRunningTasks(1); //获取在运行的任务栈
		String packname = infos.get(0).topActivity.getPackageName();
		return packname;
	}
	
	/**
	 * 开启Activity,在Activity里开启某个界面
	 * @param cls 被开启的Activity
	 */
	public static void startActivityAC(Context context,Class<?> cls){
		Intent intent = new Intent(context, cls);
		context.startActivity(intent);
	}
	
	/**
	 * 开启Activity,在Service里开启某个界面
	 * @param cls 被开启的Activity
	 */
	public static void startActivitySE(Context context, Class<?> cls){
		Intent intent = new Intent(context, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * 回到桌面,类似于按实体的主页键
	 */
	public static void backDesktop(Context context){
		Intent intent = new Intent();
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.HOME");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addCategory("android.intent.category.MONKEY");
		context.startActivity(intent);
	}
	
	/**
	 * 获取应用的包信息
	 * @param context
	 */
	public static List<SimplPackageInfo> getPackageInfo(Context context){
		List<SimplPackageInfo> packageinfos = new ArrayList<SimplPackageInfo>();
		
		PackageManager pm = context.getPackageManager();
		List<PackageInfo>  infos = pm.getInstalledPackages(0);
		for(PackageInfo info:infos){
			String datadir = info.applicationInfo.dataDir; //app包的包路径
			String sourcedir = info.applicationInfo.sourceDir;//apk文件路径
			String name = info.applicationInfo.loadLabel(pm).toString(); //应用程序名称
			String packageName = info.packageName; //app的包名
			
			SimplPackageInfo simplPackageInfo = new SimplPackageInfo();
			simplPackageInfo.apkDir = sourcedir;
			simplPackageInfo.name = name;
			simplPackageInfo.packageName = packageName;
			simplPackageInfo.resourceDir = datadir;
			packageinfos.add(simplPackageInfo);
		}
		return packageinfos;
	}
	
	public static class SimplPackageInfo{
		/**
		 * apk文件路径
		 */
		public String apkDir;
		/**
		 * apk的包路径
		 */
		public String resourceDir;
		/**
		 * apk应用名
		 */
		public String name;
		/**
		 * apk包名
		 */
		public String packageName;
	}
	
	/**
	 * 扫描手机里面所有应用程序的缓存信息
	 * 权限:"android.permission.GET_PACKAGE_SIZE"
	 * @return  CacheInfo缓存信息的集合
	 */
	public static List<CacheInfo> scanCache(Context context) {
		List<CacheInfo> cacheInfos = new ArrayList<CacheInfo>();

		// 通过反射获得getPackageSizeInfo()方法
		Method getPackageSizeInfoMethod = null;
		Method[] methods = PackageManager.class.getMethods();
		for (Method method : methods) {
			if ("getPackageSizeInfo".equals(method.getName())) {
				getPackageSizeInfoMethod = method;
			}
		}
		
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packInfos = pm.getInstalledPackages(0);
		for (PackageInfo packInfo : packInfos) {
			try {
				final CacheInfo cacheInfo = new CacheInfo();
				String name = packInfo.applicationInfo.loadLabel(pm).toString();
				getPackageSizeInfoMethod.invoke(pm, packInfo.packageName, new IPackageStatsObserver.Stub(){
					@Override
					public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
						long cache = pStats.cacheSize; //缓存大小
						long code = pStats.codeSize; //代码大小
						long data = pStats.dataSize; //数据大小
						String packageName = pStats.packageName;
						
						cacheInfo.code = code;
						cacheInfo.data = data;
						cacheInfo.cache = cache;
						cacheInfo.packageName = packageName;
					}
				});
				cacheInfo.name = name;
				cacheInfos.add(cacheInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cacheInfos;
	}
	
	public static class CacheInfo{
		public String name; //应用名
		/**
		 * app的大小byte
		 */
		public long code;
		/**
		 * 数据的大小byte
		 */
		public long data;
		/**
		 * 缓存的大小byte
		 */
		public long cache;
		public String packageName; //包名
	}
	
	/**
	 * 清理手机的全部缓存(系统不允许对单个应用进行缓存清理)<b>注意:该方法并不能清除全部缓存,并且会导致{@link AppUtil#scanCache(Context)}获取数据错误.</b>
	 */
	@Deprecated
	public static void clearAllCache(Context context){
		PackageManager pm = context.getPackageManager();
		Method[] methods = PackageManager.class.getMethods();
		for(Method method:methods){
			if("freeStorageAndNotify".equals(method.getName())){
				try {
					method.invoke(pm, Integer.MAX_VALUE,new IPackageDataObserver.Stub(){
						@Override
						public void onRemoveCompleted(String packageName,boolean succeeded) throws RemoteException {
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}
}
