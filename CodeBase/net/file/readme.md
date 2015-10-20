# Http网络调试工具的使用
---
## Fiddler2
> http协议调试工具

 - **发送请求**
	> - Composer选项卡 --> 选中请求方式(GET/POST) --> 填写URL --> 添加请求参数(post请求，在Request body中添加请求参数，必须添加请求头 `Content-Type: application/x-www-form-urlencoded` get请求，在URL中添加参数) --> Excute执行

 - **查看返回数据**
	> -  选中左侧发送的请求 --> 选中右侧的Inspectors选项卡 -->  查看下侧的Raw选项卡