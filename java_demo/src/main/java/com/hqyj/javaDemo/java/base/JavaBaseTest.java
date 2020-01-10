package com.hqyj.javaDemo.java.base;

public class JavaBaseTest {

	public static String CONTENT = "content";
	
	public char a;
	public int aa;
	public String aaa;
	public float aaaa;
	public boolean aaaaa;
	
	public char getA() {
		return a;
	}

	public void setA(char a) {
		this.a = a;
	}

	public JavaBaseTest() {}
	
	public void test(Object ... params) {
		for (Object object : params) {
			System.out.println(object);
		}
	}

	public static void main(String[] args) {
		System.out.println(JavaBaseTest.CONTENT);
		
		JavaBaseTest test = new JavaBaseTest();
		System.out.println("-----" + test.a);
		System.out.println("-----" + test.aa);
		System.out.println("-----" + test.aaa);
		System.out.println("-----" + test.aaaa);
		System.out.println("-----" + test.aaaaa);
		
		int a = 1;
		int b = 2;
		test.test(a, b);
		
		int[] arr=new int[3];
		arr = new int[5];
		System.out.println(arr.length);
	}
}
