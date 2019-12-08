package com.hqyj.chartRoom.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hqyj.chartRoom.controller.Server;
import com.hqyj.chartRoom.util.ChartRoomUtil;

/**
 * @Description: 聊天室服务端界面
 * @author: HymanHu
 * @date: 2019年12月6日
 */
public class ServerUi extends JFrame {

	private static final long serialVersionUID = 1L;

	// 总面板
	private JPanel serverPanel = new JPanel();
	
	// 服务器ip
	private JLabel ipLabel = new JLabel("服务器ip:");
    public static JTextField ipTextField = new JTextField(ChartRoomUtil.getIp());
    
    // 服务器端口
    private JLabel portLabel = new JLabel("服务器端口:");
    public static JTextField portTextField = new JTextField(ChartRoomUtil.PORT + "");
    
    // 启动按钮
    public static JButton startButton = new JButton("启动服务器");
    
    // 日志显示面板
    public static JTextArea logTextArea = new JTextArea();
    private JScrollPane logScrollPane = new JScrollPane(logTextArea);
	
	// 无参构造器创建窗体
	public ServerUi () {
		// 设置服务器ip组件属性
		ipLabel.setPreferredSize(new Dimension(100, 30));
		ipTextField.setPreferredSize(new Dimension(150, 30));
		serverPanel.add(ipLabel);
		serverPanel.add(ipTextField);
		
		// 设置服务器端口面板
		portLabel.setPreferredSize(new Dimension(100, 30));
		portTextField.setPreferredSize(new Dimension(150, 30));
		serverPanel.add(portLabel);
		serverPanel.add(portTextField);
		
		// 设置启动按钮
		startButton.setPreferredSize(new Dimension(260, 50));
		serverPanel.add(startButton);
		
		// 设置日志显示面板
		logTextArea.setLineWrap(true);
		logTextArea.setEditable(false);
		logScrollPane.setPreferredSize(new Dimension(260, 200));
		serverPanel.add(logScrollPane);
		
		// 设置窗体属性
		// 设置窗体标题
		this.setTitle("服务器窗口");
		// 设置总面板x、y坐标和长宽
		this.setBounds(100, 50, 300, 400);
		// 是否可由用户调整窗体大小
		this.setResizable(false);
		// 用户点击close关闭窗体
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(serverPanel);
		// 设置容器是否可见，注意，该方法只能放在最后，且一个类中只调用一次
		this.setVisible(true);
		
		// 添加启动按钮监听器，启动server
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Server().start();
			}
		});
	}
	
	public static void main(String[] args) {
		new ServerUi();
	}
}
