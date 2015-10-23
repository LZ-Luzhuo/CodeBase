package test;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-23 上午12:23:28
 * 
 * 描述:阻塞式长连接(仅适用于少量用户)案例的服务端.
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 */
public class TCPServer {
	// 端口
	final static int port = 10086;

	// 存放客户端的容器
	private static Map<String, Socket> clients = new LinkedHashMap<String, Socket>();

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(port);

			while (true) {

				// 获得客户端连接
				// 阻塞式方法
				System.out.println("准备接收数据");
				final Socket client = server.accept();

				// 将客户端添加到 容器中
				// clients.add(client);

				// 将读取信息放到子线程中,这样多个手机连接进来就不会阻塞
				new Thread(new Runnable() {
					public void run() {
						try {
							// 输入流，为了获取客户端发送的数据
							InputStream is = client.getInputStream();
							// 输出流,给客户端写数据
							OutputStream os = client.getOutputStream();

							byte[] buffer = new byte[1024];
							int len = -1;
							while ((len = is.read(buffer)) != -1) {
								System.out.println("数据接收完成");

								String text = new String(buffer, 0, len);
								System.out.println(text);

								//Json数据转为Map集合
								Map<String, String> map = new Gson().fromJson(text,new TypeToken<Map<String, String>>() {}.getType());

								//以下是对接收到的数据解析后的处理----------//
								String type = map.get("type");
								// 请求type=request
								if ("request".equals(type)) {
									String action = map.get("action");

									// 认证消息action=auth
									if ("auth".equals(action)) {
										// 认证消息处理
										String sender = map.get("sender");
										System.out.println(sender + "认证");
										
										// 将发送者和对应的client放到容器当中
										clients.put(sender, client);
										
									// 文本消息action=text
									} else if ("text".equals(action)) {
										// 文本消息
										String sender = map.get("sender");
										String receiver = map.get("receiver");
										String content = map.get("content");
										
										// 从容器中根据接收者获取对应的client
										Socket s = clients.get(receiver);
										if (s != null) {
											// 如果存在,则表示在线,就把接收到的内容转发给他
											OutputStream output = s.getOutputStream();
											output.write(content.getBytes());
										} else {
											// TODO 不存在表示离线 ,将信息存起来,在线时在发送
										}
									}
									
								// 处理请求的其他type
								} else {

								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
