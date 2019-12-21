package com.hqyj.servletDemo.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面访问量统计程序
 */
@WebServlet("/CounterServlet")
public class CounterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CounterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = this.getServletConfig().getServletContext();
		Integer counter = (Integer) sc.getAttribute("counter");
		if (null == counter) {
			counter = new Integer(1);
		} else {
			counter ++;
		}
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.append("<html>");
		pw.append("<header>");
		pw.append("<title>页面访问统计</title>");
		pw.append("</header>");
		pw.append("<body>");
		pw.append("<h1>该页面被访问了：" + counter + "次。</h1>");
		pw.append("</body>");
		pw.append("</html>");
		pw.flush();
		pw.close();
		
		sc.setAttribute("counter", counter);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
