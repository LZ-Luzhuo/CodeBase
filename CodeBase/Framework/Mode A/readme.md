# 框架
---
## 文件说明:
 - ChatApplication.java	:	//管理应用,需要在清单文件<application>里配置android:name=".ChatApplication"  
 
### base
 - BaseActivity.java :		//管理所有的Activity,管理activity的创建与销毁
 - BaseFragment.java :		//管理所有的Fragment
 - BaseIntentService.java :	//
 - BaseService.java : 		//管理所有的Service,管理Service的创建与销毁

### lib
 - ChatManager.java			//封装了常用方法
	 - sendRequest			//封装了全局的请求
 - Error.java				//错误码
 - ObjectCallback.java		//网络请求回调接口

### utils
 - DirUtil.java				//文件存储目录管理(缓存等)
 - ToastUtil.java			//自定义吐司管理