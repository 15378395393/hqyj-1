package com.hqyj.chartRoom.base;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MyQQ extends JFrame {
	// 声明一个AWT中的Container对象，作为该类的属性
	private Container co;
	// 将需要运用的组建定义为属性
	private JLabel jl0, jl1, jl2, jl3, jl4, jl5;
	private JButton jb, jb1, jb2;
	private JTextField jt;
	private JPasswordField jt1;
	private JComboBox jc;
	private JCheckBox jcb1, jcb2;

	// 窗体调整方法
	public void QQFrame() {
		this.setTitle("QQ用户登陆");
		this.setSize(330, 250);
		// 返回用户在此窗体上发起 "close" 时执行的操作
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 从Component继承的方法，指定窗体左上角的相对坐标
//			this.setLocation(480, 284);
		// 设置此窗口相对于指定组件的位置,如果此组件当前未显示，或者 为 null，窗口位于屏幕的中央，GUI的跨平台
		this.setLocationRelativeTo(null);
		// 从Frame继承的方法，设置此 frame 是否可由用户调整大小
		this.setResizable(false);
		// 设置此 frame 要显示在最小化图标中的图像
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image ima = tk.createImage("D:\\lovowork\\lesson10\\84.gif");
		this.setIconImage(ima);
		// 添加背景颜色
		this.setBackground(Color.BLUE);
		// 调用content方法
		this.content();
		// 设置容器是否可见，注意，该方法只能放在最后，且一个类中只调用一次
		this.setVisible(true);
	}

	// 得到窗体内容面板的方法
	public void content() {
		// 返回此窗体的 contentPane 对象,赋给全局co对象
		co = this.getContentPane();
		// 默认的边界布局管理器将窗体分成5份，当只有一个组件时候，无论如何设置其大小，都自动撑大，股将管理器设置为null
		co.setLayout(null);

		// 利用JLabel方法（短文本字符串或图像显示区）添加图片
		jl0 = new JLabel();
		ImageIcon ima = new ImageIcon("D:\\lovowork\\lesson10\\denglu.jpg");
		jl0.setIcon(ima);
		jl0.setBounds(0, 0, 400, 50);
		co.add(jl0);

		jl1 = new JLabel("QQ账号");
		jl1.setBounds(50, 80, 60, 26);
		co.add(jl1);

		// 添加下拉菜单
		Object[] ob = { "898899721", "286097986", "191990575" };
		jc = new JComboBox(ob);
		jc.setBounds(110, 80, 130, 25);
		co.add(jc);

		jl2 = new JLabel("申请账号");
		jl2.setBounds(245, 80, 60, 26);
		co.add(jl2);

		jl3 = new JLabel("输入密码");
		jl3.setBounds(50, 120, 60, 26);
		co.add(jl3);

		// 设置密码框
		jt1 = new JPasswordField();
		jt1.setBounds(110, 120, 130, 25);
		co.add(jt1);

		jl4 = new JLabel("忘记密码");
		jl4.setBounds(245, 120, 60, 26);
		co.add(jl4);

		jl5 = new JLabel("状    态");
		jl5.setBounds(60, 150, 60, 26);
		co.add(jl5);

		// 设置复选框
		jcb1 = new JCheckBox("隐身");
		jcb1.setBounds(120, 150, 60, 26);
		co.add(jcb1);

		jcb2 = new JCheckBox("自动登录");
		jcb2.setBounds(180, 150, 100, 26);
		co.add(jcb2);

		// 设置按钮
		jb1 = new JButton("查杀木马");
		jb1.setBounds(20, 180, 100, 26);
		co.add(jb1);
		jb2 = new JButton("设     置");
		jb2.setBounds(130, 180, 80, 26);
		co.add(jb2);
		jb = new JButton("登     录");
		jb.setBounds(230, 180, 80, 26);
		co.add(jb);
	}

	public static void main(String[] args) {
		MyQQ qq = new MyQQ();
		qq.QQFrame();
	}

}
