package com.hqyj.javaDemo.util.lambda;

import java.util.function.Consumer;

/**
 * @Description: Lambda Wrapper
 * lambda 中 forEach 不能抛出异常，直接在里面try catch会破坏代码简洁，使用包装类处理
 * @author: HymanHu
 * @date: 2019-08-26 13:14:54
 */
public class LambdaWrapper {
	
	/**
	 * throw Unchecked Exception For Consumer
	 * @param consumer		consumer
	 * @param clazz			抛出指定类型，如果转换失败，抛出Exception
	 * @return consumer
	 */
	public static <T, E extends Exception> Consumer<T> throwUncheckedExceptionForConsumer(
			Consumer<T> consumer, Class<E> clazz) {
		return item -> {
			try {
				consumer.accept(item);
			} catch (Exception e) {
				try {
					E exCast = clazz.cast(e);
					throw exCast;
				} catch (Exception e2) {
					throw e;
				}
			}
		};
	}
	
	/**
	 * throw Checked Exception For Consumer
	 * @param throwingConsumer		throwingConsumer
	 * @param clazz					抛出指定类型，如果转换失败，抛出RuntimeException
	 * @return consumer
	 */
	public static <T, E extends Exception> Consumer<T> throwCheckedExceptionForConsumer(
			ThrowingConsumer<T, E> throwingConsumer, Class<E> clazz) {
		return item -> {
			try {
				throwingConsumer.accept(item);
			} catch (Exception e) {
				try {
					E exCast = clazz.cast(e);
					throw exCast;
				} catch (Exception e2) {
					throw new RuntimeException(e);
				}
			}
		};
	}
}
