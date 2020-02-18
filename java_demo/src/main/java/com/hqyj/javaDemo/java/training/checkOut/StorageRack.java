package com.hqyj.javaDemo.java.training.checkOut;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @Description: 货架
 * @author: HymanHu
 * @date: 2020年2月8日
 */
public class StorageRack {

	// 货架最大货物种类数量
	public static int MAX_GOODS_SORT = 10;
	// 货架商品数组
	private static Goods[] GOODS_ARRAY = new Goods[MAX_GOODS_SORT];
	// 默认货物名称
	private static String DEFAULT_GOODS_NAME = "----";
//	private static Scanner scanner = new Scanner(System.in);
	
	// 初始化货架
	public static void init() {
		IntStream.range(0, MAX_GOODS_SORT).forEach(i -> {
			GOODS_ARRAY[i] = new Goods(0, DEFAULT_GOODS_NAME, 0, 0, 
					LocalDate.of(2020, 2, 1), LocalDate.of(2020, 2, 1), LocalDate.of(2020, 2, 1));
		});
	}
	
	// 从输入信息中获取商品信息
	public static Goods readGoods() {
		while(true) {
			String goodsInfo = CheckOutSys.scanner.nextLine().trim();
			String[] goodsPro = goodsInfo.split(" ");
			if(goodsPro != null && goodsPro.length == 6) {
				return new Goods(
						Integer.parseInt(goodsPro[0]), 
						goodsPro[1], 
						Double.parseDouble(goodsPro[2]), 
						Integer.parseInt(goodsPro[3]), 
						LocalDate.parse(goodsPro[4]), 
						LocalDate.parse(goodsPro[5]), 
						LocalDate.now());
			} else if (goodsPro != null && goodsPro.length == 1) {
				Goods goods = new Goods();
				goods.setId(Integer.parseInt(goodsPro[0]));
				return goods;
			} else {
				System.out.println("输入格式有误，请重新输入");
			}
		}
	}
	
	// 判断货架是否已满
	public static boolean isFull() {
		for (Goods goods : GOODS_ARRAY) {
			if (goods.getName().equals(DEFAULT_GOODS_NAME)) {
				return false;
			}
		}
		return true;
	}
	
	// 判断货架是否为空
	public static boolean isEmpty() {
		for (Goods goods : GOODS_ARRAY) {
			if (goods.getName().equals(DEFAULT_GOODS_NAME)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	
	// 判断货物是否存在
	public static boolean isExist(Goods goods) {
		for (Goods goodsTemp : GOODS_ARRAY) {
			if (goodsTemp.getId() == goods.getId()) {
				return true;
			}
		}
		return false;
	}
	
	// 根据商品id查找商品
	public static Goods findGoodsByGoodsId(int goodsId) {
		for (Goods goods : GOODS_ARRAY) {
			if (goods.getId() == goodsId) {
				return goods;
			}
		}
		return null;
	}
	
	// 根据商品id查找该商品在数组中的下标
	public static int findGoodsArrayIndexById(int goodsId) {
		for (int i = 0; i < GOODS_ARRAY.length; i ++) {
			if (GOODS_ARRAY[i].getId() == goodsId) {
				return i;
			}
		}
		return -1;
	}
	
	// 货架过期商品数量
	public static int toTimeGoodsNumber() {
		return Arrays.asList(GOODS_ARRAY)
				.stream()
				.filter(item -> LocalDate.now().isAfter(item.getToDate()) 
						&& !item.getName().equals(DEFAULT_GOODS_NAME))
				.collect(Collectors.toList())
				.size();
	}
	
	// 添加商品
	public static void addGoods(Goods goods) {
		if (isFull()) {
			System.out.println("货架已满，不能上架");
			return;
		}
		if (isExist(goods)) {
			System.out.println("商品已经存在，不能上架");
			return;
		}
		
		try {
			for (Goods goodsTemp : GOODS_ARRAY) {
				if (goodsTemp.getName().equals(DEFAULT_GOODS_NAME)) {
					BeanUtils.copyProperties(goodsTemp, goods);
					System.out.println("添加货物成功");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 删除商品
	public static void deleteDoogs(Goods goods) {
		if (isEmpty()) {
			System.out.println("货架为空，无法删除");
			return;
		}
		
		try {
			if (isExist(goods)) {
				for (Goods goodsTemp : GOODS_ARRAY) {
					if (goodsTemp.getId() == goods.getId()) {
						BeanUtils.copyProperties(goodsTemp, 
							new Goods(0, DEFAULT_GOODS_NAME, 0, 0, 
								LocalDate.of(2020, 2, 1), 
								LocalDate.of(2020, 2, 1), 
								LocalDate.of(2020, 2, 1)));
						System.out.println("删除商品成功");
						return;
					}
				}
				System.out.println("删除商品失败");
			} else {
				System.out.println("删除的商品不在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 修改商品信息
	public static void updateGoods(int goodsId) {
		if (isEmpty()) {
			System.out.println("货架为空，无法修改");
			return;
		}
		
		int index = findGoodsArrayIndexById(goodsId);
		if (index == -1) {
			System.out.println("商品不存在，无法修改");
		} else {
			boolean con = true;
			while (con) {
				System.out.println("请选择要修改的商品属性：");
				System.out.println("a:单价 b:数量 q:退出");
				switch (CheckOutSys.scanner.nextLine().toLowerCase()) {
					case "a": {
						System.out.println("请输入商品单价：");
						double price = CheckOutSys.scanner.nextFloat();
						GOODS_ARRAY[index].setPrice(price);
						break;
					}
					case "b": {
						System.out.println("请输入商品数量：");
						int number = CheckOutSys.scanner.nextInt();
						GOODS_ARRAY[index].setNumber(number);
						break;
					}
					case "q": {
						con = false;
						break;
					}
					default: {
						System.out.println("输入有错误，请重新输入");
					}
				}
			}
		}
	}
	
	// 打印货架商品信息
	public static void printGoodsInfo() {
		System.out.println("========================================================");
		System.out.println("编号    商品    单价    数量    生产时间    上架日期    到期日期");
		System.out.println("========================================================");
		for (Goods goods : GOODS_ARRAY) {
			System.out.print(String.format(" %d ", goods.getId()));
			System.out.printf("%-5s", goods.getName());
			System.out.printf("%-5.2f", goods.getPrice());
			System.out.printf("%5d ", goods.getNumber());
			System.out.print(String.format(" %s ", goods.getProductDate()));
			System.out.print(String.format(" %s ", goods.getUpDate()));
			System.out.print(String.format(" %s ", goods.getToDate()));
			System.out.println("");
		}
		System.out.println("========================================================");
	}
	
}
