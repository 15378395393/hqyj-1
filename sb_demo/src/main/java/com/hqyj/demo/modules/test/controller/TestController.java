package com.hqyj.demo.modules.test.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.hqyj.demo.modules.test.entity.City;
import com.hqyj.demo.modules.test.entity.Country;
import com.hqyj.demo.modules.test.service.TestService;
import com.hqyj.demo.modules.test.vo.ApplicationTestBean;

/**
 * 测试类控制器
 * @author: HymanHu
 * @date: 2019年11月26日
 */
@Controller
@RequestMapping("/test")
public class TestController {
	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	@Value("${server.port}")
	private int port;
	@Value("${com.hqyj.name}")
	private String name;
	@Value("${com.hqyj.age}")
	private int age;
	@Value("${com.hqyj.description}")
	private String description;
	@Value("${com.hqyj.random}")
	private String random;
	
	@Autowired
	private ApplicationTestBean applicationTestBean;
	@Autowired
	private TestService testService;
	
	/**
	 * 返回test/index页面
	 * return index ---- return classpath:/templates/index.html
	 * template:test/index ---- classpath:/templates/test/index.html
	 */
	@RequestMapping("/index")
	public String testPage(ModelMap modelMap) {
		int countryId = 522;
		List<City> cities = testService.getCitiesByCountryId(countryId);
		cities = cities.stream().limit(10).collect(Collectors.toList());
		
		modelMap.addAttribute("thymeleafTitle", "This is thymeleaf test page.");
		modelMap.addAttribute("changeType", "checkbox");
		modelMap.addAttribute("checked", true);
		modelMap.addAttribute("currentNumber", 88);
		modelMap.addAttribute("baiduUrl", "/test/config");
//		modelMap.addAttribute("shopLogo", "http://cdn.duitang.com/uploads/item/201308/13/20130813115619_EJCWm.thumb.700_0.jpeg");
		modelMap.addAttribute("country", testService.getcountryByCountryId(countryId));
		modelMap.addAttribute("city", cities.get(0));
		modelMap.addAttribute("cities", cities);
		modelMap.addAttribute("updateCityUri", "/test/city");
		modelMap.addAttribute("template", "test/index");
		return "index";
	}
	
	/**
	 * 删除城市
	 */
	@DeleteMapping(value="/city/{cityId}")
	@ResponseBody
	public void deleteCity(@PathVariable int cityId) {
		testService.deleteCity(cityId);
	}
	
	/**
	 * 更改城市
	 * 接受form表单数据 ---- application/x-www-form-urlencoded || @ModelAttribute
	 */
	@PutMapping(value="/city", consumes="application/x-www-form-urlencoded")
	@ResponseBody
	public City updateCity(@ModelAttribute City city) {
		return testService.updateCity(city);
	}
	
	/**
	 * 插入城市
	 * 接受json数据 ---- @RequestBody || application/json
	 */
	@PostMapping(value="/city", consumes="application/json")
	@ResponseBody
	public City insertCity(@RequestBody City city) {
		return testService.insertCity(city);
	}
	
	/**
	 * 分页查询城市信息
	 */
	@RequestMapping("/cities/{currentPage}/{pageSize}")
	@ResponseBody
	public PageInfo<City> getCitiesByPage(@PathVariable int currentPage, @PathVariable int pageSize) {
		return testService.getCitiesByPage(currentPage, pageSize);
	}
	
	/**
	 * 根据country name 查询国家信息
	 * 接受查询参数 ---- @RequestParam
	 */
	@RequestMapping("/country")
	@ResponseBody
	public Country getCountryByCountryName(@RequestParam String countryName) {
		return testService.getCountryByCountryName(countryName);
	}
	
	/**
	 * 根据国家id查询国家信息
	 * 接受path参数 ---- @PathVariable
	 */
	@RequestMapping("/country/{countryId}")
	@ResponseBody
	public Country getcountryByCountryId(@PathVariable int countryId) {
		return testService.getcountryByCountryId(countryId);
	}
	
	/**
	 * 根据国家id查询所有城市
	 * @PathVariable --- 获取url路径上的参数
	 */
	@RequestMapping("/cities/{countryId}")
	@ResponseBody
	public List<City> getCitiesByCountryId(@PathVariable int countryId) {
		return testService.getCitiesByCountryId(countryId);
	}
	
	/**
	 * post接口
	 */
	@PostMapping("/post")
	@ResponseBody
	public String postTest() {
		return "This is a post interface.";
	}
	
	/**
	 * log相关接口
	 */
	@RequestMapping("/log")
	@ResponseBody
	public String loggerTest() {
		LOGGER.trace("This is trace log");
		LOGGER.debug("This is debug log");
		LOGGER.info("This is info log");
		LOGGER.warn("This is warn log");
		LOGGER.error("This is error log");
		
		return "This is logger test.";
	}

	/**
	 * 配置相关接口
	 */
	@RequestMapping("/config")
	@ResponseBody
	public String configTest() {
		StringBuffer sb = new StringBuffer();
		sb.append(port).append("----").append("<br>")
			.append(name).append("----").append("<br>")
			.append(age).append("----").append("<br>")
			.append(description).append("----").append("<br>")
			.append(random).append("----").append("<br>");
		
		sb.append(applicationTestBean.getName()).append("----").append("<br>")
			.append(applicationTestBean.getAge()).append("----").append("<br>")
			.append(applicationTestBean.getDescription()).append("----").append("<br>")
			.append(applicationTestBean.getRandom()).append("----").append("<br>");
		return sb.toString();
	}
	
	/**
	 * spring boot第一个接口
	 */
	@RequestMapping("/info")
	@ResponseBody
	public String testInfo() {
		return "This is spring boot app.";
	}
}
