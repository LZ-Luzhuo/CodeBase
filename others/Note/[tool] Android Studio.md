## Android Studio
---
> 要求:JDK7以及以上
> 32位和64位是同一个文件

### 安装:
> - ![1](/Resource/AndroidStudio/as_1.png)  
>  全选
> - ![2](/Resource/AndroidStudio/as_2.png)  
> 选择Android Studio和 Android SDK 的安装目录。
> - ![3](/Resource/AndroidStudio/as_3.png)  
> 如果你在 [1] 中勾选了 HAXM ，就会出现这一步。配置8G。
> - ![4](/Resource/AndroidStudio/as_4.png)  
> Android Studio的运行需要 VC++ 环境，Android Studio安装的过程中，会自动安装。这也是为什么建议使用安装包（exe）的原因。
> - ![5](/Resource/AndroidStudio/as_5.png)  
> 你就看到这个界面。说明你的Android Studio已经安装成功了。

### 运行:
> - 准备:安装 JDK 并配置 JDK 环境变量(请使用传统的 `JAVA_HOME` 环境变量名称。)  
> - ![6](/Resource/AndroidStudio/as_6.png)  
> 第一个选项 ：使用以前版本的配置文件夹。
  第二个选项 ：导入某一个目录下的配置文件夹。
  第三个选项 ：不导入配置文件夹。(**第一次使用**)  
> - ![7](/Resource/AndroidStudio/as_7.png)  
> 这是在检查你的 Android SDK 。(**需要联网**)(不想检查:在Android Studio安装目录下的 bin 目录下，找到 idea.properties 文件，在文件最后追加 `disable.android.first.run=true` 。)
> - ![8](/Resource/AndroidStudio/as_8.png)  
> 能看到这个界面，说明你需要更新你的 Android SDK 
> - ![9](/Resource/AndroidStudio/as_9.png)  
> 选择安装更新 Android SDK 。第一个选项表示全选，第二个表示自定义。
> - ![10](/Resource/AndroidStudio/as_10.png)  
> 如果你 [9] 中选择第一个选项的话，会显示这个界面。选择 Accept 点击 Finish 进行安装即可。  
> - ![11](/Resource/AndroidStudio/as_11.png)  
> 如果你 [9] 中选择第二个选项的话，会显示这个界面。需要你选择一个安装目录，需要注意的是： 这个目录中不能包含空格以及汉字。不建议使用默认的%APPDATA%目录 。点击 next 后可以看到类似 [10] 的页面，选择 Accept 点击 Finish 进行安装。
> - ![12](/Resource/AndroidStudio/as_12.png)  
> 当你更新完 Android SDK ，你就会看到这个界面。直到这个界面才说明，你可以使用Android Studio了。
> 
		选项1 ： 创建一个Android Studio项目。
		选项2 ： 打开一个Android Studio项目。
		选项3 ： 导入官方样例，会从网络上下载代码。此功能在以前的测试版本中是没有的，建议多看一看官方给的范例。
		选项4 ： 从版本控制系统中导入代码。支持 CVS 、 SVN 、 Git 、 Mercurial ， 甚至GitHub。
		选项5 ： 导入非Android Studio项目。比如纯生的 Eclipse Android项目， IDEA Android项目。如果你的 Eclipse 项目使用官方建议导出（即使用 Generate Gradle build files 的方式导出），建议使用 选项2 导入。
		选项6 ： 设置。
		选项7 ： 帮助文档。

> 如果一些选项不能点击，说明你的 JDK 或者 Android SDK 目录指向有问题，请看下面的 设置 **JDK 或者 Android SDK目录** 。

### 其他:
##### 设置 JDK 或者 Android SDK 目录
> - 方法1:选择 Configure --> Project Defaults --> Project Structure
> - 方法2:选择 File --> Other Settings --> Default Project Structure

##### 导入eclipse项目:
> - 将eclipse里的项目粘到桌面
> - as中:File --> Import Moudule... --> 选择桌面的项目 --> Next --> Finish

##### 导入库:
> - as中:File --> Import Moudule... --> 选择库 --> Next --> Finish

##### 注意细节(Setting):
> - Code Style:代码样式,尽量不要改
> - Compiler:√ Make project automatically(自动编译)
> - File Encodings:编码改成utf-8 (3处)
> - Gradle:配置 Gradle home 路径;√ Offine work
> - Tasks:可以修改 Connection timeout (模拟器连接时间)
> - Version Control(版本控制):svn(自带);git(自己装,装好选择路径)
> - Appearance:√ Override default fonts by (not recommended)(勾上不会出现口口口了)
> - Editor:√ Show quick doc on mouse move Delay
> - Editor:Appearance:勾上想显示的提示
> - Editor:Colors & Fonts:Font:修改编码字体(Consolas)/大小 (要保存成自己的样式才能改)
> - Editor:Code Completion:修改Case sensitive completion 为 None (提示不区分大小写)
> - Editor:Auto Import:√ Add unambiguous imports on the fly (自动导包)
> - File and Code Templates:Includes --> File Header (设置头注释)
> - Keymap(快捷键):最好使用默认的
> - Plugins(插件):Browse repositories...(网络装插件);Install plugin from disk...(安装本地插件)
> - 一个Project最好只好一个项目

##### 删除项目:
> - File --> project Structure... --> 选择项目 --> - (移除项目)
> - 切换Project --> 右键项目 --> delete (删除项目(物理))

#### project Structure:
> - Compile Sdk Version:编译版本
> - Signing:签名
> - Flavors:打包
> - Dependencies(关联库):+ 1.网络 2.libs里的jar包 3.关联库

#### 工具栏:
> - Build:Clean Project:clean项目
> - Build:Generate Signed APK...:签名
> - VCS:Brows VCS Repository:版本控制

### Android Studio
#### 目录结构:
> - .idea
> - app
>
		build:outputs:apk:生成的apk位置
		libs:jar包
		src:androidTest:java:ApplicationTest:自动生成的测试类,可以直接用
		   :main:java:代码
			    :res:资源目录
					:drawable:
					:layout:
					:menu:
					:mipmap_hdpi:图片资源位置
					:values:
		build.gradle:依赖管理工具
				
> - build
> - gradle

#### 常用快捷键
> - Ctrl+Alt+space:提示代码 (*Alt+/*)
> - Alt+Enter:补充代码 (*Ctrl+1+选*)
> - Ctrl+p:提示代码
> - Ctrl+d:复制行
> - Ctrl+y:删除行 (*Ctrl+D*)
> - Ctrl+Alr+o:导包 (*Ctrl+Shift+o*)
> - Alt+Insert 获取get/set/构造/toString   (*Ctrl+Alt+s*)
> - sout:打印 (*syso*)
> - F8:调试
> - Ctrl+Alt+f:变成成员变量
> - Ctrl+Alt+l:格式化
> - Ctrl+Shift+u:大小写
