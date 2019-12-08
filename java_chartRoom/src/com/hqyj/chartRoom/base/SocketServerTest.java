package com.hqyj.chartRoom.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: socket服务器端，接受客户端消息，打印到控制台
 * @author: HymanHu
 * @date: 2019年12月7日
 */
public class SocketServerTest {
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		BufferedReader br = null;
		try {
			// 创建 serverSocket，监听某端口
			serverSocket = new ServerSocket(9090);
			System.out.println("server开启，等待socket进入……");
			
			// 在serverSocket中监听客户端套接字，返回socket对象
			socket = serverSocket.accept();
			
			// 从socket中获取输入流，写入bufferedReader中，得到信息，并打印到控制器
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = br.readLine();
			System.out.println("客户端传来消息：" + message);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			try {
				if (br != null) {
					br.close();
				}
				if (socket != null) {
					socket.close();
				}
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
