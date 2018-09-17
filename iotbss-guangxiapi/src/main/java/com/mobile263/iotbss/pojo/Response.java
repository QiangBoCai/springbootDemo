package com.mobile263.iotbss.pojo;

import java.io.Serializable;

public class Response implements Serializable{

	private String rspCode;
	private String rspDesc;


	public Response(String rspCode, String rspDesc) {
		super();
		this.rspCode = rspCode;
		this.rspDesc = rspDesc;
	}
	
	public String getRspCode() {
		return rspCode;
	}
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}
	public String getRspDesc() {
		return rspDesc;
	}
	public void setRspDesc(String rspDesc) {
		this.rspDesc = rspDesc;
	}
	@Override
	public String toString() {
		return "Response [rspCode=" + rspCode + ", rspDesc=" + rspDesc + "]";
	}
	
	
}

