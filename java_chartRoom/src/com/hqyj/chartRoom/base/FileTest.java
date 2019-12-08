package com.hqyj.chartRoom.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 流操作测试类
 * @author: HymanHu
 * @date: 2019年12月7日
 */
public class FileTest {
	
	// 文件路径
	public void filePath() {
		System.out.println("==============");
		System.out.println("当前项目根目录:" + System.getProperty("user.dir"));
		System.out.println("当前classPath路径:" + Thread.currentThread().getContextClassLoader().getResource("").getPath());
		System.out.println("当前类classPath路径(不包含自己):" + FileTest.class.getResource("").getPath());
		System.out.println("配置文件路径1：" + FileTest.class.getResource("/test.text").getPath());
		System.out.println("配置文件路径2：" + FileTest.class.getClassLoader().getResource("test.text").getPath());
		System.out.println("==============");
	}
	
	// 读取文件
	public void readFile() {
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = new FileInputStream(FileTest.class.getResource("/test.text").getPath());
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				sb.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 写文件
	public void writeFile() {
		InputStream is = null;
		OutputStream os = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			// 读取文件
			is = new FileInputStream(FileTest.class.getResource("/test.text").getPath());
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			List<String> contents = new ArrayList<String>();
			String line = null;
			while ((line = br.readLine()) != null) {
				contents.add(line);
			}
			
			// 写入文件
			os = new FileOutputStream("/test1.text");
			bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			for (String temp : contents) {
				bw.write(temp);
				bw.newLine();
				bw.flush();
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
				if (br != null) {
					br.close();
				}
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		FileTest fileTest = new FileTest();
		fileTest.filePath();
		fileTest.readFile();
		fileTest.writeFile();
	}
}
