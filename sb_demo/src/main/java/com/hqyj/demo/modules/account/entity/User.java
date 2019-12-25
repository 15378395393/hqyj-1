package com.hqyj.demo.modules.account.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 用户类
 * @author: HymanHu
 * @date: 2019年11月28日
 */
@Entity
@Table(name="m_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Excel(name = "Id", orderNum = "0", width = 15)
	private int userId;
	@Excel(name = "用户名", orderNum = "1", width = 15)
	private String userName;
	@Excel(name = "密码", orderNum = "2", width = 15)
	private String password;
	// format: exportFormat and importFormat
	@Excel(name = "创建时间", orderNum = "3", width = 15, format = "yyyy-MM-dd")
	private Date createDate;
	
	@Transient
	private String account;
	@Transient
	private boolean rememberMe;
	@Transient
	private List<Role> roles;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
}
