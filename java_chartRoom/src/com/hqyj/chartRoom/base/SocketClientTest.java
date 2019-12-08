package com.hqyj.chartRoom.base;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Description: socket客户端，用户控制台输入，传入服务器
 * @author: HymanHu
 * @date: 2019年12月7日
 */
public class SocketClientTest {

	// java1.5特性，获取用户控制台输入
	private static Scanner scanner;

	public static void main(String[] args) {
		Socket socket = null;
		try {
			// 连接服务器
			socket = new Socket("127.0.0.1", 9090);
			
			System.out.println("请输入信息：");
			scanner = new Scanner(System.in);
			String message = scanner.next();
			
			// 将输入字符串写入输出流
			socket.getOutputStream().write(message.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭socket
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
