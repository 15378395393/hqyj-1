package com.hqyj.chartRoom.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.hqyj.chartRoom.util.HostUtil;

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
		this.start();
	}
	
	@Override
	public void run() {
		BufferedReader br = null;
		BufferedWriter ownerBw = null;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ownerBw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String lineMessage = null;
			while ((lineMessage = br.readLine()) != null) {
				String[] temps = lineMessage.split(HostUtil.SPLIT_CHAR);
				if (temps.length <= 1) {
					ownerBw.write("数据格式错误。");
					ownerBw.newLine();
					ownerBw.flush();
				}
				
				String destIp = temps[0];
				String content = temps[1];
				boolean isOnline = false;
				BufferedWriter destBw = null;
				for (ServerTransport serverTransport : Server.serverTransports) {
					if (destIp.equals(serverTransport.clientIp)) {
						destBw = new BufferedWriter(
								new OutputStreamWriter(serverTransport.socket.getOutputStream()));
						destBw.write(content);
						destBw.newLine();
						destBw.flush();
						isOnline = true;
					}
				}
				
				if (!isOnline) {
					ownerBw.write("对方不在线。");
					ownerBw.newLine();
					ownerBw.flush();
				}
				
			}
		} catch (IOException e) {
			Server.serverTransports.remove(this);
			e.printStackTrace();
			throw new RuntimeException("获取流失败。");
		}
	}
	
}
