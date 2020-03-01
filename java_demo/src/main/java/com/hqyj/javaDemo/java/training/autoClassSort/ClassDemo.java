
//ClassDemo.java

package com.hqyj.javaDemo.java.training.autoClassSort;

import java.io.*;
import java.util.Date;
import java.util.Calendar;

public class ClassDemo {

//	public void print() throws Exception {
//		WritableWorkbook book = Workbook.createWorkbook(new File("Teaching.xls"));
//		WritableSheet sheet = book.createSheet("第一页", 0);
//		Label[] label = new Label[5];
//		label[0] = new Label(0, 0, "星期");
//		label[1] = new Label(1, 0, "日期");
//		label[2] = new Label(2, 0, "剑锋");
//		label[3] = new Label(3, 0, "秋静");
//		label[4] = new Label(4, 0, "秦少游");
//		for (Label l : label) {
//			sheet.addCell(l);
//		}
//		// 输出日期
//		for (int i = 1; i < 365; i++) {
//			Calendar cal = Calendar.getInstance();
//			cal.add(Calendar.DATE, i - 1);
//			double m = cal.get(Calendar.DAY_OF_WEEK);
//			jxl.write.Number number = new jxl.write.Number(0, i, m);
//
//			sheet.addCell(number);
//			Date date = cal.getTime();
//			jxl.write.DateTime dt = new jxl.write.DateTime(1, i, date);
//			sheet.addCell(dt);
//
//		}
//
//		book.write();
//		book.close();
//	}
//
//	public static void main(String[] args) {
//		ClassDemo cd = new ClassDemo();
//		try {
//			cd.print();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}

}
