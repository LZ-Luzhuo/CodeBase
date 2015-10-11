#Mysql
##数据库使用时出现连接不够用

#####连接不够用 Data source rejected establishment of connection,  message from server: "Too many connections"

#####问题解析:
**初步分析:** 设定的并发连接数太少 / 多次insert,update操作没有关闭session / 设定的空闲时间太少

**初步解决:** 

- **1.停止mysql** (拒绝访问:管理员身份运行cmd)

		net stop mysql		//停止

- **2.打开my.ini文件,在 `[mysqld]` 下面添加下面三行**
- 
  	# max_connections修改最大连接数为1000(注意是修改)
	max_connections=1000
	# max_user_connections设置每用户最大连接数为500 
	max_user_connections=500
	# wait_timeout表示200秒后将关闭空闲（IDLE）的连接，但是对正在工作的连接不影响。
	wait_timeout=200
 
- **3.启动mysql**

	net start mysql		//启动

- **4.查看是否修改成功**
- 
	mysqladmin -uroot -p variables
	Password:

	//可以看到以下项说明修改成功
	| max_connections                 | 1000
	| max_user_connections            | 500
	| wait_timeout                    | 200