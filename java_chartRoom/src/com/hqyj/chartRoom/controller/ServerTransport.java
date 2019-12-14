package com.hqyj.chartRoom.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.hqyj.chartRoom.util.ChartRoomUtil;

/**
 * @Description: 客户端和服务器建立socket连接后，服务器接收到多个socket，并对之进行处理
 * @author: HymanHu
 * @date: 2019年12月7日
 */
public class ServerTransport extends Thread {
	private Socket socket;
	private String clientIp;
	
	public ServerTransport(Socket socket) {
		this.socket = socket;
		this.clientIp = socket.getInetAddress().getHostAddress();
		// 开启处理socket线程
		this.start();
	}
	
	/* 
	 * 服务器获取信息后，需要将该信息传输回每一个客户端
	 * 处理信息返回功能的是socket对象
	 * 循环遍历服务器端获得的socket list，
	 * 对每一个socket进行消息输出，达到每个面板都显示最新消息的功能
	 */
	@Override
	public void run() {
		BufferedReader br = null;
		// 如果有错误消息，则只对自己的客户端进行错误消息输出
		BufferedWriter ownerBw = null;
		// 如果没有错误消息，则对每个客户端进行消息输出
		BufferedWriter destBw = null;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ownerBw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String lineMessage = null;
			while ((lineMessage = br.readLine()) != null) {
				// 对消息进行拆分判断，该功能展示String常用方法
				String[] temps = lineMessage.split(ChartRoomUtil.SPLIT_CHAR);
				if (temps.length <= 1) {
					ownerBw.write("数据格式错误。");
					ownerBw.newLine();
					ownerBw.flush();
				}
				
				// 获取消息体，并对每个客户端进行输出
				String destIp = temps[0];
				String content = temps[1];
				boolean isOnline = false;
				for (ServerTransport serverTransport : Server.serverTransports) {
					destBw = new BufferedWriter(
							new OutputStreamWriter(serverTransport.socket.getOutputStream()));
					destBw.write(content);
					destBw.newLine();
					destBw.flush();
					isOnline = true;
					// 点对点发送消息，判断ip是否匹配，页面还应添加点对点连接ip输入框
//					if (destIp.equals(serverTransport.clientIp)) {
//					}
				}
				
				// 如果对方不在线，则提示错误消息，适用于点对点传输
//				if (!isOnline) {
//					ownerBw.write("对方不在线。");
//					ownerBw.newLine();
//					ownerBw.flush();
//				}
				
			}
		} catch (IOException e) {
			// 发生IOException，则移除对应socket
			Server.serverTransports.remove(this);
			ChartRoomUtil.releaseSocket(null, socket);
			e.printStackTrace();
			throw new RuntimeException("获取流失败。");
		}
	}
	
}
