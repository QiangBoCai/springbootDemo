package com.lance.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StudentVO {
	private long id;
	private Date createTime;
	private String username;
	
	private String password;
	private int age;
	
	
	@JsonIgnore//序列化时忽略该字段
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	
}
