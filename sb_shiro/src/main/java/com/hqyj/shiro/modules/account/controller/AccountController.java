package com.hqyj.shiro.modules.account.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqyj.shiro.common.Result;
import com.hqyj.shiro.modules.account.entity.Resource;
import com.hqyj.shiro.modules.account.entity.Role;
import com.hqyj.shiro.modules.account.entity.User;
import com.hqyj.shiro.modules.account.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	/**
	 * 跳转logoin页面
	 */
	@RequestMapping("/login")
	public String loginPage(ModelMap modelMap) {
		modelMap.addAttribute("template", "account/login");
		return "indexSimple";
	}
	
	/**
	 * 用户登录
	 */
	@RequestMapping(value="/doLogin", method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result doLogin(HttpServletRequest request, @RequestBody User user) {
		Result result = new Result(200, "success.");
		
		Subject subject = SecurityUtils.getSubject();
		try {
			// 登录验证，调用MyRealm中doGetAuthenticationInfo方法
			subject.login(new UsernamePasswordToken(user.getUserName(), user.getPassword()));
			// 授权，调用MyRealm中doGetAuthorizationInfo方法
			subject.checkRoles();
		} catch (Exception e) {
			result = new Result(500, e.getMessage());
			return result;
		}
		
		//存入Session
    	HttpSession session = request.getSession(true);
    	session.setAttribute("user", user);
		
		return result;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, ModelMap modelMap) {
    	SecurityUtils.getSubject().logout();
    	
		return "redirect:/account/login";
	}
	
	/**
	 * 跳转注册页面
	 */
	@RequestMapping("/register")
	public String registerPage(ModelMap modelMap) {
		modelMap.addAttribute("template", "account/register");
		return "indexSimple";
	}
	
	/**
	 * 新增用户
	 */
	@RequestMapping(value="/doRegister", 
			method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result doRegister(HttpServletRequest request, @RequestBody User user) {
		Result result = accountService.addUser(user);
		
		//存入Session
    	HttpSession session = request.getSession(true);
    	session.setAttribute("user", user);
    	
		return result;
	}
	
	/**
	 * 跳转dashboard页面
	 */
	@RequestMapping("/dashboard")
	public String dashboardPage(ModelMap modelMap) {
		modelMap.addAttribute("template", "account/dashboard");
		return "index";
	}
	
	/**
	 * 跳转user页面
	 */
	@RequestMapping("/users")
	//@RequiresRoles(value={"admin", "manager"}, logical=Logical.OR)
	public String usersPage(ModelMap modelMap) {
		
		modelMap.put("roles", accountService.getRoles());
		modelMap.put("users", accountService.getUsers());
		modelMap.put("template", "account/users");
		return "index";
	}
	
	/**
	 * 编辑user
	 */
	@RequestMapping(value="/editUser", 
			method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result editUser(@RequestBody User user) {
		return accountService.editUser(user);
	}
	
	/**
	 * 删除user
	 * shiro常见注解
	 * @RequiresAuthentication : 表示当前 Subject 已经认证登录的用户才能调用的代码块。
	 * @RequiresUser : 表示当前 Subject 已经身份验证或通过记住我登录的。
	 * @RequiresGuest : 表示当前 Subject 没有身份验证，即是游客身份。
	 * @RequiresRoles(value={"admin", "user"}, logical=Logical.AND)
	 * @RequiresPermissions (value={"***","***"}, logical= Logical.OR) 
	 */
	@RequestMapping("/deleteUser/{userId}")
	@RequiresPermissions("deleteUser")
	public String deleteUser(@PathVariable("userId") int userId) {
		accountService.deleteUser(userId);
		return "redirect:/account/users";
	}
	
	/**
	 * 跳转roles页面
	 */
	@RequestMapping("/roles")
	public String rolesPage(ModelMap modelMap) {
		modelMap.put("roles", accountService.getRoles());
		modelMap.put("template", "account/roles");
		return "index";
	}
	
	/**
	 * 根据userId获取role list
	 */
	@RequestMapping("/roles/user/{userId}")
	@ResponseBody
	public List<Role> getRolesByUserId(@PathVariable("userId") int userId) {
		return accountService.getRolesByUserId(userId);
	}
	
	/**
	 * 根据resourceId获取role list
	 */
	@RequestMapping("/roles/resource/{resourceId}")
	@ResponseBody
	public List<Role> getRolesByResourceId(@PathVariable("resourceId") int resourceId) {
		return accountService.getRolesByResourceId(resourceId);
	}
	
	/**
	 * 新增或修改role
	 */
	@RequestMapping(value="/editRole", 
			method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result editRole(@RequestBody Role role) {
		return accountService.editRole(role);
	}
	
	/**
	 * 删除role
	 */
	@RequestMapping("/deleteRole/{roleId}")
	public String deleteRole(@PathVariable("roleId") int roleId) {
		accountService.deleteRole(roleId);;
		return "redirect:/account/roles";
	}
	
	/**
	 * 跳转resources页面
	 */
	@RequestMapping("/resources")
	public String resourcesPage(ModelMap modelMap) {
		modelMap.put("roles", accountService.getRoles());
		modelMap.put("resources", accountService.getResources());
		modelMap.put("template", "account/resources");
		return "index";
	}
	
	/**
	 * 新增或编辑resource
	 */
	@RequestMapping(value="/editResource", 
			method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result editResource(@RequestBody Resource resource) {
		return accountService.editResource(resource);
	}
	
	/**
	 * 删除resource
	 */
	@RequestMapping("/deleteResource/{resourceId}")
	public String deleteResource(@PathVariable("resourceId") int resourceId) {
		accountService.deleteResource(resourceId);
		return "redirect:/account/resources";
	}
}
