package com.hqyj.servletDemo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hqyj.servletDemo.dao.UserDaoImpl;
import com.hqyj.servletDemo.entity.User;

/**
 * Servlet implementation class DoUpdateUser
 */
@WebServlet("/doUpdateUser")
public class DoUpdateUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoUpdateUserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			//请求转发
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}
		
		//请求转发
		request.getRequestDispatcher("/users").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			//请求转发
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}
		
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		User userSelected = new User();
		userSelected.setUserId(Integer.parseInt(userId));
		userSelected.setUserName(userName);
		userSelected.setPassword(password);
		
		UserDaoImpl userDao = new UserDaoImpl();
		userDao.updateUser(userSelected);
		List<User> users = userDao.getUsers();
		request.setAttribute("users", users);
		
		request.getRequestDispatcher("/users").forward(request, response);
	}

}
