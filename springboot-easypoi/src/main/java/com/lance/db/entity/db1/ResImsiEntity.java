package com.lance.db.entity.db1;

import java.io.Serializable;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResImsiEntity implements Serializable {
	private static final long serialVersionUID = -2314142742688138932L;

	private long id;
	
	private int sourceId;
	
	private String imsi;
	
	private String msisdn;
	
	private String ki;
	
	private String opc;
	
	private String mlist;
	
	
	private String status;
	
	private Date createTime;
	
	private Date updateTime;
	
	
	private Date modifyTime;
	
	
	
}
