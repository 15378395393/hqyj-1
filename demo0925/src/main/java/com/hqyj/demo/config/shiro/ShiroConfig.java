package com.hqyj.demo.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

	@Autowired
	private MyRealm myRealm;
	
	/**
	 * 配置shiro安全管理器
	 */
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myRealm);
		return securityManager;
	}
	
	/**
	 * 配置shiro过滤器工厂
	 * -----------------
	 * 拦截权限
	 * anon：匿名访问，无需登录
	 * authc：登录后才能访问
	 * logout：登出
	 * roles：角色过滤器
	 * ------------------
	 * URL匹配风格
	 * ?：匹配一个字符，如 /admin? 将匹配 /admin1，但不匹配 /admin 或 /admin/
	 * *：匹配零个或多个字符串，如 /admin* 将匹配 /admin 或/admin123，但不匹配 /admin/1
	 * **：匹配路径中的零个或多个路径，如 /admin/** 将匹配 /admin/a 或 /admin/a/b
	 */
	@Bean
	public ShiroFilterFactoryBean filterBean() {
		ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
		filterFactory.setSecurityManager(securityManager());
		filterFactory.setLoginUrl("/shiro/login");
		filterFactory.setSuccessUrl("/shiro/dashboard");
		filterFactory.setUnauthorizedUrl("/error/403");
		
		Map<String, String> map = new LinkedHashMap<>();
		map.put("/static/**", "anon");
		map.put("/js/**", "anon");
		map.put("/css/**", "anon");
		map.put("/test/**", "anon");
		map.put("/shiro/login", "anon");
		map.put("/shiro/doLogin", "anon");
		map.put("/shiro/register", "anon");
		map.put("/shiro/doRegister", "anon");
		map.put("/shiro/**", "authc");
		filterFactory.setFilterChainDefinitionMap(map);
		
		return filterFactory;
	}
	
	/**
	 * 注册shiro方言，让thymeleaf支持shiro标签
	 */
	@Bean
	public ShiroDialect shiroDialect(){
		return new ShiroDialect();
	}
	
	/**
	 * 自动代理类，支持Shiro的注解
	 */
	@Bean
	@DependsOn({"lifecycleBeanPostProcessor"})
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

    /**
     * 开启Shiro的注解
     */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}
}
