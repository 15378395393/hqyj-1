package com.hqyj.chartRoom.ui;

import java.awt.Color;
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

public class UiTest extends JFrame {

private static final long serialVersionUID = -2862033822313415513L;
	
	// 总面板
	private JPanel clientPanel = new JPanel();
	
	// 聊天窗口面板
	public static JTextArea chartTextArea = new JTextArea();
	public static JTextArea nameArea = new JTextArea();
    private JScrollPane chatScrollPane = new JScrollPane(chartTextArea);
    
    // ip and port面板
    private JPanel ipAndPortPanel = new JPanel();
    // ip输入框
    public static JTextField desIpTextField = new JTextField("服务器Ip");
    // port输入框
    public static JTextField desPortTextField = new JTextField("服务器Port");
    // 连接按钮
    private JButton connectButton = new JButton("连接");
    //忽视按钮
    private JButton noButton = new JButton("忽视");
    //忽视按钮
    private JButton colorButton = new JButton("背景");
    
    // 发送面板
    private JPanel sendPanel = new JPanel();
    // 昵称输入框
    public static JTextField nameTextField = new JTextField("请输入昵称");
    // 消息输入框
    public static JTextArea messageTextArea = new JTextArea();
    private JScrollPane messageScrollPane = new JScrollPane(messageTextArea);
    // 发送按钮
    private JButton sendButton = new JButton("发送");
    //顏色按鈕
    private JPanel colorbuttonPanel = new JPanel();
    private JButton yellowButton = new JButton("yellow");
    private JButton blueButton = new JButton("blue");
    private JButton redButton=new JButton("red");
    
    // 构造器创建窗体
    public UiTest () {
    	// 设置 聊天窗口面板 属性
		// 自动换行
		chartTextArea.setLineWrap(true);
		nameArea.setLineWrap(true);
		// 不能编辑
		chartTextArea.setEditable(false);
		nameArea.setEditable(false);
		// 设置最好的大小，根据界面整体的变化而变化
		chatScrollPane.setPreferredSize(new Dimension(550, 400));
		// 总面板 组装 聊天窗口面板
		clientPanel.add(chatScrollPane);
		
		// 设置连接按钮
		desIpTextField.setPreferredSize(new Dimension(80, 30));
		desPortTextField.setPreferredSize(new Dimension(80, 30));
		connectButton.setPreferredSize(new Dimension(100, 30));
		noButton.setPreferredSize(new Dimension(100, 30));
		//colorbuttonPanel.setPreferredSize(new Dimension(100, 30));
		yellowButton.setPreferredSize(new Dimension(100, 30));
		blueButton.setPreferredSize(new Dimension(100, 30));
		redButton.setPreferredSize(new Dimension(100, 30));
		ipAndPortPanel.add(desIpTextField);
		ipAndPortPanel.add(desPortTextField);
		ipAndPortPanel.add(connectButton);
		ipAndPortPanel.add(noButton);
		colorbuttonPanel.add(yellowButton);
		colorbuttonPanel.add(blueButton);
		colorbuttonPanel.add(redButton);
		//ipAndPortPanel.add(colorButton);
		// 总面板 组装 发送面板
		clientPanel.add(ipAndPortPanel);
		clientPanel.add(colorbuttonPanel);
		
		// 设置 发送面板 组件属性
		messageTextArea.setLineWrap(true);
		nameTextField.setPreferredSize(new Dimension(60, 30));
		messageScrollPane.setPreferredSize(new Dimension(250, 30));
		sendButton.setPreferredSize(new Dimension(100, 30));
		sendButton.setEnabled(false);
		noButton.setEnabled(false);
		//colorButton.setEnabled(false);
		// 发送面板 组装 各类组件
		sendPanel.add(nameTextField);
		sendPanel.add(messageScrollPane);
		sendPanel.add(sendButton);
		// 总面板 组装 发送面板
		clientPanel.add(sendPanel);
				
		// 设置窗体属性
		// 设置窗体标题
		this.setTitle("群聊窗口");
		// 设置总面板x、y坐标和长宽
		this.setBounds(300, 100, 600, 600);
		// 是否可由用户调整窗体大小
		this.setResizable(false);
		// 用户点击close关闭窗体
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(clientPanel);
		// 设置容器是否可见，注意，该方法只能放在最后，且一个类中只调用一次
		this.setVisible(true);
		
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
		
		desPortTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				desPortTextField.setText("");
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
		
		// 绑定连接按钮事件
		connectButton.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent e) {
				sendButton.setEnabled(true);
				noButton.setEnabled(true);
			}
		});
		
		//绑定忽视按钮事件
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==noButton) {
					chartTextArea.setText("");
				}
				
			}
		});
		
		//绑定背景颜色按钮事件
		colorButton.addActionListener(new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 Object source=e.getSource();
				 if(source==yellowButton) {
						colorbuttonPanel.setBackground(Color.yellow);
					}else if(source==blueButton) {
						colorbuttonPanel.setBackground(Color.blue);
					}else if(source==redButton){
						colorbuttonPanel.setBackground(Color.red);
					}	
			 }
		 });
		
		// 绑定发送按钮监听器
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
    }
    
    public static void main(String[] args) {
		new UiTest();
	}
}
