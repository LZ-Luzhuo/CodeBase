#启动界面
##启动应用程序时，会突然白一下，再进入启动界面,处理方式:

#####1.直接删除android:theme="@style/AppTheme"

	<application  
       android:name="com.jchdcp.JchdcpApplication"  
       android:allowBackup="true"  
       android:icon="@drawable/ic_launcher"  
       android:label="@string/app_name"  
       android:theme="@style/AppTheme" >  

#####2.或者是将AppTheme设置如下

	<style name="AppBaseTheme" parent="android:Theme.Light"></style>  

    <!-- Application theme. -->  
    <style name="AppTheme" parent="AppBaseTheme">  
        <item name="android:windowBackground">@android:color/transparent</item>  
        <item name="android:windowNoTitle">true</item>  
    </style>  

#####问题解析:  
**初步分析:** 布局控件会变成黑色,且EditText控件下默认下划线无法显示