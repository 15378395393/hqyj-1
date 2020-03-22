package com.hqyj.javaDemo.java.base;

import java.text.DecimalFormat;

public class Test {
	public static void main(String[] args) {
		System.out.println(71 / 10 + 3 * 27 + 6 * 2);
		int sum1, sum2 = 0;
		for (int a = 0; a <= 100; a ++) {
			for (int b = 0; b <= 100; b ++) {
				for (int c = 0; c <= 100; c ++) {
					sum1 = a + 30 * b + 60 * c;
//					sum1 = a + 30 * b + 60 * c;
					sum2 = a + b + c;
					if (sum1 == 1000 && sum2 == 100) {
						System.out.println(String.format("买鸡蛋%,d, 买鸭蛋%d, 买鹅蛋%d.", a, b , c));
						break;
					}
				}
			}
		}
		double aaa = (1 / 9) * 100;
		DecimalFormat df2  = new DecimalFormat("###.00");
		System.out.println(3.33 / 2.0);
		System.out.println((double) (Math.round(3.33 * 100) / 100.0));
	}
}
