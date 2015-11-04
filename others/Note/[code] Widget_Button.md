# Button
---
### 1.获取/失去焦点
 - layout＿activity_main.xml
 > - **android:background="@drawable/btn"**
 > - **android:clickable="true"**
 > - **android:focusable="true"**

		<RelativeLayout 
			android:layout_width="fill_parent"
			android:layout_height="50dp"
			android:background="@drawable/btn"
			android:clickable="true"
			android:focusable="true">
		        
			<TextView 
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_centerInParent="true"
			    android:text="白-->灰"/>
		</RelativeLayout>

 - **drawable＿btn.xml** (*state_pressed的参数在前*)
 > - **<item android:drawable="@xxx" android:state_pressed="true"/>**

		<?xml version="1.0" encoding="utf-8"?>
		<selector xmlns:android="http://schemas.android.com/apk/res/android" >
		
		    <item android:drawable="@color/gray_pressed" android:state_pressed="true"/>
		    <item android:drawable="@color/white_normal"/>
		    
		</selector>

 - values＿colors.xml
 
		<?xml version="1.0" encoding="utf-8"?>
		<resources>
		    <color name="white_normal">#FFFFFFFF</color>
		    <color name="gray_pressed">#44ABABAB</color>
		</resources>

### 2.按钮
 - **drawable＿btn.xml** (*state_pressed的参数在前*)
 > - **<item android:drawable="@xxx" android:state_enabled="true" android:state_focused="true"/>**

		<?xml version="1.0" encoding="utf-8"?>
		<selector xmlns:android="http://schemas.android.com/apk/res/android">
		
		    <item android:drawable="@color/gray_pressed" android:state_pressed="true"/>
		    <item android:drawable="@color/gray_pressed" android:state_enabled="true" android:state_focused="true"/>
		    <item android:drawable="@color/white_normal" android:state_enabled="true"/>
		    <item android:drawable="@color/gray_pressed" android:state_focused="true"/>
		
		</selector>

> - 其余代码同*1.获取/失去焦点*
> - `layout＿activity_main.xml`不需要`android:focusable="true"`参数