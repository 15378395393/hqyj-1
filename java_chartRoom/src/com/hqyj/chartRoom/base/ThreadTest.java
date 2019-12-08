package com.hqyj.chartRoom.base;

/**
 * --线程测试类，1、继承Thread；2、实现Runnable
 * @Description: 
 * @author: HymanHu
 * @date: 2019年12月8日
 */
public class ThreadTest extends Thread{

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
	
	// 成员内部类，写在类内部，和方法、属性平级
	// 实现Runnable
	public class ThreadTest1 implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
		}
	}
	
	public static void main(String[] args) {
		ThreadTest threadTest = new ThreadTest();
		threadTest.start();
		
		ThreadTest1 threadTest1 = threadTest.new ThreadTest1();
		Thread thread1 = new Thread(threadTest1);
		thread1.start();
		
	}

}
