
//ClassRoom.java

package com.hqyj.javaDemo.java.training.autoClassSort;

public class ClassRoom {
	private String name;//教室名称
	private int id;//教室号
	private int number;//教室容纳人数
	
    public ClassRoom(){
    	
    }
    public ClassRoom(int id,String name,int number){
    	this.name = name;
    	this.id = id;
    	this.number = number;
    }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
