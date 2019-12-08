package com.hqyj.chartRoom.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: Chart Room Util
 * @author: HymanHu
 * @date: 2019年12月6日
 */
public class ChartRoomUtil {
	// 换行常量
	public final static String NEW_LINE = "\r\n";
	public final static String SPLIT_CHAR = "@";
	// 服务器默认启动端口
	public final static int PORT = 10086;
	// 服务器最大连接数
	public final static int MAX_NUMBER = 50;

	// 获取当前机器ip
	public static String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * --关闭socket
	 * @param serverSocket
	 * @param socket
	 */
	public static void releaseSocket(
			ServerSocket serverSocket, Socket socket) {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * --关闭流
	 * @param br
	 * @param ownerBw
	 * @param destBw
	 */
	public static void closeStream(BufferedReader br, 
			BufferedWriter ownerBw, BufferedWriter destBw) {
		try {
			if (br != null) {
				br.close();
			}
			if (ownerBw != null) {
				ownerBw.close();
			}
			if (destBw != null) {
				destBw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
