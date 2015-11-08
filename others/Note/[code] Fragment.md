# Fragment
---
## 生命周期
> - 切换Fragment:onCreate() --> onCreateView --> onStart() --> onResume | --> onPause --> onStop --> onDestory
> - 主页键:Fragment:onCreate() --> onCreateView --> onStart() --> onResume | --> onPause --> onStop


## 基本使用
**MainActivity.java**  
>
		public class MainActivity extends FragmentActivity {
>		
			@Override
			protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
			}
>		
			public void fragment1(View v) {
				// 创建Fragment对象
				Fragment1 fragment1 = new Fragment1();
				// 获取fragment管理器;// 打开事务;// 把内容显示至帧布局;// 提交
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment1).commit();
			}
		}


**activity_main.xml**
>
		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
		    xmlns:tools="http://schemas.android.com/tools"
		    android:layout_width="match_parent"
		    android:layout_height="match_parent"
		    tools:context=".MainActivity" >
>		
		    <LinearLayout
		        android:layout_width="wrap_content"
		        android:layout_height="wrap_content"
		        android:orientation="vertical" >
>		
		        <Button
		            android:layout_width="wrap_content"
		            android:layout_height="wrap_content"
		            android:onClick="fragment1"
		            android:text="fragment1"
		            android:textColor="@color/holo_red_dark" />		
		    </LinearLayout>
>		
		    <!-- 必须是FrameLayout容器 -->
		    <FrameLayout
		        android:id="@+id/fragment"
		        android:layout_width="match_parent"
		        android:layout_height="match_parent" >
		    </FrameLayout>
>		
		</LinearLayout>


**Fragment1.java**
>
		public class Fragment1 extends Fragment {
			@Override
			public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
				View view = inflater.inflate(R.layout.fragment1, null);
				return view;
			}
		}
