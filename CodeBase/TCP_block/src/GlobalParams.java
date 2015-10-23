package com.example.tcp;


public interface GlobalParams {
	// 当前用户
	String sender = "A";
	// 用户标志
	String token = "A";
	//接收信息的用户
	String receiver = "A";
	// TCP连接主机地址
	final String dstName = "192.168.0.100";
	// TCP连接端口
	final int dstPort = 10086;
}
