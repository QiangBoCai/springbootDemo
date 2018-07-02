package com.lance.bean;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;


/**
 * 测试实体类
 * @author xiaotao
 *
 */
public class User {
	
	private long id;
	private Date createTime;
	private String name;
	private int age;
	private String address;
	
	private Date endTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@JsonIgnore//序列化时忽略该字段
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  //因为使用了fastjson，此时@JsonFormat注解会失效
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	

	//@JSONField(serialize = false)//序列化时忽略该字段
	//@JSONField(format =  "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	
}
