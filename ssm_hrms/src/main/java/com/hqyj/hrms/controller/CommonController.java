package com.hqyj.hrms.controller;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqyj.hrms.entity.User;
import com.hqyj.hrms.service.AccountService;
import com.hqyj.hrms.vo.Result;

@Controller
public class CommonController {
	
	private final static Log LOGGER = LogFactory.getLog(CommonController.class);
	
	@Autowired
	private AccountService accountService;

	@RequestMapping("/login")
	public String login() {
		return "common/login";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "common/register";
	}
	
	/**
	 * 用户注册
	 */
	@PostMapping(value="/doRegister", consumes="application/json")
	@ResponseBody
	public Result doRegister(@RequestBody User user) {
		user.initUserInfo();
		return accountService.inserOrUpdatetUser(user);
	}
	
	/**
	 * 用户登录
	 */
	@PostMapping(value="/doLogin", consumes="application/json")
	@ResponseBody
	public Result doLogin(@RequestBody User user) {
		user.initUserInfo();
		return accountService.getUserResult(user);
	}
	
	/**
	 * 退出登录
	 */
	@RequestMapping("/loginOut")
	public String loginOut() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "common/login";
	}
	
	/**
	 * indexpage
	 */
	@RequestMapping("/index")
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping("/wellcome")
	public String wellcomePage(HttpServletRequest request, ModelMap modelMap) {
		Subject subject = SecurityUtils.getSubject();
	    LOGGER.debug("------------------" + subject.isRemembered());
	    LOGGER.debug("------------------" + subject.isAuthenticated());
	    
		User user = accountService.getUserByName((String)subject.getPrincipal());
		if (user == null) {
			return "common/login";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ipAddress = null;
		String serverName = null;
		String loacalUrl = request.getScheme() + "://" + request.getLocalAddr() 
			+ ":" + request.getServerPort() + request.getServletPath();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
			serverName = inetAddress.getHostName();
		} catch (Exception e) {
			ipAddress = "无法获取ip";
		}
		
		modelMap.addAttribute("loginTime", sdf.format(new Date()));
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("userIp", ipAddress);
		modelMap.addAttribute("localIp", ipAddress);
		modelMap.addAttribute("serverName", serverName);
		modelMap.addAttribute("loacalUrl", loacalUrl);
		
		return "common/wellcome";
	}
}
