# 系统广播接收
---
## 短信接收
> - 权限:`<uses-permission android:name="android.permission.RECEIVE_SMS"/>`
> - 过滤:`<action android:name="android.provider.Telephony.SMS_RECEIVED" />`
>
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		for(Object obj:objs){
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			String body = smsMessage.getMessageBody(); //内容
			String sender = smsMessage.getOriginatingAddress(); //发送者
			//abortBroadcast();//截断广播
		}

## 来电(非广播)
> - 权限:`<uses-permission android:name="android.permission.READ_PHONE_STATE"/>`
> - 过滤:/
> 
 		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		tm.listen(new PhoneStateListener(){
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				super.onCallStateChanged(state, incomingNumber);
				// incomingNumber:电话号码
				// state:TelephonyManager.CALL_STATE_RINGING:// 响铃状态
				// state:TelephonyManager.CALL_STATE_IDLE:// 空闲状体
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);
		//tm.listen(listener, PhoneStateListener.LISTEN_NONE); // 取消监听


	/**
	 * 挂断电话(通过反射)
	 * <br>权限:"android.permission.CALL_PHONE"
	 */
	public void endcall() {
		try {
			Class clazz = 	MainActivity.class.getClassLoader().loadClass("android.os.ServiceManager");
			Method method = clazz.getDeclaredMethod("getService", String.class);
			IBinder b  = (IBinder) method.invoke(null, TELEPHONY_SERVICE); 
			//获取到了原生未经包装的系统电话的管理服务。
			ITelephony iTelephony = ITelephony.Stub.asInterface(b);
			iTelephony.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

>
	/**
	 * 清除指定号码的通讯记录(内容观察者观察到通讯记录变化时在调用)
	 * 权限:"android.permission.WRITE_CONTACTS"
	 * <pre> 
	 * 	Uri url = Uri.parse("content://call_log/calls");
	 *	getContentResolver().registerContentObserver(url, true, new ContentObserver(new Handler()){
	 *		public void onChange(boolean selfChange) {
	 *			super.onChange(selfChange);
	 *			getContentResolver().unregisterContentObserver(this);
	 *			deleteCalllog(incomingNumber); //观察到通讯记录变化再调用
	 *		}
	 *	});
	 * </pre> 
	 * @param incomingNumber 号码
	 */
	public void deleteCalllog(String incomingNumber) {
		ContentResolver resolver = getContentResolver();
		Uri url = Uri.parse("content://call_log/calls");
		resolver.delete(url, "number=?", new String[]{incomingNumber});
	}


## 去电
> - 权限:`<uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>`
> - 过滤:`<action android:name="android.intent.action.NEW_OUTGOING_CALL" />`
>
		String phone = getResultData(); //号码

## 锁屏(只有代码注册广播才有效)

	// 代码注册锁屏广播
	xxxReceiver receiver = new xxxReceiver();
	IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);//锁屏
	//IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);//解屏
	context.registerReceiver(receiver, intentFilter);

	//注销广播
	//context.unregisterReceiver(receiver); 