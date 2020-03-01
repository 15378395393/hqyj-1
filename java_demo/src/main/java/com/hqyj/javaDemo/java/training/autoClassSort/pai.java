package com.hqyj.javaDemo.java.training.autoClassSort;

import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;

public class pai {
	private Teacher t;// 老师人数t
	private Class c;// 班级c
	private Course cs;// 课程cs
	private ClassRoom cr;// 教室cr
	private LinkedList<Course> csList;
	private LinkedList<ClassRoom> crList;
	private LinkedList<Teacher> tList;
	private LinkedList<Class> cList;
	// 已经排成的时间段
	LinkedList<String> sub;

	public pai() {

		// 设置老师
		t = new Teacher(1001, "剑锋");

		tList = new LinkedList<Teacher>();
		tList.add(t);

		// 设置上课班级
		c = new Class(0601, "JAVA0601班", 20);// 0601名, "JAVA0601班", 20人数
		cList = new LinkedList<Class>();
		cList.add(c);
		// 设置课程
		cs = new Course(1001, "javabase", 29, 6);
		// 1001课程号, "javabase"课程名称, 29课时数, 6周天数

		csList = new LinkedList<Course>();
		csList.add(cs);

		// 设置上课教室
		cr = new ClassRoom(1001, "实验楼303", 50);
		// 1001教室号, "实验楼303"教师名称, 50容纳人数

		crList = new LinkedList<ClassRoom>();
		crList.add(cr);

	}

	//
	LinkedList<String> temList1;
	LinkedList<String> temList2;

	// 从LinkedList<String>中随机取出timesWeek个元素组成的LinkedList<String>
	public LinkedList<String> randList(LinkedList<String> list, int timesWeek) {
		LinkedList<String> subList = new LinkedList<String>();
		Random rand = new Random();

		int j = 0;
		while (true) {
			// rand.nextInt该方法的作用是生成一个随机的int值，该值介于[0,n)的区间，也就是0到n之间的随机int值，包含0而不包含n
			String m = list.get(rand.nextInt((list.size() - 1)));
			// contains是判断是否包含有XX
			if (!subList.contains(m)) {
				subList.add(m);
				j++;
			}
			if (j == timesWeek) {
				break;
			}
		}
		return subList;
	}

	// 对随机时间段排序
	public TreeSet<String> listToTree(LinkedList<String> sub) {
		TreeSet<String> set = new TreeSet<String>(sub);

		return set;
	}

	public void order(Teacher t) {
		Course course = null;
		course = cs;
		// clone()是创建节数的副本
		// list1.retainAll(list2) ; list1 和 list2 两个集合 先取 交集 。 然后 将交集 赋给 list1
		temList1 = (LinkedList<String>) (t.getTs().getList().clone());// temList1存放原教师空闲时间
		t.getTs().getList().retainAll(c.getCs().getList());// 求教师与学生时间交集
		temList2 = t.getTs().getList();// temList2存放教师与学生时间交集
		sub = randList(temList2, course.getTimesWeek());// 取出教师和学生的一定次数的随机组合
		c.getCs().getList().removeAll(sub);// 移去被分去的时间
		t.getTs().setList(temList1);// 恢复t1中时间
		t.getTs().getList().removeAll(sub);// 移去被分去的时间
		System.out.println(sub);
		System.out.println(sub);
	}

	public void showCourseTable(Teacher t) {
		Course course = null;
		course = cs;

		System.out.println(t.getName() + "的课表详情排列如下： " + "课程名称: " + course.getName());
		System.out.println("班级名称： " + c.getName() + " 教室名称： " + cr.getName());

		for (String s : listToTree(sub)) {
			System.out.println(s);
		}
	}

	public void c() {
		/* for(t=1;t<=3;i++){} */
		// t1排课
		order(t);
		showCourseTable(t);
	}

	public void add() {
		// TODO Auto-generated method stub

	}

}
