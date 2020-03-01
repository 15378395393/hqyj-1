//Course.java

package com.hqyj.javaDemo.java.training.autoClassSort;

public class Course {
	private String name;//课程名称
	private int times;//课时数
	private int timesWeek;
	private int id;//课程号
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public Course(){
    	
    }
    public Course(int id,String name,int times,int timesWeek){
    	this.name = name;
    	this.id = id;
    	this.times = times;
    	this.timesWeek = timesWeek;
    }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getTimesWeek() {
		return timesWeek;
	}
	public void setTimesWeek(int timesWeek) {
		this.timesWeek = timesWeek;
	}
	
}