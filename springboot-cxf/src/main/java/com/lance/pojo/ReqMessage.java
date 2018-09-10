package com.lance.pojo;

import java.io.Serializable;

public class ReqMessage implements Serializable  {

	private static final long serialVersionUID = 1L;
	//交易流水号
	private String transactionId = "";//yyyyMMddHHmmssSSS-OperationType-Sequence
	//手机号码
	private String acc_nbr ="";
	//充值账本
	private String charge_code = "";
	//金额
	private String fee = "";
	
	//过期时间
	private String expireDate = "";//yyyyMMddHHmmss
	
	//完整性密钥
	private String iKey = "";//在首次订购时必选
	
	//token
	private String token = "";//对必选参数集合的加密toke

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getCharge_code() {
		return charge_code;
	}

	public void setCharge_code(String charge_code) {
		this.charge_code = charge_code;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getiKey() {
		return iKey;
	}

	public void setiKey(String iKey) {
		this.iKey = iKey;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	
	
	
}
