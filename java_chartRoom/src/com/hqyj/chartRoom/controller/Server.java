package com.hqyj.chartRoom.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.hqyj.chartRoom.ui.ServerUi;
import com.hqyj.chartRoom.util.ChartRoomUtil;

public class Server extends Thread {
	private ServerSocket serverSocket;
	public static List<ServerTransport> serverTransports = new ArrayList<ServerTransport>();
	
	@Override
	public void run() {
		System.out.println("启动服务器……");
		try {
			// 创建server Socket
			serverSocket = new ServerSocket(Integer.parseInt(ServerUi.portTextField.getText().trim()));
			
			// 输出启动服务器信息
			ServerUi.startButton.setText("服务器已经启动。");
			ServerUi.startButton.setEnabled(false);
			ServerUi.logTextArea.append("服务器启动成功。" + ChartRoomUtil.NEW_LINE);
			
			// 启动acceptMessage方法线程
			acceptMessage();
		} catch (NumberFormatException e1) {
			ChartRoomUtil.releaseSocket(serverSocket, null);
			e1.printStackTrace();
			throw new RuntimeException("端口输入错误。");
		} catch (IOException e1) {
			ChartRoomUtil.releaseSocket(serverSocket, null);
			e1.printStackTrace();
			throw new RuntimeException("服务器端口已被占用。");
		}
	}
	
	/**
	 * --服务器端接受消息的方法
	 */
	public void acceptMessage () {
		System.out.println("接受客户端信息，并返回给客户端……");
		
		Thread acceptThread = new Thread() {

			private Socket socket;
			
			@Override
			public void run() {
				// 服务器计数功能
				int clientAccount = 0;
				while (clientAccount <= ChartRoomUtil.MAX_NUMBER) {
					try {
						// 从serverSocket中获取socket信息
						socket = serverSocket.accept();
						
						// 输出客户端连接信息
						clientAccount ++;
						String serverMessage = "第" + clientAccount + "个客户端连接成功！连接信息：" 
								+ socket.getInetAddress().getHostAddress() + ": " 
								+ socket.getPort() + ChartRoomUtil.NEW_LINE;
						ServerUi.logTextArea.append(serverMessage);
						
						/*
						 * -- 将每个客户端的socket对象包装到ServerTransport中
						 * -- 对每个socket的处理，在ServerTransport中实现
						 */
						serverTransports.add(new ServerTransport(socket));
					} catch (IOException e) {
						ChartRoomUtil.releaseSocket(serverSocket, socket);
						e.printStackTrace();
						throw new RuntimeException("客户端连接失败。");
					}
				}
				
				// 超出连接人数，输出错误信息
				String serverMessage = "服务器超出负荷。" + ChartRoomUtil.NEW_LINE;
				ServerUi.logTextArea.append(serverMessage);
			}
			
		};
		
		acceptThread.start();
	}

}
