package com.hqyj.servletDemo.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.hqyj.servletDemo.dao.UserDaoImpl;
import com.hqyj.servletDemo.entity.User;

/**
 * Servlet implementation class DoRegisterController
 */
@WebServlet("/doRegister")
public class DoRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoRegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.getRequestDispatcher("/register").forward(request, response);
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
			request.getRequestDispatcher("/register").forward(request, response);
			return;
		}
		
		User userTemp = userDaoImpl.getUserByUserName(userName);
		if (userTemp != null) {
			request.setAttribute("message", "用户名已经存在");
			//请求转发
			request.getRequestDispatcher("/register").forward(request, response);
			// 请求重定向
//			response.sendRedirect("/register");
			return;
		}
		
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setCreateDate(new Date());
		user = userDaoImpl.insertUser(user);
		
		request.getSession().setAttribute("user", user);
		request.getRequestDispatcher("/index").forward(request, response);
	}

}
