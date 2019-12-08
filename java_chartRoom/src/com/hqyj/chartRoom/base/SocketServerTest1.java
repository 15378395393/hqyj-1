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
 * @Description: socket服务器，双向通道，接受消息并发送消息
 * @author: HymanHu
 * @date: 2019年12月7日
 */
public class SocketServerTest1 {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socketIn = null;
		Socket socketOut = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		Scanner scanner = null;
		
		try {
			// 创建 serverSocket，监听某端口
			serverSocket = new ServerSocket(9090);
			System.out.println("server开启9090，等待socket进入……");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// serverSocket没有关闭，可以一直从serverSocket得到socket
		while (true) {
			try {
				socketIn = serverSocket.accept();
				br = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
				String messageIn = br.readLine();
				System.out.println("客户端：" + messageIn);
				
				System.out.print("服务器：");
				scanner = new Scanner(System.in);
				String messageOut = scanner.next();
				socketOut = new Socket("localhost", 9091);
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
					if (socketOut != null) {
						socketIn.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
