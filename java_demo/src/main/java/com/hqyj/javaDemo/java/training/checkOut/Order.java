package com.hqyj.javaDemo.java.training.checkOut;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 订单类
 * @author: HymanHu
 * @date: 2020年2月9日
 */
public class Order {
	private static int id = 0;
	private int orderId;
	private List<Goods> cargos;

	public Order() {
		this.orderId = ++id;
		this.cargos = new ArrayList<Goods>();
	}
	
	// 在订单中找到对应的商品
	public Goods findGoodsById(int goodsId) {
		for (Goods goods : this.cargos) {
			if (goods.getId() == goodsId) {
				return goods;
			}
		}
		return null;
	}
	
	// 添加或修改订单商品
	public void addOrUpdateCargo(int goodsId, int goodsNumber) {
		Goods goods = findGoodsById(goodsId);
		if (goods == null) {
			goods = StorageRack.findGoodsByGoodsId(goodsId);
			if (goods == null) {
				System.out.println("添加的商品不存在");
			} else {
				goods.setNumber(goodsNumber);
				this.cargos.add(goods);
			}
		} else {
			goods.setNumber(goods.getNumber() + goodsNumber);
		}
	}
	
	// 计算订单总价
	public double totalPrice() {
		return this.cargos.stream()
				.map(item -> item.getNumber() * item.getPrice())
				.reduce((item, next) -> item + next)
				.get();
	}
	
	// 打印订单信息
	public void printCorgoInfo() {
		System.out.println("==============================");
		System.out.println("单号："+ id);
		System.out.println("打印日期："+ LocalDate.now().toString());
		System.out.println("==============================");
		System.out.println("编号    商品    单价    数量    合计");
		this.cargos.stream().forEach(item -> {
			System.out.printf("%d %-8s %-6.2f %-5d", item.getId(), 
					item.getName(), item.getPrice(), item.getNumber());
			System.out.println("");
		});
		System.out.printf("总计：%7.2f\n", this.totalPrice());
		System.out.println("==============================");
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public List<Goods> getCargos() {
		return cargos;
	}

	public void setCargos(List<Goods> cargos) {
		this.cargos = cargos;
	}

}
