package com.lance.db.entity.db1;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name ="student")
public class Student implements Serializable {
	private static final long serialVersionUID = -2314142742688138932L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)   //主键,id为long
	@Column(name="id")
	private long id;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="age")
	private int age;
	


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
	@Override
	public String toString() {
		return "Student [id=" + id + ", createTime=" + createTime + ", username=" + username + ", password=" + password
				+ ", age=" + age + "]";
	}
	

	
	
	
	
	
}
