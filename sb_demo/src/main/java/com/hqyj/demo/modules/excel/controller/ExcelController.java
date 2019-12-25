package com.hqyj.demo.modules.excel.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hqyj.demo.modules.account.entity.User;
import com.hqyj.demo.modules.account.service.AccountService;
import com.hqyj.demo.modules.common.vo.Result;
import com.hqyj.demo.utils.ExcelUtils;

@Controller
@RequestMapping("/excel")
public class ExcelController {

	@Autowired
	private AccountService accountService;
	
	/**
	 * 测试页
	 */
	@RequestMapping("/index")
	public String excelPage() {
		return "index";
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response) throws IOException {
	    List<User> users = accountService.getUsers();
	    ExcelUtils.exportExcel(users, "员工信息表", "员工信息", User.class, "员工信息", response);
	}
	
	/**
	 * 导入文件
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST, consumes="multipart/form-data")
	@ResponseBody
	public Result importExcel(@RequestParam("excelFile") MultipartFile file) throws IOException {
	    List<User> users = ExcelUtils.importExcel(file, User.class);
	    users.stream().forEach(item -> {
	    	System.out.println(item.getUserId() + "--" + item.getUserName() 
	    		+ "--" + item.getPassword() + "--" + item.getCreateDate());
	    });
	    return new Result(200, "导入成功");
	}
}
