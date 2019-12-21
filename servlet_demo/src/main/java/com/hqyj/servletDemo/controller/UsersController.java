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
 * Servlet implementation class UsersController
 */
@WebServlet("/users")
public class UsersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersController() {
        super();
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
		
		UserDaoImpl userDao = new UserDaoImpl();
		List<User> users = userDao.getUsers();
		request.setAttribute("users", users);
		
		request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
