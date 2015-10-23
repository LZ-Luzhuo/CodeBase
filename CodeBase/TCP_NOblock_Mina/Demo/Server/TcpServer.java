package com.demo.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-22 下午8:53:57
 * 
 * 描述:MinaTCP服务端,非阻塞式,效率高
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class TcpServer {
	public static void main(String[] args) {
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		try {
			//可以不实现IoHandler接口，而是继承它的实现类IoHandlerAdapter，根据具体需求覆盖其中的几个方法就可以
			acceptor.setHandler(new IoHandler() {

				@Override
				public void sessionOpened(IoSession session) throws Exception {
					// 当一个新的连接建立时，由I/O processor thread调用
					System.out.println("open");
				}
				
				@Override
				public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
					// 当连接进入空闲状态时调用
					
				}
				
				@Override
				public void sessionCreated(IoSession arg0) throws Exception {
					// 当连接打开是调用
					System.out.println("create");
					
				}
				
				@Override
				public void sessionClosed(IoSession arg0) throws Exception {
					// 当连接关闭时调用
					System.out.println("close");
				}
				
				@Override
				public void messageSent(IoSession session, Object content) throws Exception {
					// 当一个消息被(IoSession#write)发送出去后调用
					
					// IoSession 指的是客户端的session
					// 服务器写发送信息
					//session.write("服务器发送的内容");
					
					System.out.println("Sent:"+content);
				}
				
				@Override
				public void messageReceived(IoSession session, Object content) throws Exception {
					// 当接收了一个消息时调用
					System.out.println("Received:"+content);
					
					session.write("ok");
				}
				
				@Override
				public void exceptionCaught(IoSession arg0, Throwable arg1)
						throws Exception {
					// 当实现IoHandler的类抛出异常时调用
					
				}

				@Override
				public void inputClosed(IoSession arg0) throws Exception {
					// TODO Auto-generated method stub
					
				}
			});
			
			
			// 添加Log(必须 )
			DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
			chain.addLast("logger", new LoggingFilter());
			chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
			
			acceptor.bind(new InetSocketAddress(10086));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
