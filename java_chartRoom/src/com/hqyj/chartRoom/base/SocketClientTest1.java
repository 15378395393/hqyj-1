package com.hqyj.chartRoom.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Description: socket客户端，双向通道，发送消息并接收消息
 * @author: HymanHu
 * @date: 2019年12月7日
 */
public class SocketClientTest1 {
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socketIn = null;
		Socket socketOut = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String messageOut = null;
		Scanner scanner = null;
		
		try {
			// 创建 serverSocket，监听某端口
			serverSocket = new ServerSocket(9091);
			System.out.println("server开启9091，等待socket进入……");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			// 连接服务器
			socketOut = new Socket("localhost", 9090);
			
			System.out.print("客户端：");
			scanner = new Scanner(System.in);
			messageOut = scanner.next();
			
			// 将输入字符串写入输出流
			bw = new BufferedWriter(new OutputStreamWriter(socketOut.getOutputStream()));
			bw.write(messageOut);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			try {
				if (bw != null) {
					bw.close();
				}
				if (socketOut != null) {
					socketOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// serverSocket没有关闭，可以一直从serverSocket得到socket
		while (true) {
			try {
				socketIn = serverSocket.accept();
				br = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
				String messageIn = br.readLine();
				System.out.println("服务器：" + messageIn);
				
				System.out.print("客户端：");
				scanner = new Scanner(System.in);
				messageOut = scanner.next();
				socketOut = new Socket("localhost", 9090);
				bw = new BufferedWriter(new OutputStreamWriter(socketOut.getOutputStream()));
				bw.write(messageOut);
				bw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭连接
				try {
					if (br != null) {
						br.close();
					}
					if (bw != null) {
						bw.close();
					}
					if (socketIn != null) {
						socketIn.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
