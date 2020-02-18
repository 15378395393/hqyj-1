package com.hqyj.javaDemo.java.training.checkOut;

import java.util.Scanner;

/**
 * @Description: 结算系统
 * @author: HymanHu
 * @date: 2020年2月10日
 */
public class CheckOutSys {

	public static Scanner scanner = new Scanner(System.in);
	
	// 系统开始菜单
	public static void beginMenu() {
		System.out.println("*********************************");
		System.out.println("******welcome to the market******");
		System.out.println("*********************************");
		System.out.println("*********************************");
		System.out.println("*****[S]店主         [C]顾客*****");
		System.out.println("*****[A]关于         [Q]退出*****");
		System.out.println("*********************************");
	}
	
	// 系统退出菜单
	public static void quitMenu() {
		System.out.println("*********************************");
		System.out.println("***********欢迎下次光临**********");
		System.out.println("*********************************");
	}
	
	// 关于菜单
	public static void aboutNemu() {
		System.out.println("*********************************");
		System.out.println("****     author:HymanHu      ****");
		System.out.println("****      version:1.1.0      ****");
		System.out.println("****     date:2020-02-10     ****");
		System.out.printf("*********************************\n\n");
	}
	
	// 店主菜单
	public static void ownerMenu() {
		System.out.println("*********************************");
		System.out.println("*****        店主            *****");
		System.out.println("*********************************");
		System.out.println("*****[P]上架         [D]下架*****");
		System.out.println("*****[M]修改         [Q]退出*****");
		System.out.println("*********************************");
	}
	
	// 店主操作
	public static void owner() {
		boolean con = true;
		while(con) {
			ownerMenu();
			int number = StorageRack.toTimeGoodsNumber();
			if (number > 0) {
				System.out.println("上架商品中有 " + number + "类商品过期，请尽快下架");
			}
			System.out.println("请选择你的操作");
			String option = scanner.nextLine().toLowerCase();
			switch (option) {
				case "p": {
					StorageRack.printGoodsInfo();
					System.out.println("请输入上架商品信息，格式如下(不用输入上架日期)");
					System.out.println("1 面包 5 20 2020-02-03 2020-11-15");
					Goods goods = StorageRack.readGoods();
					StorageRack.addGoods(goods);
					StorageRack.printGoodsInfo();
					break;
				}
				case "d": {
					StorageRack.printGoodsInfo();
					System.out.println("请输入需要删除的商品编号");
					Goods goods = StorageRack.readGoods();
					StorageRack.deleteDoogs(goods);
					StorageRack.printGoodsInfo();
					break;
				}
				case "m": {
					StorageRack.printGoodsInfo();
					System.out.println("请输入需要修改的商品编号");
					Goods goods = StorageRack.readGoods();
					StorageRack.updateGoods(goods.getId());
					StorageRack.printGoodsInfo();
					break;
				}
				case "q": {
					con = false;
					break;
				}
				default: {
					System.out.println("输入有误，请重新输入");
					break;
				}
			}
		}
	}
	
	// 顾客菜单
	public static void customerMenu() {
		System.out.println("*********************************");
		System.out.println("*****          顾客         *****");
		System.out.println("*********************************");
		System.out.println("*****[A]选购         [S]结算*****");
		System.out.println("*****[M]修改         [Q]退出*****");
		System.out.println("*********************************");
	}
	
	// 顾客操作
	public static void customer() {
		boolean con = true;
		Order order = new Order();
		while(con) {
			customerMenu();
			String option = scanner.nextLine().toLowerCase();
			switch (option) {
				case "a": {
					StorageRack.printGoodsInfo();
					System.out.println("请输入选购商品编号以及数量，格式如下");
					System.out.println("1 4");
					String[] goodsInfo = scanner.nextLine().split(" ");
					if (goodsInfo != null && goodsInfo.length == 2) {
						int goodsId = Integer.parseInt(goodsInfo[0]);
						int goodsNumber = Integer.parseInt(goodsInfo[1]);
						Goods goods = StorageRack.findGoodsByGoodsId(goodsId);
						if (goods != null) {
							order.addOrUpdateCargo(goodsId, goodsNumber);
							order.printCorgoInfo();
						} else {
							System.out.println("商品不存在，请重新选择");
							continue;
						}
					} else {
						System.out.println("输入格式有误，请重新输入");
						continue;
					}
					break;
				}
				case "m": {
					System.out.println("请输入修改商品编号以及数量，格式如下");
					System.out.println("1 4");
					String[] goodsInfo = scanner.nextLine().split(" ");
					if (goodsInfo != null && goodsInfo.length == 2) {
						int goodsId = Integer.parseInt(goodsInfo[0]);
						int goodsNumber = Integer.parseInt(goodsInfo[1]);
						Goods goods = order.findGoodsById(goodsId);
						if (goods != null) {
							order.addOrUpdateCargo(goodsId, goodsNumber);
							order.printCorgoInfo();
						} else {
							System.out.println("商品不存在，请重新选择");
							continue;
						}
					} else {
						System.out.println("输入格式有误，请重新输入");
						continue;
					}
					break;
				}
				case "s": {
					order.printCorgoInfo();
					System.out.println("请输入付款金额");
					double payMoney = Double.parseDouble(scanner.nextLine().trim());
					System.out.println(String.format("找零：%.2f", order.totalPrice() - payMoney));
					break;
				}
				case "q": {
					con = false;
					break;
				}
				default: {
					System.out.println("输入格式有误，请重新输入");
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		boolean con = true;
		StorageRack.init();
		while(con) {
			beginMenu();
			System.out.println("请选择你需要的操作");
			String option = scanner.nextLine().toLowerCase();
			switch(option) {
				case "s": {
					owner();
					break;
				}
				case "c": {
					customer();
					break;
				}
				case "a": {
					aboutNemu();
					break;
				}
				case "q": {
					quitMenu();
					con = false;
					break;
				}
				default: {
					System.out.println("输入有错，请重新输入");
					break;
				}
			}
		}
		scanner.close();
	}
	
}
