package com.hqyj.javaDemo.java.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.hqyj.javaDemo.util.lambda.LambdaWrapper;

/**
 * @Description: Lambda Test
 * @author: HymanHu
 * @date: 2019-07-27 13:19:36
 */
public class LambdaTest {
	
	public interface Converter<T1, T2> {
		void convert(int i);
	}
	
	public static void main(String[] args) {
		// 初始化接口
		final int num = 5;
		Converter<Integer, String> converter = 
				(param) -> {System.out.println("Converter result: " + String.valueOf(param + num));};
		converter.convert(3);
		
		// 创建线程
		new Thread(() -> {System.out.println(Thread.currentThread().getName());}, "Thread1").start();
		
		// 循环 1-10
		IntConsumer intConsumer = (int i) -> System.out.print(String.valueOf(i) + "-");
		IntStream.range(1, 11).forEach(intConsumer);
		System.out.println();
		IntStream.range(1, 11).forEach(i -> System.out.print(i));
		
		// list
		List<String> list = Optional.ofNullable(LambdaTest.getTestList()).orElse(Collections.emptyList());
		System.out.println(list);
		System.out.println(String.join("----", list));
		
		Function<String, String> functon = (item) -> {return item.toUpperCase();};
		list.stream().map(functon).forEach(item -> System.out.print(item + "-"));
		list.stream().map((item) -> {return item.toUpperCase();}).forEach(item -> System.out.print(item + "-"));
		list.stream().map(item -> item.toUpperCase()).forEach(item -> System.out.print(item + "-"));
		System.out.println();
		
		Predicate<String> predicate = (item) -> item.indexOf("r") > 0 && item.length() == 4;
		list.stream().filter(predicate).forEach(item -> System.out.print(item + "-"));
		list.stream().filter(item -> item.indexOf("r") > 0).forEach(item -> System.out.println(item + "-"));
		
		int gongyuehsu = IntStream.range(1, 10)
				.filter(item -> 32 % item == 0)
				.reduce((gongyueshu, item) -> gongyueshu * item).getAsInt();
		System.out.println(gongyuehsu);
		
		// Exception handle
		try {
			list.stream().forEach(LambdaWrapper.throwUncheckedExceptionForConsumer(
					item -> {
						System.out.println(1 / 0);
					}, ArithmeticException.class));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// map
		Map<String, String> mapTest = Optional.ofNullable(LambdaTest.getMapTest()).orElse(Collections.emptyMap());
		mapTest.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out :: println);
		mapTest.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(item -> {
			System.out.println(item.getKey() + "--" + item.getValue());
		});
		Map<String, String> resultMap = mapTest.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(
						Map.Entry :: getKey, 
						Map.Entry :: getValue, 
						(oldValue, newValue) -> oldValue, LinkedHashMap :: new));
		System.out.println(resultMap);
	}
	
	public static List<String> getTestList() {
		return Stream.of(
				"asdf",
				"qwer",
				null,
				"",
				"zxcv"
			).filter(Objects :: nonNull) // 不能过滤""
			.filter(item -> StringUtils.isNotBlank(item) && item != null)
			.map(item -> item.replaceAll("a", "9"))
			.collect(Collectors.toList());
	}
	
	public static Map<String, String> getMapTest() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ccccc", "c_c_c_c");
		map.put("aaaaa", "a_a_a_a");
		map.put("ddddd", "d_d_d_d");
		map.put("bbbbb", "b_b_b_b");
		return map;
	}
}
