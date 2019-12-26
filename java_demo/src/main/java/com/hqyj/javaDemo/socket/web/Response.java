package com.hqyj.javaDemo.socket.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Response {
	private Request request;
	private OutputStream output;

	public Response(Request request, OutputStream output) {
		this.request = request;
		this.output = output;
	}

	// 流输出静态网页内容
	public void sendStaticResource() {
		FileInputStream fis = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			File file = new File(HttpServer.WEB_ROOT, request.getUri());
			if (file.exists()) {
					fis = new FileInputStream(file);
				br = new BufferedReader(new InputStreamReader(fis));
				bw = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
				
				// 写响应头，设置Content-Type: text/html
				String responseHeader = "HTTP/1.1 200\r\n" 
						+ "Content-Type: text/html\r\n";
				bw.write(responseHeader);
				bw.newLine();
				bw.flush();
				
				// 写网页内容
				String line = null;
				while ((line = br.readLine()) != null) {
					System.out.println("----" + line);
					bw.write(line);
					bw.newLine();
					bw.flush();
				}
			} else {
				// file not found
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
						+ "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (br != null) {
					br.close();
				}
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
