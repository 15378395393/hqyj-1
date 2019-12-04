package com.hqyj.demo.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公共处理类控制器
 * @author: HymanHu
 * @date: 2019年12月4日
 */
@Controller
@RequestMapping("/common")
public class CommonController {

	/**
	 * 返回403错误页面
	 */
	@RequestMapping("/403")
	public String errorPageForUnauthorized() {
		return "index";
	}
}
