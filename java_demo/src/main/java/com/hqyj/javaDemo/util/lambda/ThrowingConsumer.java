package com.hqyj.javaDemo.util.lambda;

/**
 * @Description: Throwing Consumer
 * 		Consumer接口accept方法没有抛出异常，我们需要用一个类似于Consumer的函数式接口来抛出异常
 * @author: HymanHu
 * @date: 2019-08-26 15:12:21
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
	
	void accept(T t) throws E;
}
