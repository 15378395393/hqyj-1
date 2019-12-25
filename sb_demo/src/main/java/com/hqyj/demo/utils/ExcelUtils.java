package com.hqyj.demo.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

/**
 * Excel Utils
 * @author: HymanHu
 * @date: 2019年12月25日
 */
public class ExcelUtils {
	
	public static void exportExcel(List<?> list, String title, String sheetName, 
			Class<?> pojoClass, String fileName, boolean isCreateHeader, 
			HttpServletResponse response) throws IOException {
		ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
		exportParams.setCreateHeadRows(isCreateHeader);
		defaultExport(list, pojoClass, fileName, response, exportParams);
	}

	public static void exportExcel(List<?> list, String title, String sheetName, 
			Class<?> pojoClass, String fileName,
			HttpServletResponse response) throws IOException {
		defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName, ExcelType.XSSF));
	}

	public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, 
			ExportParams exportParams, HttpServletResponse response) throws IOException {
		defaultExport(list, pojoClass, fileName, response, exportParams);
	}

	public static void exportExcel(List<Map<String, Object>> list, String fileName, 
			HttpServletResponse response) throws IOException {
		defaultExport(list, fileName, response);
	}

	private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, 
			HttpServletResponse response, ExportParams exportParams) throws IOException {
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
		downLoadExcel(fileName, response, workbook);
	}

	private static void defaultExport(List<Map<String, Object>> list, String fileName, 
			HttpServletResponse response) throws IOException {
		Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
		downLoadExcel(fileName, response, workbook);
	}

	private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook)
			throws IOException {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename*= UTF-8''"
					+ URLEncoder.encode(fileName + "." + ExcelTypeEnum.XLSX.getValue(), "UTF-8"));
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	public static <T> List<T> importExcel(String filePath, Integer titleRows, 
			Integer headerRows, Class<T> pojoClass) throws IOException {
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		ImportParams params = new ImportParams();
		params.setTitleRows(titleRows);
		params.setHeadRows(headerRows);
		params.setNeedSave(true);
		params.setSaveUrl("/excel/");
		try {
			return ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
		} catch (NoSuchElementException e) {
			throw new IOException("模板不能为空");
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	public static <T> List<T> importExcel(MultipartFile file, Class<T> pojoClass) throws IOException {
		return importExcel(file, 1, 1, pojoClass);
	}

	public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, 
			Integer headerRows, Class<T> pojoClass) throws IOException {
		return importExcel(file, titleRows, headerRows, false, pojoClass);
	}

	public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, 
			Integer headerRows, boolean needVerfiy, Class<T> pojoClass) throws IOException {
		if (file == null) {
			return null;
		}
		try {
			return importExcel(file.getInputStream(), titleRows, headerRows, needVerfiy, pojoClass);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	public static <T> List<T> importExcel(InputStream inputStream, Integer titleRows, 
			Integer headerRows, boolean needVerfiy, Class<T> pojoClass) throws IOException {
		if (inputStream == null) {
			return null;
		}
		ImportParams params = new ImportParams();
		params.setTitleRows(titleRows);
		params.setHeadRows(headerRows);
		params.setSaveUrl("/excel/");
		params.setNeedSave(true);
		params.setNeedVerfiy(needVerfiy);
		try {
			return ExcelImportUtil.importExcel(inputStream, pojoClass, params);
		} catch (NoSuchElementException e) {
			throw new IOException("excel文件不能为空");
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * Excel Type Enum
	 * @author: HymanHu
	 * @date: 2019年12月25日
	 */
	enum ExcelTypeEnum {
		XLS("xls"), XLSX("xlsx");
		private String value;

		ExcelTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
