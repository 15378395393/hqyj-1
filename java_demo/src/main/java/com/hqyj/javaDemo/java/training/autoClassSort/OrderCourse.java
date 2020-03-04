//OrderCourse.java

package com.hqyj.javaDemo.java.training.autoClassSort;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class OrderCourse {
	private Teacher t1, t2, t3;//老师人数t

	private Class c1, c2, c3;//班级c

	private Course cs1, cs2, cs3;//课程cs

	private ClassRoom cr1, cr2, cr3;//教室cr

	private LinkedList<Course> csList;

	private LinkedList<ClassRoom> crList;

	private LinkedList<Teacher> tList;

	private LinkedList<Class> cList;
	// 已经排成的时间段
	LinkedList<String> sub1;
	LinkedList<String> sub2;
	LinkedList<String> sub3;
	//
	LinkedList<String> temList1;
	LinkedList<String> temList2;

	public OrderCourse() {
		// 设置老师
		t1 = new Teacher(1001, "剑锋");
		t2 = new Teacher(1002, "秋静");
		t3 = new Teacher(1003, "秦少游");
		tList = new LinkedList<Teacher>();
		tList.add(t1);
		tList.add(t2);
		tList.add(t3);
		// 设置上课班级
		c1 = new Class(0601, "JAVA0601班", 20);//0601名, "JAVA0601班", 20人数
		c2 = new Class(0602, "JAVA0602班", 25);
		c3 = new Class(0603, "JAVA0603班", 19);
		cList = new LinkedList<Class>();
		cList.add(c1);
		cList.add(c2);
		cList.add(c3);
		// 设置课程
		cs1 = new Course(1001, "javabase", 29, 6);
		//1001课程号, "javabase"课程名称, 29课时数, 6周天数
		cs2 = new Course(1002, "jsp", 35, 6);
		cs3 = new Course(1003, "oracle", 51, 6);
		csList = new LinkedList<Course>();
		csList.add(cs1);
		csList.add(cs2);
		csList.add(cs3);
		// 设置上课教室
		cr1 = new ClassRoom(1001, "实验楼303", 50);
		//1001教室号, "实验楼303"教师名称, 50容纳人数
		cr2 = new ClassRoom(1002, "南方商务大厦", 40);
		cr3 = new ClassRoom(1003, "先锋公司", 30);
		crList = new LinkedList<ClassRoom>();
		crList.add(cr1);
		crList.add(cr2);
		crList.add(cr3);

	}

	public void initShow() {
		System.out.println("目前排课的资源情况如下：");
		System.out.println("教师情况");
		for (Teacher t : tList) {
			System.out.println("教师ID：" + t.getId() + " 教师姓名: " + t.getName());
		}
		System.out.println("教室情况");
		for (ClassRoom cr : crList) {
			System.out.println("教室ID：" + cr.getId() + " 教室名称: " + cr.getName()
					+ "" + cr.getNumber());
		}
		System.out.println("课程情况");
		for (Course cs : csList) {
			System.out.println("课程ID：" + cs.getId() + " 课程名称: " + cs.getName()
					+ " 课时数：" + cs.getTimes() + " 每周课时 " + cs.getTimesWeek());
		}
		System.out.println("班级情况");
		for (Class c : cList) {
			System.out.println("班级ID：" + c.getId() + " 班级名称: " + c.getName()
					+ " 班级人数：" + c.getNumber());
		}
	}

	// 增加排课资源数
	public void add() {

	}

	// 从LinkedList<String>中随机取出timesWeek个元素组成的LinkedList<String>
	public LinkedList<String> randList(LinkedList<String> list, int timesWeek) {
		LinkedList<String> subList = new LinkedList<String>();
		Random rand = new Random();

		int  j = 0;
		while (true) {
	//rand.nextInt该方法的作用是生成一个随机的int值，该值介于[0,n)的区间，也就是0到n之间的随机int值，包含0而不包含n
			String m = list.get(rand.nextInt((list.size() - 1)));
			//contains是判断是否包含有XX
			if (!subList.contains(m)) {
				subList.add(m);
			}
			j++;
			if (j == timesWeek) {
				break;
			}
		}
		return subList;
	}
	
	// 对随机时间段排序
	public TreeSet<String> listToTree(LinkedList<String> sub){
		TreeSet<String> set =new  TreeSet<String>(sub);
		
		return set;
	}
/*	public TreeSet<String> AllTree(LinkedList<String> sub1,LinkedList<String> sub2,
			LinkedList<String> sub3){
		TreeSet<String> set =new  TreeSet<String>(sub1);
		set.addAll(sub2);
		set.addAll(sub3);
		return set;
	}
*/
	// 排课流程
	public void order(List<Teacher> teacherList, List<Class> classList, 
			List<Course> courseList, List<ClassRoom> classRoomList) {
		for(Teacher teacher : teacherList) {
			// 根据下标找出老师对应的教室
			Course course = courseList.get(teacherList.indexOf(teacher));
			LinkedList<String> teacherSequenceTemp = new LinkedList<>();
			LinkedList<String> teacherClassTemp = new LinkedList<>();
			LinkedList<String> teacherClassRandom = new LinkedList<>();
			
			System.out.println("===========================================");
			System.out.println(teacher.getName() + "的课表详情排列如下： " + "课程名称: "
					+ course.getName());
			for(Class cla : classList) {
				// 根据下标找出班级对应的教室
				ClassRoom classRoom = classRoomList.get(classList.indexOf(cla));
				// 将老师空闲时间存入临时表
				teacherSequenceTemp = (LinkedList<String>) (teacher.getTs().getList().clone());
				// 求老师空闲时间和班级课程交集，并放入临时列表
				teacher.getTs().getList().retainAll(cla.getCs().getList());
				teacherClassTemp = teacher.getTs().getList();
				// 取出老师和班级的随机组合列表
				teacherClassRandom = randList(teacherClassTemp, course.getTimesWeek());
				// 班级课程移除被排出的时间
				cla.getCs().getList().removeAll(teacherClassRandom);
				// 老师移除被排除的时间
				teacher.getTs().setList(teacherSequenceTemp);
				teacher.getTs().getList().removeAll(teacherClassRandom);
				
				System.out.println("班级名称： " + cla.getName() + " 教室名称： " + classRoom.getName());
				for (String s : listToTree(teacherClassRandom)) {
					System.out.println(s);
				}
			}
		}
	}
	
	public void order(Teacher t){
		Course course = null;
		if(t==t1){
			course = cs1;
		}
		else if(t==t2){
			course = cs2;
		}
		else if(t==t3){
			course = cs3;
		}//clone()是创建节数的副本
		//list1.retainAll(list2) ;  list1 和 list2 两个集合 先取 交集 。 然后 将交集 赋给 list1 
		temList1 = (LinkedList<String>) (t.getTs().getList().clone());// temList1存放原教师空闲时间
		t.getTs().getList().retainAll(c1.getCs().getList());// 求教师与学生时间交集
		temList2 = t.getTs().getList();// temList2存放教师与学生时间交集
		sub1 = randList(temList2, course.getTimesWeek());// 取出教师和学生的一定次数的随机组合
		c1.getCs().getList().removeAll(sub1);// 移去被分去的时间
		t.getTs().setList(temList1);// 恢复t1中时间
		t.getTs().getList().removeAll(sub1);// 移去被分去的时间

		temList1 = (LinkedList<String>) (t.getTs().getList().clone());// temList1存放原教师空闲时间
		t.getTs().getList().retainAll(c2.getCs().getList());// 求交集
		temList2 = t.getTs().getList();// temList2存放教师与学生时间交集
		sub2 = randList(temList2, course.getTimesWeek());// 取出教师和学生的一定次数的随机组合
		c2.getCs().getList().removeAll(sub2);// 移去被分去的时间
		t.getTs().setList(temList1);// 恢复t1中时间
		t.getTs().getList().removeAll(sub2);// 移去被分去的时间

		temList1 = (LinkedList<String>) (t.getTs().getList().clone());// temList1存放原教师空闲时间
		t.getTs().getList().retainAll(c3.getCs().getList());// 求交集
		temList2 = t.getTs().getList();// temList2存放教师与学生时间交集
		sub3 = randList(temList2, course.getTimesWeek());// 取出教师和学生的一定次数的随机组合
		c3.getCs().getList().removeAll(sub3);
		t.getTs().setList(temList1);// 恢复t1中时间
		t.getTs().getList().removeAll(sub3);// 移去被分去的时间
	}
	// 输入课表方法
	public void showCourseTable(Teacher t){
		Course course = null;
		/*if(t==t1){
			course = cs1;
		}
		else if(t==t2){
			course = cs2;
		}
		else if(t==t3){
			course = cs3;
		}*/course =cs1;
		System.out.println();
		System.out.println(t.getName() + "的课表详情排列如下： " + "课程名称: "
				+ course.getName());
		System.out.println("班级名称： " + c1.getName() + " 教室名称： " + cr1.getName());
		for (String s : listToTree(sub1)) {
			System.out.println(s);
		}

		System.out.println("班级名称： " + c2.getName() + " 教室名称： " + cr2.getName());
		for (String s : listToTree(sub2)) {
			System.out.println(s);
		}

		System.out.println("班级名称： " + c3.getName() + " 教室名称： " + cr3.getName());
		for (String s : listToTree(sub3)) {
			System.out.println(s);
		}
		
		
//		System.out.println("********************");
//		for (String s : AllTree(sub1,sub2,sub3)) {
//			System.out.println(s);
//			if(sub1.contains(s)){
//				
//	    	}
//			else if(sub2.contains(s)){
//				
//			}
//			else if(sub3.contains(s)){
//				
//			}		
//		}
	}
	public void control() {
		
		// t1排课
		order(t1);
		showCourseTable(t1);
		// t2 排课
		order(t2);
		showCourseTable(t2);
		// t3 排课
		order(t3);
		showCourseTable(t3);	
	}
}


