package com.hqyj.chartRoom.util;

import java.net.InetAddress;

/**
 * @Description: Host Util
 * @author: HymanHu
 * @date: 2019年12月6日
 */
public class HostUtil {
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
}
