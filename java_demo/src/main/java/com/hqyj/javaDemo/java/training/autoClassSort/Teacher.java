//Teacher.java

package com.hqyj.javaDemo.java.training.autoClassSort;

public class Teacher {
	private int id;
	private String name;
    private Sequence ts;//节数的集合

	public Sequence getTs() {
		return ts;
	}

	public void setTs(Sequence ts) {
		this.ts = ts;
	}

	public Teacher() {

	}

	public Teacher(int id,String name) {
		this.name = name;
		this.id = id;
		setTs(new Sequence());
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
}