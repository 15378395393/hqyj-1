package com.hqyj.demo.modules.account.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hqyj.demo.modules.account.dao.AccountDao;
import com.hqyj.demo.modules.account.entity.Resource;
import com.hqyj.demo.modules.account.entity.Role;
import com.hqyj.demo.modules.account.entity.User;
import com.hqyj.demo.modules.account.service.AccountService;
import com.hqyj.demo.modules.common.vo.Result;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao accountDao;

	@Override
	public Result getUserByUserNameAndPassword(User user) {
		User userResult = accountDao.getUserByUserNameAndPassword(user);
		if (userResult == null) {
			return new Result(500, "User name or password error.");
		}
		
		return new Result(200, "Login success.");
	}
	
	@Override
	public User getUserByName(String userName) {
		return accountDao.getUserByName(userName);
	}
	
	@Override
	public Result addUser(User user) {
		if (user == null) {
			return new Result(500, "User info is null");
		}
		
		User userTemp = accountDao.getUserByName(user.getUserName());
		if (userTemp != null) {
			return new Result(500, "User name exist.");
		}
		
		user.setCreateDate(new Date());
		accountDao.addUser(user);
		
		return new Result(200, "success", user);
	}

	@Override
	public Result editUser(User user) {
		if (user == null) {
			return new Result(500, "resource info is null");
		}
		
		// 编辑user
		
		// 添加 userRole
		accountDao.deletUserRoleByUserId(user.getUserId());
		if (user.getRoles() != null) {
			for (Role role : user.getRoles()) {
				accountDao.addUserRole(role.getRoleId(), user.getUserId());
			}
		}
		
		return new Result(200, "success", user);
	}

	@Override
	public void deleteUser(int userId) {
		accountDao.deletUserRoleByUserId(userId);
		accountDao.deleteUser(userId);
	}

	@Override
	public List<User> getUsers() {
		return accountDao.getUsers();
	}

	@Override
	public Result editRole(Role role) {
		if (role == null || StringUtils.isBlank(role.getRoleName())) {
			return new Result(500, "Role info is null");
		}
		
		if (role.getRoleId() > 0) {
			accountDao.updateRole(role);
		} else {
			accountDao.addRole(role);
		}
		
		return new Result(200, "success", role);
	}

	@Override
	public void deleteRole(int roleId) {
		accountDao.deleteRole(roleId);
	}

	@Override
	public List<Role> getRoles() {
		return accountDao.getRoles();
	}

	@Override
	public List<Role> getRolesByUserId(int userId) {
		return accountDao.getRolesByUserId(userId);
	}

	@Override
	public List<Role> getRolesByResourceId(int resourceId) {
		return accountDao.getRolesByResourceId(resourceId);
	}

	@Override
	public Result editResource(Resource resource) {
		if (resource == null || StringUtils.isBlank(resource.getResourceUri()) ) {
			return new Result(500, "resource info is null");
		}
		
		// 添加 resource
		if (resource.getResourceId() > 0) {
			accountDao.updateResource(resource);
		} else {
			accountDao.addResource(resource);
		}
		
		// 添加 roleResource
		accountDao.deletRoleResourceByResourceId(resource.getResourceId());
		if (resource.getRoles() != null && !resource.getRoles().isEmpty()) {
			for (Role role : resource.getRoles()) {
				accountDao.addRoleResource(role.getRoleId(), resource.getResourceId());
			}
		}
		
		return new Result(200, "success", resource);
	}
	
	@Override
	public void deleteResource(int resourceId) {
		accountDao.deletRoleResourceByResourceId(resourceId);
		accountDao.deleteResource(resourceId);
	}

	@Override
	public List<Resource> getResources() {
		return accountDao.getResources();
	}

	@Override
	public List<Resource> getResourcesByRoleId(int roleId) {
		return accountDao.getResourcesByRoleId(roleId);
	}

}
