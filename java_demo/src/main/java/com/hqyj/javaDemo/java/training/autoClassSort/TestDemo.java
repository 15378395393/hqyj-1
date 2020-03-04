//TestDemo.java

package com.hqyj.javaDemo.java.training.autoClassSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class TestDemo {
	public static void main(String[] args) {
//		OrderCourse ordercourse = new OrderCourse();
//		pai p =new pai();
//		ordercourse.initShow();
//		System.out.print("增加资源(以上陈列的)，请输入y，否则进行排课操作：");
//		Scanner scanner = new Scanner(System.in);
//		String input = scanner.next();
//		if (input.equals("y")) {
//			ordercourse.add();
//			//p.add();
//		}
//		System.out.println("系统进行排课中...");
//		ordercourse.control();
		 //p.c();
		
		List<Teacher> teacherList = new ArrayList<Teacher>();
		List<Class> classList = new ArrayList<Class>();
		List<Course> courseList = new ArrayList<Course>();
		List<ClassRoom> classRoomList = new ArrayList<ClassRoom>();
		IntStream.range(0, 3).forEach(item -> {
			teacherList.add(new Teacher(item, "老师" + item));
			classList.add(new Class(item, "班级" + item, 20));
			courseList.add(new Course(item, "课程" + item, 29, 6));
			classRoomList.add(new ClassRoom(item, "教室" + item, 50));
		});
		
		OrderCourse ordercourse2 = new OrderCourse();
		ordercourse2.order(teacherList, classList, courseList, classRoomList);
	}
}