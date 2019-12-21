package com.hqyj.servletDemo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hqyj.servletDemo.entity.User;
import com.hqyj.servletDemo.util.DBUtil;

/**
 * user dao
 * @author: HymanHu
 * @date: 2019年11月24日
 */
public class UserDaoImpl {

	/**
	 * 根据userName查询user
	 */
	public User getUserByUserName(String userName) {
		Connection conn = DBUtil.getConnection();
		String sql = "select * from m_user where user_name = ?";
		User user = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setCreateDate(rs.getDate("create_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		
		return user;
	}
	
	public User getUserByUserNameAndPassword(String userName, String password) {
		Connection conn = DBUtil.getConnection();
		String sql = "select * from m_user where user_name = ? and password = ?";
		User user = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setCreateDate(rs.getDate("create_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		
		return user;
	}
	
	/**
	 * 根据userId查询user
	 */
	public User getUserByUserId(int userId) {
		Connection conn = DBUtil.getConnection();
		String sql = "select * from m_user where user_id = ?";
		User user = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setCreateDate(rs.getDate("create_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		
		return user;
	}
	
	/**
	 * 插入user，并返回有id的user
	 */
	public User insertUser(User user) {
		Connection conn = DBUtil.getConnection();
		String sql = "insert into m_user (user_name, password, create_date) values (?, ?, ?)";
		
		try {
			/*
			 * 从5.1.17版本之后的mysql-connector增加了返回GeneratedKeys的条件，
			 * 如果需要返回GeneratedKeys，则PreparedStatement需要显示添加一个参数
			 * PreparedStatement.RETURN_GENERATED_KEYS
			 */
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassword());
			ps.setDate(3, new Date(user.getCreateDate().getTime()));
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			while(rs.next()) {
				user.setUserId(rs.getInt(1));
			}
			
//			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		
		return user;
	}
	
	/**
	 * 更新user
	 */
	public User updateUser(User user) {
		Connection conn = DBUtil.getConnection();
		String sql = "update m_user set user_name = ?, password = ? where user_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getUserId());
			ps.execute();
			
//			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		
		return user;
	}
	
	public void deleteUser(int userId) {
		Connection conn = DBUtil.getConnection();
		String sql = "delete from m_user where user_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
	}
	
	/**
	 * 查询所有users
	 */
	public List<User> getUsers() {
		Connection connection = null;
		String sql = "select * from m_user";
		List<User> users = new ArrayList<User>();
		
		try {
			connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setCreateDate(rs.getDate("create_date"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		
		return users;
	}
}
