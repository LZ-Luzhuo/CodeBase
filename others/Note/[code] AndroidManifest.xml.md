# AndroidManifest.xml
---
## `<application />`
 - `android:name="com.example.chat.ChatApplication"`
	> - 默认是 `android.app.Application`
	> - 自定义:`public class ChatApplication extends Application {}`
	> - 这样:当应用程序退出后其生命周期也跟着结束

## `<activity />`
 - `android:launchMode="singleInstance"`
	> - 启动模式

 - `android:excludeFromRecents="true"`
	> - true:不在最近任务列表中记录