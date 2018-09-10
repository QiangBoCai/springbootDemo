package com.lance.db.entity.db2;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name ="student")
public class StudentEntity  implements Serializable  {
	private static final long serialVersionUID = -2314142742688138932L;

	//@Id 
	//@GeneratedValue(strategy =GenerationType.IDENTITY)//注：1.JPA策略 Oracle使用IDENTITY 需要使用Oracle sql 创建SEQUENCE,且id 为String
	//@Column(name="id")
	//private String  id; 
	@Id 
	@GeneratedValue(generator ="hibernateIdStrategy")
	@GenericGenerator(name = "hibernateIdStrategy", strategy = "native") //2.使用 Hibernate拓展id策略,id为String
	@Column(name="id")
	private String  id; 
	
	//@Id
	//@GeneratedValue(generator = "snowFlakeId")
	//@GenericGenerator(name = "snowFlakeId", strategy = "com.lance.db.entity.db2.SnowflakeId")//3.使用自定义主键生成策略需要 指定类全名
	//@Column(name = "id")
	//private String id;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="age")
	private int age;

	public StudentEntity(String id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	



	
	
	
	
	
}
