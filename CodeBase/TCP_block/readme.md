# 阻塞式长连接TCP #
---
> 阻塞式长连接TCP,在子线程中进行阻塞,效率低,适合少量用户的链接.如果用户量大,可以考虑使用mina的nio(非阻塞).


**客户端**
> - MainActivity.java     //使用
> - GlobalParams.java     //需要改变的参数
> 
> - client                //客户端
>> - Connector.java       //连接者,不是给用户使用的
>> - ConnectorManager.java//用户只需调用getInstance().putRequest(request),其他由服务代用
>
>> - receiver             //广播
>>> - PushReceiver.java   //对接收到的数据进行广播,该广播采用动态注册
>
>> - request              //请求,这里指定请求的一些规则,并实现Request接口
>> - service              //服务
>>> - CoreService.java    //后台处理连接,解析数据,广播数据的活动的服务


**服务器**
> TCPServer.java