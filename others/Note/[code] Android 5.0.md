# Android 5.0 新特性
---
### 主题样式(Material Design) values-v21 --> styles.xml
	<style name="AppTheme" parent="android:Theme.Material.Light">
		<!-- 状态栏颜色-->
	    <!-- 状态栏颜色，会被statusBarColor效果覆盖-->
	    <item name="android:colorPrimaryDark">@color/status_red</item>
	    <!-- 顶部状态栏颜色 -->
	    <item name="android:statusBarColor">@color/status_red</item>
	    <!-- actionBar栏颜色 -->
	    <item name="android:colorPrimary">@color/action_red</item>
	    <!-- 底部状态栏颜色 -->
	    <item name="android:navigationBarColor">@color/navigation_red</item>
	    <!-- action背景颜色 -->
	    <item name="android:windowBackground">@color/window_bg_red</item>
	
		<!-- 文字颜色 -->
	    <!-- Button，textView的文字颜色  -->
	    <item name="android:textColor">@color/white_text</item>
	    <!-- RadioButton checkbox等控件的文字 -->
	    <item name="android:textColorPrimaryDisableOnly">@color/white_text</item>
	    <!-- actionBar的标题文字颜色 -->
	    <item name="android:textColorPrimary">@color/white_text</item>
	
	</style>


### 控件
##### 三维 `activity_main.xml`
	x轴:layout_width = "100dp"
	y轴:layout_height = "100dp"
	z轴:elevation = "100dp"		或者		translationZ = "100dp"


#####  轮廓
>> - `activity_main.xml` 设置 `android:outlineProvider`
>>> 
>>>
	none:	无效果
	background:	背景
	bounds:		阴影大小
	paddedBounds:	阴影厚度

>>
>> - `activity_main.xml` 设置 `backgroud(circle.xml(drawable))`
>>> `circle.xml(drawable):`
>>>
    <?xml version="1.0" encoding="utf-8"?>
	<shape xmlns:android="http://schemas.android.com/apk/res/android"
        android:shape="oval">
        <!-- 边框 -->
        <stroke
            android:width="1dip"
            android:color="#eaff35" />
        <!-- 填充 -->
        <solid android:color="#eaff35" />
	</shape>

>> - 代码设置圆形:
>>> 
>>> 
	ViewOutlineProvider vop = new ViewOutlineProvider() {
		public void getOutline(View view, Outline outline){
			// 可以指定圆形,矩形,圆角矩形,path
			outline.setOval(0, 0, view.getWidth(), view.getHeight());
		{
	};
	(TextView) tv.setOutlineProvider(vop);

#####  裁剪
>> - 代码裁剪:
>
		ViewOutlineProvider vop = new ViewOutlineProvider() {
			public void getOutline(View view, Outline outline) {
				// 剪成圆形
				outline.setOval(0,0,view.getWidth(),view.getHeight());
			}
		};
		(TextView) tv.setOutlineProvider(vop);
		(TextView) tv.setClipToOutline(true);

##### 颜色渲染
> - `bitmap.xml`
	
	android:tintMode = "multiply"	//渲染模式
	android:tint = "#5677fc"	//颜色

> - 代码案例:
> 
		// 选择器
		<?xml version="1.0" encoding="utf-8"?>
		<selector xmlns:android="http://schemas.android.com/apk/res/android">
		    <item android:drawable="@drawable/tint_bitmap" android:state_pressed="true"/>
		    <item android:drawable="@mipmap/ring" />
		</selector>
> 
		// Bitmap 渲染
		<?xml version="1.0" encoding="utf-8"?>
		<bitmap xmlns:android="http://schemas.android.com/apk/res/android"
	        android:src="@mipmap/ring"
	        android:tintMode="multiply"
	        android:tint="#5677fc" /> 

##### vector矢量图
> - 案例代码:(path;工具:http://editor.method.ac/)
> 
		<?xml version="1.0" encoding="utf-8"?>
		<vector xmlns:android="http://schemas.android.com/apk/res/android"
		        android:height="300dp"
		        android:width="300dp"
		        android:viewportHeight="40"
		        android:viewportWidth="40" >
> 
		        <path android:fillColor="#ff00ff"
		                android:pathData="M20.5,9.5
		                        c-1.955,0,-3.83,1.268,-4.5,3
		                        c-0.67,-1.732,-2.547,-3,-4.5,-3
		                        C8.957,9.5,7,11.432,7,14
		                        c0,3.53,3.793,6.257,9,11.5
		                        c5.207,-5.242,9,-7.97,9,-11.5
		                        C25,11.432,23.043,9.5,20.5,9.5z"
		         />
		</vector>

##### 取色器
> - 案例代码:
> 
		//可以指定bitmap像素值,也不可不指定,一般不指定
		bitmap.getPixel(x,y)
> 
		//辅助线程直接使用：
		Palette palette = Palette.generate(Bitmap bitmap);
		//主线程中使用异步的方式：
		Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
		        public void onGenerated(Palette palette) {  }
		});
> 
		// 获取颜色
		palette.getVibrantColor(int defaultColor);	//鲜艳的
		palette.getDarkVibrantColor(int defaultColor);	//鲜艳的暗色
		palette.getLightVibrantColor(int defaultColor);	//鲜艳的亮色
		palette.getMutedColor(int defaultColor);	//柔和的
		palette.getDarkMutedColor(int defaultColor);	//柔和的暗色
		palette.getLightMutedColor(int defaultColor);	//柔和的亮色

### 动画
##### Button 触摸反馈
> - 系统自带
> 
		默认												//背景黑色
		?android:attr/selectableItemBackground			//背景透明
		?android:attr/selectableItemBackgroundBorderless	//圆形透明

> - 自定义
>
		// 沿着中心的缩小的动画
		Animator animator = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2, view.getWidth(), 0);
		animator.setInterpolator(new LinearInterpolator());
		animator.setDuration(1000);
		animator.start();
>		
		// 从左上角扩展的圆形动画
		Animator animator = ViewAnimationUtils.createCircularReveal(view,0,0,0,(float) Math.hypot(view.getWidth(), view.getHeight()));
		animator.setDuration(1000);
		animator.start();

##### 曲线运动
> - 案例代码:
> 
	Path path = new Path();
	path.moveTo(100,100);	//开始位置
	path.quadTo(1000,300,300,700);	//贝塞尔曲线
	ObjectAnimator mAnimator = ObjectAnimator.ofFloat(curved, View.X, View.Y, path);
	Path p = new Path();
	p.lineTo(0.6f, 0.9f);	//画直线
	p.lineTo(0.75f, 0.2f);
	p.lineTo(1f, 1f);
	mAnimator.setInterpolator(new PathInterpolator(p));
	mAnimator.setDuration(3000);
	mAnimator.start();

#### 状态选择器
> - 案例代码:
>
		<selector xmlns:android="http://schemas.android.com/apk/res/android">	//按压时
		    <item android:state_pressed="true">
		        <set>
		            <objectAnimator android:propertyName="translationZ"
		                            android:duration="200"
		                            android:valueTo="20dp"
		                            android:valueType="floatType"/>
		        </set>
		    </item>
		    <item android:state_enabled="true" android:state_pressed="false">	//默认时
		        <set>
		            <objectAnimator android:propertyName="translationZ"
		                            android:duration="200"
		                            android:valueTo="0"
		                            android:valueType="floatType"/>
		        </set>
		    </item>
		</selector>

> 
		// 可以加动画
		<animated-selector xmlns:android="http://schemas.android.com/apk/res/android">
		    <item android:id="@+id/pressed" android:drawable="@drawable/btn_check_15" android:state_pressed="true"/>
		    <item android:id="@+id/normal"  android:drawable="@drawable/btn_check_0"/>
		    <transition android:fromId="@+id/normal" android:toId="@+id/pressed">
		        <animation-list>
		            <item android:duration="20" android:drawable="@drawable/btn_check_0"/>
		            <item android:duration="20" android:drawable="@drawable/btn_check_1"/>
		            <item android:duration="20" android:drawable="@drawable/btn_check_2"/>
		        </animation-list>
		    </transition>
		</animated-selector>

##### 矢量动画
> - 案例代码:
> - drawable --> xxx.xml --> 动画→vector_anim
> 
		<animated-vector xmlns:android="http://schemas.android.com/apk/res/android"
		    android:drawable="@drawable/vector_drawable" >
		    <target
		        android:name="v"
		        android:animation="@anim/vector_anim" />
		</animated-vector>

> - anim --> vector_anim.XML 动画
> 
		<set xmlns:android="http://schemas.android.com/apk/res/android">
		    <objectAnimator
		        android:duration="3000"
		        android:propertyName="pathData"
		        android:valueFrom="M300,70 l 0,-70 70,70 0,0 -70,70z"	//开始位置
		        android:valueTo="M300,70 l 0,-70 70,0  0,140 -70,0z"
		        android:valueType="pathType" />
		</set>

#### 转场动画 
> - 系统自带
>
		Translation translation = new Explode();	//下面上来
		Translation translation = new Fade();	//淡入淡出
		Translation translation = new Slide();	//下往上滑动

>> - 案例代码:
>>
		Translation translation = new Explode();
		Intent intent = new Intent(TransitionsActivity.this, ExploadeActivity.class);
		transition.setDuration(1000);
		getWindow().setEnterTransition(transition);
		getWindow().setExitTransition(transition);
		getWindow().setReturnTransition(transition);
		getWindow().setReenterTransition(transition);
		startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransitionsActivity.this).toBundle());

##### RecyclerView
> - RecyclerView 代替了老版本的 ListView 和 GridView
> - 使用 ListView 初始化 LinearLayoutManager
> - 使用 GridView 初始化 GridLayoutManager
> - CardView 立体效果的View (帧布局的子类)
> - StaggeredGridLayoutManager 瀑布流