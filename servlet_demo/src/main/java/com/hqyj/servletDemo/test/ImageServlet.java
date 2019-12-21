package com.hqyj.servletDemo.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestForImage
 */
@WebServlet("/imageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		//创建图像
		int width = 200;
		int height = 30;
		BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		//得到图画上下文
		Graphics2D g = (Graphics2D)image.getGraphics();
		//填充背景
		g.setColor(Color.gray);
		g.fillRect(0,0,width,height);
		//画字符串
		g.setColor(Color.white);
		g.setFont(new Font("Dialog",Font.PLAIN,14));
		g.drawString("辛苦遭逢起一经！",10,height/2 + 4);
		//画边框
		g.setColor(Color.black);
		g.drawRect(0,0,width - 1,height - 1);
		//Dispose context
		g.dispose();
		//send back image
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(image ,"png", sos);
//		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
//		encoder.encode(image);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
