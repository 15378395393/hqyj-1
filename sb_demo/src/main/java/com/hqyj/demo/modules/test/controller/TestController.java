package com.hqyj.demo.modules.test.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	 * 下载文件
	 * 响应头信息
	 * 'Content-Type': 'application/octet-stream',
	 * 'Content-Disposition': 'attachment;filename=req_get_download.js'
	 * @return ResponseEntity ---- spring专门包装响应信息的类
	 */
	@RequestMapping("/download")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
		try {
			// 使用resource来包装下载文件
			Resource resource = new UrlResource(
					Paths.get("D:/upload" + File.separator + fileName).toUri());
			if (resource.exists() && resource.isReadable()) {
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
						.header(HttpHeaders.CONTENT_DISPOSITION, 
								String.format("attachment; filename=\"%s\"", resource.getFilename()))
						.body(resource);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 将文件以BufferedInputStream的方式读取到byte[]里面，然后用OutputStream.write输出文件
	 */
	@RequestMapping("/download1")
	public void downloadFile1(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam String fileName) {
		String filePath = "D:/upload" + File.separator + fileName;
		File downloadFile = new File(filePath);
		
		if (downloadFile.exists()) {
			response.setContentType("application/octet-stream");
			response.setContentLength((int)downloadFile.length());
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
					String.format("attachment; filename=\"%s\"", fileName));
			
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(downloadFile);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (bis != null) {
						bis.close();
					}
				} catch (Exception e2) {
					LOGGER.debug(e2.getMessage());
					e2.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 以包装类 IOUtils 输出文件
	 */
	@RequestMapping("/download2")
	public void downloadFile2(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam String fileName) {
		String filePath = "D:/upload" + File.separator + fileName;
		File downloadFile = new File(filePath);
		
		try {
			if (downloadFile.exists()) {
				response.setContentType("application/octet-stream");
				response.setContentLength((int)downloadFile.length());
				response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
						String.format("attachment; filename=\"%s\"", fileName));
				
				InputStream is = new FileInputStream(downloadFile);
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传多个文件
	 */
	@RequestMapping(value="/uploadBatchFile", method=RequestMethod.POST, consumes="multipart/form-data")
	public String uploadBatchFile(@RequestParam MultipartFile[] files, 
			RedirectAttributes redirectAttributes) {
		boolean isEmpty = true;
		try {
			for (MultipartFile file : files) {
				if (file.isEmpty()) {
//					break;
					continue;
				}
				
				String fileName = file.getOriginalFilename();
				String destFileName = "D:/upload" + File.separator + fileName;
				
				File destFile = new File(destFileName);
				file.transferTo(destFile);
				
				isEmpty = false;
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "upload file fail.");
			return "redirect:/test/index";
		}
		
		if (isEmpty) {
			redirectAttributes.addFlashAttribute("message", "Please select file.");
		} else {
			redirectAttributes.addFlashAttribute("message", "upload file success.");
		}
		
		return "redirect:/test/index";
	}
	
	/**
	 * 上传单个文件，虽然是form表单，但file是以参数的形式传递的，采用requestParam注解接收MultipartFile
	 */
	@RequestMapping(value="/upload", method=RequestMethod.POST, consumes="multipart/form-data")
	public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
		
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select file.");
			return "redirect:/test/index";
		}
		
		try {
			String fileName = file.getOriginalFilename();
			String destFileName = "D:/upload" + File.separator + fileName;
			
			File destFile = new File(destFileName);
			file.transferTo(destFile);
			
			// 使用工具类Files来上传文件
//			byte[] bytes = file.getBytes();
//			Path path = Paths.get(destFileName);
//			Files.write(path, bytes);
			
			redirectAttributes.addFlashAttribute("message", "upload file success.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "upload file fail.");
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
		}
		
		return "redirect:/test/index";
	}
	
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
//		modelMap.addAttribute("template", "test/index");
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
	public String testInfo(HttpServletRequest request, 
			@RequestParam(name="key", defaultValue="1111", required=false) String value) {
		String value2 = request.getParameter("key");
		return "This is spring boot app." + value + "---" + value2;
	}
}
