package com.hqyj.javaDemo.socket.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket 模拟 http server
 * 启动serverSocket监听，获取请求Uri，去webRoot文件夹下请求查找静态资源文件，使用流输出到网页端
 * http://127.0.0.1:9999/index.html
 * http://127.0.0.1:9999/index.txt
 * @author: HymanHu
 * @date: 2019年12月26日
 */
public class HttpServer {

	/**
	 * -项目根目录下webRoot文件夹，放置需要显示的页面文件，txt格式
	 * http://127.0.0.1:9999/index.html
	 */
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webRoot";
	public static final int SERVER_SOCKET_PORT = 9999;
	
	// shutdown command
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	
	/**
	 * 启动serverSocket监听
	 */
	public void await() throws IOException {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(SERVER_SOCKET_PORT, 1, InetAddress.getByName("127.0.0.1"));
			System.out.println("Start server: " + 
					InetAddress.getByName("127.0.0.1") + ":" + SERVER_SOCKET_PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		while (!shutdown) {
			final Socket socket = ss.accept();
			new Thread() {
				
				@Override
				public void run() {
					InputStream is = null;
					OutputStream os =  null;
					try {
						is = socket.getInputStream();
						os = socket.getOutputStream();
						
						// 获取请求信息，解析uri
						Request request = new Request(is);
						request.parse();
						
						// 使用流输出静态资源文件内容
						Response response = new Response(request, os);
						response.sendStaticResource();
						
						shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							// 在此关闭socket，里面的输入输出流已经拿到，传递到request、response对象，不能关闭流
							if (socket != null) {
								socket.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
	}
	
	public static void main(String[] args) throws IOException {
		HttpServer hs = new HttpServer();
		hs.await();
	}
}
