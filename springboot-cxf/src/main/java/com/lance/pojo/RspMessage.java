package com.lance.pojo;

import java.io.Serializable;

public class RspMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	//交易流水号
	private String transactionId = "";//yyyyMMddHHmmssSSS-OperationType-Sequence
	//返回码
	private String resultCode =""; //0代表成功
	//返回描述
	private String resultStr = "";//eg:OK
	//充值id
	private String orderId = "";
	
	//token
	private String token = "";//对必选参数集合的加密toke

	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	
	
}
