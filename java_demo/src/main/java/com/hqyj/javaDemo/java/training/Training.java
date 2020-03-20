package com.hqyj.javaDemo.java.training;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.IntStream;

/**
 * @Description: Java基础练习题
 * @author: HymanHu
 * @date: 2020年2月8日
 */
public class Training {

	// 求[1-6]随机数
	public int randomTest() {
		return (int)(1 + Math.random() * 6);
	}
	
	// 求[m, n]随机数
	public int randomTest(int m, int n) {
		return (int)(m + Math.random() * (n - m + 1));
	}
	
	// 求三个数最大值
	public int maxTest(int x, int y, int z) {
		return x > y ? (x > z ? x : z) : (y > z ? y : z);
	}
	
	/**
	 * -模拟 Math.round 方法
	 * @param x		输入的 double
	 * @param y		保留的位数
	 * @return		四舍五入后的double
	 */
	public double roundTest(double x, int y) {
		// 方式一
		BigDecimal bd = new BigDecimal(x);
		System.out.println(bd.setScale(y, RoundingMode.HALF_UP).doubleValue());
		// 方式二(String.format 或 DecimalFormat)
		System.out.println(String.format("%." + y + "f", x));
		// 方式三
		return (int)(x * Math.pow(10, y) + 0.5) / Math.pow(10, y);
	}
	
	// 数字转化ASCII(控制字符会输出问号)
	public void printChar() {
		IntStream.range(0, 128).forEach(item -> {
			System.out.print((char)item);
		});
		System.out.println("");
	}
	
	// 字符串反向输出
	public String printDesc(String input) {
		// 方式1
		System.out.println(new StringBuffer(input).reverse());
		// 方式2
		StringBuffer sb = new StringBuffer();
		for(int i = input.length() -1; i >= 0; i --) {
			sb.append(input.charAt(i));
		}
		return sb.toString();
	}
	
	// 九九乘法表
	public void multiplication() {
		IntStream.range(1, 10).forEach(item -> {
			IntStream.range(1, item + 1).forEach(item2 -> {
				System.out.print(item2 + "*" + item + "=" + item * item2 + " ");
			});
			System.out.println("");
		});
	}
	
	/**
	 * -通过把字符移动一定的位数来实现英文字符的加密和解密
	 * @param c		输入的字符
	 * @param x		位移数，正反向通过符号控制
	 * @return char
	 */
	public static char encrypt(char c, int x) {
		if (c >= 'a' && c <= 'z') {
			c += x % 26;
			if (c < 'a') {
				c += 26;
			} else if (c > 'z') {
				c -= 26;
			}
		} else if (c >= 'A' && c <= 'Z') {
			c += x % 26;
			if (c < 'A') {
				c += 26;
			} else if (c > 'Z') {
				c -= 26;
			}
		}
		return c;
	}
	
	// 加密字符串
	public static String encrypt(String input, int x) {
		StringBuffer sb = new StringBuffer();
		for(char temp : input.toCharArray()) {
			sb.append(encrypt(temp, x));
		}
		return sb.toString();
	}
	
	// 解密字符串
	public static String decrypt(String input, int x) {
		return encrypt(input, -x);
	}
	
	/**
	 * -等号右边从左往右，a放3到寄存器，继续执行（a++），自增在后，放3到寄存器，接着a自增为4，最后一个a++放寄存器为4，a再自增变5
	 * -计算结果是将寄存器的值进行相加，结果10，赋值给a
	 * a的值变化：3-4-5-10
	 */
	public static void selfAdd() {
		int a = 3;
		a = a + (a++) + a++;
		System.out.println(a);
	}
	
	public static void main(String[] args) {
		Training training = new Training();
		IntStream.range(0, 100).forEach(item -> {
			System.out.println(training.randomTest(2, 10));
		});
		System.out.println(training.maxTest(1, 3, 2));
		System.out.println(training.roundTest(3.1415999, 3));
		training.printChar();
		System.out.println(training.printDesc("1234 56.78"));
		training.multiplication();
		System.out.println(Training.encrypt("afzAFZ", 5));
		System.out.println(Training.decrypt("fkeFKE", 5));
		
		char x = 'a';
		x = (char) (x + 3);
		x += 3;
		x = 'a' + 3;
		
		selfAdd();
		
		
		char xx = 'b';
		int i = 0;
		int j = 11;
		System.out.println(true ? xx : 0);
		System.out.println(true ? xx : 65536.00);
		System.out.println(true ? xx : 111111111);
		System.out.println(false ? i: xx);
		System.out.println(true ? j : 65536.00);
	}
	
}
