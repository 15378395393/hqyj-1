package com.hqyj.servletDemo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.hqyj.servletDemo.dao.UserDaoImpl;
import com.hqyj.servletDemo.entity.User;

/**
 * Servlet implementation class DoLoginController
 */
@WebServlet("/doLogin")
public class DoLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoLoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.getRequestDispatcher("/login").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDaoImpl userDaoImpl = new UserDaoImpl();
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			request.setAttribute("message", "请输入用户名和密码");
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}
		User user = userDaoImpl.getUserByUserNameAndPassword(userName, password);
		if (user == null) {
			request.setAttribute("message", "用户名或密码输入错误");
			//请求转发
			request.getRequestDispatcher("/login").forward(request, response);
			// 请求重定向
//			response.sendRedirect("/login");
			return;
		}
		
		request.getSession().setAttribute("user", user);
		request.getRequestDispatcher("/index").forward(request, response);
	}

}
