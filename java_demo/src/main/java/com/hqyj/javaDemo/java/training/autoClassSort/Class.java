//Class.java

package com.hqyj.javaDemo.java.training.autoClassSort;
public class Class {
  private String name;//班级名称
  private int id;//班级号
  private int number;//班级人数
  private Sequence cs;
  
  public Class(){
	  
  }
  public Class(int id,String name,int number){
	  this.name = name;
	  this.number = number;
	  this.id = id;
	  setCs(new Sequence());
  }
 
public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getNumber() {
	return number;
}
public void setNumber(int number) {
	this.number = number;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Sequence getCs() {
	return cs;
}
public void setCs(Sequence cs) {
	this.cs = cs;
}


}