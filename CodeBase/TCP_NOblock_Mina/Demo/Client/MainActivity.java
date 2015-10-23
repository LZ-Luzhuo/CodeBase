package com.example.minatcp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et = (EditText) findViewById(R.id.et);
	}
	
	
	IoSession session = null; 
	public void clickMessage(View v) {
		final String content = et.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			return;
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				String host = "192.168.0.100";
				int port = 10086;
				//连接器
				NioSocketConnector connector = new NioSocketConnector();
				// 继承IoHandler接口的实现类IoHandlerAdapter
				connector.setHandler(new IoHandlerAdapter(){

					@Override
					public void messageReceived(IoSession session,
							Object message) throws Exception {
						super.messageReceived(session, message);
						
						Log.i("mina","Received:"+ message);
					}

					@Override
					public void messageSent(IoSession session, Object message)
							throws Exception {
						super.messageSent(session, message);
						
						Log.i("mina", "Sent:"+message);
					}
					
				});
				
				// 添加Log(必须 )
				DefaultIoFilterChainBuilder chain = connector.getFilterChain();
				chain.addLast("logger", new LoggingFilter());
				chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
				
				ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
				future.awaitUninterruptibly(); //尝试连接,阻塞式,成功后跳过
				session = future.getSession();
				// 发送内容
				session.write(content);
				// 断开连接
				//session.close(true);
				
			}
		}).start();

	}
	
}
