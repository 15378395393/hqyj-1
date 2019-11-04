package com.hqyj.erp.modules.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.erp.modules.account.dao.AccountDao;
import com.hqyj.erp.modules.account.entity.User;
import com.hqyj.erp.modules.account.service.AccountService;
import com.hqyj.erp.modules.authority.dao.AuthorityDao;
import com.hqyj.erp.modules.authority.entity.UserRole;
import com.hqyj.erp.modules.common.vo.Result;
import com.hqyj.erp.modules.common.vo.SearchVo;
import com.hqyj.erp.modules.common.vo.SystemConstant;
import com.hqyj.erp.util.MD5Util;

@Service
public class AccountServiceImpl implements AccountService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AuthorityDao authorityDao;

	@Override
	public Result inserOrUpdatetUser(User user) {
		if (user == null || StringUtils.isBlank(user.getAccount()) 
				|| (StringUtils.isBlank(user.getPassword()) && user.getUserId() <= 0)) {
			return new Result(500, "User name or password is null.");
		}
		
		User existUser = accountDao.getUserByName(user.getAccount());
		if (existUser != null && 
				((existUser.getUserId() != user.getUserId()) || user.getUserId() <= 0)) {
			return new Result(500, "User is exist.");
		}
		
		try {
			user.initUser(user);
			if (user.getUserId() > 0) {
				accountDao.updateUserById(user);
			} else {
				accountDao.insertUser(user);
			}
			
			authorityDao.deleteUserRole(user.getUserId());
			Integer[] userRoles = user.getUserRoles();
			if (userRoles != null) {
				for (Integer roleId : userRoles) {
					authorityDao.insertUserRole(new UserRole(user.getUserId(), roleId));
				}
			}
			
			return new Result(200, "insert User success.", user);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
			return new Result(500, "insert User fail.");
		}
	}

	@Override
	public Result getUserResult(User user) {
		Subject subject = SecurityUtils.getSubject();
		try {
			UsernamePasswordToken usernamePasswordToken = 
					new UsernamePasswordToken(user.getAccount(), MD5Util.getMD5(user.getPassword()));
			usernamePasswordToken.setRememberMe(user.getRememberMe());
			
			// 登录验证，调用MyRealm中doGetAuthenticationInfo方法
			subject.login(usernamePasswordToken);
			
			// 授权，调用MyRealm中doGetAuthorizationInfo方法
			subject.checkRoles();
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(500, e.getMessage());
		}
		
		return new Result(200, "success");
	}

	@Override
	public User getUserBySession() {
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getSession().getAttribute(SystemConstant.USER_KEY);
		subject.getPrincipal();
		if (userId != null) {
			return accountDao.getUserById(userId);
		} else {
			return null;
		}
	}

	@Override
	public User getUserByName(String account) {
		return accountDao.getUserByName(account);
	}

	@Override
	public User getUserById(int userId) {
		return accountDao.getUserById(userId);
	}

	@Override
	public PageInfo<User> getUserList(SearchVo userSearch) {
		List<User> users = new ArrayList<>();
		
		SearchVo.initSearchVo(userSearch);
		PageHelper.startPage(userSearch.getCurrentPage(), userSearch.getPageSize());
		try {
			users = accountDao.getUserListBySearch(userSearch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new PageInfo<>(users);
	}

	@Override
	public Result deleteUserById(int userId) {
		try {
			accountDao.deleteUserById(userId);
			return Result.getResult(1);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getResult(-1);
		}
	}
}
