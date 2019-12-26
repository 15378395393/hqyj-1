package com.hqyj.javaDemo.socket.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Request {

	private InputStream is;
	private String uri;

	public Request(InputStream is) {
		this.is = is;
	}
	
	/**
	 * 解析请求信息，找到uri字符串
	 * GET /index.txt HTTP/1.1
	 * Host: 127.0.0.1:9999
	 * User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0
	 * ...
	 */
	public void parse() {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String line = null;
			while((line = br.readLine()) != null) {
				if (line.length() > 0) {
					sb.append(line).append("\r\n");
				} else {
					break;
				}
			}
			
			System.out.println("request info: ");
			System.out.println(sb.toString());
			
			uri = parseUri(sb.toString());
			System.out.println("uri: " + uri);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 解析Request请求信息，获取uri
	private String parseUri(String requestString) {
		int index1, index2;
		index1 = requestString.indexOf(' ');
		if (index1 != -1) {
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1)
				return requestString.substring(index1 + 1, index2);
		}
		return null;
	}

	public String getUri() {
		return uri;
	}
}
