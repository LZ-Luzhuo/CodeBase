# 案例
---
### getImage From MediaStore or Camera
> 这是关于从相册/相机获取图片的案例:
>> - 从相册获取图片,并且裁剪图片
>> - 从相机获取图片(不能直接剪裁,需要剪裁请调用剪裁代码)
>> - 剪裁图片

### Button
> 这是自定义按钮的案例:
>> - 获取/失去焦点 的方式
>> - 按钮 的方式

### EditText
> 文本输入框案例:
>> - 自定义了输入框背景
>> - 输入文字显示delete按钮
>> - ![](/EditText/EditText.gif)

### Parcelable
> Parcelable的案例
>> - `public class Bean implements Parcelable { ... }`

### ListView
> ListView的优化
>> - 1.复用convertView;
>> - 2.异步加载网络图片;
>> - 3.快速滑动时不显示图片;
>> - universal-image-loader-1.9.4.jar

### LruCache
> 内存缓存优化和磁盘缓存优化
>> - abc.jpg放在SD卡根目录下
>> - SD卡根目录下的` img2 `文件夹就是该案例的缓存目录
>> - 清理缓存策略全部采用先添加先清除,后添加后清除
>> - 目前有部分手机没有SD卡功能,可能需要改动ImageCache.java里的...代码,考虑没有SD卡时可将目录改为手机自带存储目录(**该部分代码未加,以后可考虑加上**)
>>
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File externalStorageDirectory = Environment.getExternalStorageDirectory();
			String path = externalStorageDirectory.getAbsolutePath() + ConstantValue.IMAGE_PATH;
>>			
			diskLruCache = DiskLruCache.openCache(GloableParams.context, new File(path), DIS_CACHE_SIZE);
		}

### AsyncTask
> AsyncTask 线程池案例

### Load Big Image View
> 加载巨幅图画的案例(非缩小式压缩)
> BitmapRegionDecoder类的使用
>> - qingmingshanghetu.jpg放在assets目录下

### XXXGestureDetector
> 手势类工具
> ###### MoveGestureDetector
> 移动手势类
>> - 手指向左滑,-x;向右滑+x;
>> - 手指向上滑,-y;向下滑+y;