package com.hqyj.hrms.config;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.hqyj.hrms.entity.Resource;
import com.hqyj.hrms.entity.Role;
import com.hqyj.hrms.entity.User;
import com.hqyj.hrms.service.AccountService;
import com.hqyj.hrms.service.AuthorityService;

/**
 * Shiro核心组件，实现用户验证和授权两大功能
 * @author: HymanHu
 * @date: 2019年11月11日
 */
//@Component
public class MyRealm extends AuthorizingRealm {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private AuthorityService authorityService;

	/* 
	 * 资源授权，获取当前用户，封装该用户下所有角色，再封装角色对应的授权资源
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 授权类
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		// 获取user信息
		String userName = (String) principals.getPrimaryPrincipal();
		User user = accountService.getUserByName(userName);
		if (user == null) {
			return null;
		}
		
		// 获取角色信息，配合页面标签，确定不同角色访问不同的资源
		List<Role> roles = authorityService.getRolesByUserId(user.getUserId());
		for (Role role : roles) {
			authorizationInfo.addRole(role.getRoleName());
			List<Resource> resources = authorityService.getResourcesByRoleId(role.getRoleId());
			for (Resource resource : resources) {
				authorizationInfo.addStringPermission(resource.getResourceName());
			}
		}
		
		return authorizationInfo;
	}

	/* 
	 * 身份验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String account = (String) token.getPrincipal();
		User user = accountService.getUserByName(account);
		if (user == null) {
			throw new UnknownAccountException("The account do not exist.");
		}
		
		// realmName: 当前 realm 对象的唯一名字. 调用父类的 getName() 方法
		return new SimpleAuthenticationInfo(account, user.getPassword(), getName());
	}

}
