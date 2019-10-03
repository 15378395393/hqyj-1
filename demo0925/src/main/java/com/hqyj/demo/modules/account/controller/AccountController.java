package com.hqyj.demo.modules.account.controller;

import java.awt.Font;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqyj.demo.common.Result;
import com.hqyj.demo.common.gifCaptcha.GifCaptcha;
import com.hqyj.demo.modules.account.entity.User;
import com.hqyj.demo.modules.service.UserService;

@Controller
@RequestMapping("/shiro")
public class AccountController {
	
	@Autowired
	private UserService userService;

	/**
	 * 跳转logoin页面
	 */
	@RequestMapping("/login")
	public String loginPage(ModelMap modelMap) {
		modelMap.addAttribute("template", "shiro/login");
		return "shiroIndexSimple";
	}
	
	/**
	 * 用户登录
	 */
	@RequestMapping(value="/doLogin", method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result doLogin(HttpServletRequest request, @RequestBody User user) {
		Result result = userService.getUserByNameAndPassword(user);
		
		//存入Session
    	HttpSession session = request.getSession(true);
    	session.setAttribute("user", user);
    	
		return result;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, ModelMap modelMap) {
		HttpSession session = request.getSession(true);
    	session.removeAttribute("user");
    	
		return "redirect:/shiro/login";
	}
	
	/**
	 * 跳转注册页面
	 */
	@RequestMapping("/register")
	public String registerPage(ModelMap modelMap) {
		modelMap.addAttribute("template", "shiro/register");
		return "shiroIndexSimple";
	}
	
	/**
	 * 新增用户
	 */
	@RequestMapping(value="/doRegister", 
			method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result doRegister(HttpServletRequest request, @RequestBody User user) {
		Result result = userService.addUser(user);
		
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
		modelMap.addAttribute("template", "shiro/dashboard");
		return "shiroIndex";
	}
	
	/**
	 * 跳转user页面
	 */
	@RequestMapping("/users")
	public String usersPage(ModelMap modelMap) {
		modelMap.put("template", "shiro/users");
		return "shiroIndex";
	}
	
	/**
	 * 跳转roles页面
	 */
	@RequestMapping("/roles")
	public String rolesPage(ModelMap modelMap) {
		modelMap.put("template", "shiro/roles");
		return "shiroIndex";
	}
	
	/**
	 * 跳转permission页面
	 */
	@RequestMapping("/permissions")
	public String permissionsPage(ModelMap modelMap) {
		modelMap.put("template", "shiro/permissions");
		return "shiroIndex";
	}
	
	/**
	 * 生成动态验证码
	 * $("#codePic").bind("click", function() {
	 * 	$("#codePic").attr("src", "/getGifCode?flag=" + Math.random());
	 * });
	 */
	@RequestMapping(value="/getGifCode",method=RequestMethod.GET)
	public void getGifCode(HttpServletResponse response, HttpServletRequest request){
		// 设定response相关信息
		response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/gif");
        
        GifCaptcha gifCaptcha = new GifCaptcha(146, 33, new Font("宋体", Font.BOLD, 20), 100);
        try {
        	//存入Session
        	HttpSession session = request.getSession(true);
        	session.setAttribute("captchaWord", gifCaptcha.getWord());
        	
        	// 输出gif图片
            gifCaptcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
