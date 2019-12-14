package com.hqyj.chartRoom.base;

public class Test {

	public static void main(String[] args) {
		int bb = 0;
		Integer aa = null;
		String temp = "aaf@ddgdf@sadgfa";
		String[] ss = temp.split("@");
		for (String sss : ss) {
			System.out.println(sss);
		}
		System.out.println(temp.length() - 
				temp.replaceAll("a", "").length());
		System.out.println(temp.toUpperCase());
		System.out.println(temp.trim().toUpperCase());
	}
}
