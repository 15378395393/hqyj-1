package com.hqyj.chartRoom.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hqyj.chartRoom.controller.Client;

/**
 * @Description: 聊天室客户端界面
 * @author: HymanHu
 * @date: 2019年12月6日
 */
public class ClientUi extends JFrame {
	
	private static final long serialVersionUID = -2862033822313415513L;

	// 总面板
	private JPanel clientPanel = new JPanel();
	
	// 聊天窗口面板
	public static JTextArea chartTextArea = new JTextArea();
    private JScrollPane chatScrollPane = new JScrollPane(chartTextArea);
    
    // 发送面板
    private JPanel sendPanel = new JPanel();
    // ip输入框
    public static JTextField desIpTextField = new JTextField("请输入对方Ip");
    // 昵称输入框
    public static JTextField nameTextField = new JTextField("请输入昵称");
    // 消息输入框
    public static JTextArea messageTextArea = new JTextArea();
    private JScrollPane messageScrollPane = new JScrollPane(messageTextArea);
    // 发送按钮
    private JButton sendButton = new JButton("发送");
    
    // 构造器创建窗体
    public ClientUi () {
    	// 设置 聊天窗口面板 属性
		// 自动换行
		chartTextArea.setLineWrap(true);
		// 不能编辑
		chartTextArea.setEditable(false);
		// 设置最好的大小，根据界面整体的变化而变化
		chatScrollPane.setPreferredSize(new Dimension(550, 400));
		// 总面板 组装 聊天窗口面板
		clientPanel.add(chatScrollPane);
		
		// 设置 发送面板 组件属性
		messageTextArea.setLineWrap(true);
		desIpTextField.setPreferredSize(new Dimension(80, 30));
		nameTextField.setPreferredSize(new Dimension(60, 30));
		messageScrollPane.setPreferredSize(new Dimension(250, 30));
		sendButton.setPreferredSize(new Dimension(100, 30));
		// 发送面板 组装 各类组件
		sendPanel.add(desIpTextField);
		sendPanel.add(nameTextField);
		sendPanel.add(messageScrollPane);
		sendPanel.add(sendButton);
		// 总面板 组装 发送面板
		clientPanel.add(sendPanel);
		
		// 设置窗体属性
		// 设置窗体标题
		this.setTitle("群聊窗口");
		// 设置总面板x、y坐标和长宽
		this.setBounds(300, 100, 600, 500);
		// 是否可由用户调整窗体大小
		this.setResizable(false);
		// 用户点击close关闭窗体
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(clientPanel);
		// 设置容器是否可见，注意，该方法只能放在最后，且一个类中只调用一次
		this.setVisible(true);
		
		// 启动客户端线程
		Client client = new Client();
		client.start();
		
		// 绑定ip输入框焦点事件
		desIpTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				desIpTextField.setText("");
			}
		});
		
		// 绑定用户名焦点事件
		nameTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				nameTextField.setText("");
			}
		});
		
		
		// 绑定发送按钮监听器
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendMessage();
			}
		});
    }
    
    public static void main(String[] args) {
		new ClientUi();
	}
    
}
