package com.hqyj.servletDemo.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * 创建servlet对象
     */
    public TestServlet() {
        super();
        System.out.println("New servlet.");
    }

	/* 
	 * servlet开始服务
	 */
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		System.out.println("Call service.");
		super.service(req, res);
	}

	/* 
	 * 销毁servlet
	 */
	@Override
	public void destroy() {
		System.out.println("Destroy servlet.");
		super.destroy();
	}

	/* 
	 * 初始化servlet
	 */
	@Override
	public void init() throws ServletException {
		System.out.println("Init servlet.");
		super.init();
	}

	/* 
	 * 对应get请求，直接输出页面
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Call get method.");
		
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName() + "------" + cookie.getValue());
		}
		
		Cookie cookie = new Cookie("testCookie", "HymanHu");
		cookie.setMaxAge(24*60*60);
		response.addCookie(cookie);
		
		response.setContentType("text/html");
		response.setStatus(response.SC_OK);
		PrintWriter pw = response.getWriter();
		pw.append("<html>");
		pw.append("<head>");
		pw.append("<title>Hello World</title>");
		pw.append("</head>");
		pw.append("<body>");
		pw.append("<div>");
		pw.append("The first servlet page1111.</br>");
		pw.append(request.getPathInfo() + "</br>");
		pw.append(request.getLocalName() + "</br>");
		pw.append(request.getMethod() + "</br>");
		pw.append(request.getRemoteHost() + "</br>");
		pw.append("</div>");
		pw.append("</body>");
		pw.append("</html>");
	}

	/* 
	 * 对应post请求
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Call post method.");
		doGet(request, response);
	}

}
