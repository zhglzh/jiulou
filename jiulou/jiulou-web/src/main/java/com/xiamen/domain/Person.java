package com.xiamen.domain;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = -846624391412673444L;
	private int personId;
	private String name;
	private String age;
	
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}

	
}
