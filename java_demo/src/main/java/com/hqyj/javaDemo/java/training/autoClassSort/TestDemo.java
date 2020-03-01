//TestDemo.java

package com.hqyj.javaDemo.java.training.autoClassSort;

import java.util.Scanner;

public class TestDemo {
	public static void main(String[] args) {
		OrderCourse ordercourse = new OrderCourse();
		pai p =new pai();
		//ordercourse.initShow();
		System.out.print("增加资源(以上陈列的)，请输入y，否则进行排课操作：");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		if (input.equals("y")) {
			ordercourse.add();
			//p.add();
		}
		System.out.println("系统进行排课中...");
		ordercourse.control();
		 //p.c();
	}
}