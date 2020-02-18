package com.hqyj.javaDemo.java.training.checkOut;

import java.time.LocalDate;

/**
 * @Description: 商品类
 * @author: HymanHu
 * @date: 2020年2月8日
 */
public class Goods {

	private int id;
	private String name;
	private double price;
	private int number;
	private LocalDate productDate; // s生产日期
	private LocalDate toDate; // 保质期
	private LocalDate upDate; // 上架时间

	public Goods() {
	}

	public Goods(int id, String name, double price, int number, LocalDate productDate, LocalDate toDate,
			LocalDate upDate) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.number = number;
		this.productDate = productDate;
		this.toDate = toDate;
		this.upDate = upDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public LocalDate getProductDate() {
		return productDate;
	}

	public void setProductDate(LocalDate productDate) {
		this.productDate = productDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public LocalDate getUpDate() {
		return upDate;
	}

	public void setUpDate(LocalDate upDate) {
		this.upDate = upDate;
	}

}
