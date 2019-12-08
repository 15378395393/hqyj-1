package com.hqyj.chartRoom.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.hqyj.chartRoom.ui.ClientUi;
import com.hqyj.chartRoom.ui.ServerUi;
import com.hqyj.chartRoom.util.ChartRoomUtil;

/**
 * @Description: 聊天室客户端，向服务器发送信息，接受服务器返回的信息
 * @author: HymanHu
 * @date: 2019年12月7日
 */
public class Client extends Thread {
	private Socket socket;

	/**
	 * --线程启动后，和服务器建立socket连接，监控服务端返回的信息内容，并将之展示在聊天窗口区
	 */
	@Override
	public void run() {
		BufferedReader br = null;
		try {
			System.out.println("接收服务器信息……");
			
			// 根据ip和端口初始化socket
			socket = new Socket(ServerUi.ipTextField.getText().trim().toLowerCase(), 
					Integer.parseInt(ServerUi.portTextField.getText().trim()));
			
			// 从socket中获取输入流，装载到bufferedReader中
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			// 将从socket中获取的内容，按行输出到客户端聊天窗口面板
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("----------" + line);
				ClientUi.chartTextArea.append(line + ChartRoomUtil.NEW_LINE);
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException("连接失败！");
		} finally {
			ChartRoomUtil.closeStream(br, null, null);
		}
	}
	
	/**
	 * --点击客户端发送按钮，启动线程，发送消息到服务器端
	 */
	public void sendMessage() {
		System.out.println("客户端发送消息……");
		
		Thread sendThread = new Thread() {
			
			@Override
			public void run() {
				BufferedWriter bw = null;
				try {
					// 从socket中获取输出流，装载到bufferedWriter中
					bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					
					// 获取用户输入的聊天内容
					String message = ClientUi.desIpTextField.getText().trim() + ChartRoomUtil.SPLIT_CHAR 
							+ ClientUi.nameTextField.getText().trim() + ": " 
							+ ClientUi.messageTextArea.getText().trim();
					
					// 将用户输入的信息写入socket中
					bw.write(message);
					bw.newLine();
					bw.flush();
					
					ClientUi.messageTextArea.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		sendThread.start();
	}
	
}
