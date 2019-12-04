package com.hqyj.demo.modules.common.controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常统一处理类
 * @author: HymanHu
 * @date: 2019年12月4日
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerResolver {

	/**
	 * 统一处理UnauthorizedException，跳转到403错误页面
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public String handlerAccessDeniedException() {
		return "redirect:/common/403";
	}
}
