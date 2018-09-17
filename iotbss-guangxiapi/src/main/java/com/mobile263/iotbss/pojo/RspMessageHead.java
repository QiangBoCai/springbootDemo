package com.mobile263.iotbss.pojo;

import java.io.Serializable;

public class RspMessageHead implements Serializable {

	private static final long serialVersionUID = 1L;
	//TRAN_ID
	private String transactionId;//交易流水号 yyyyMMddHHmmssSSS-OperationType-Sequence
	
	//ORIG_DOMAIN
	private String origDomain;
	//SERVICE_NAME
	private String serviceName;
	
	//ACTION_CODE
	private String actionCode;
	//RESPONSE
	private Response response;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOrigDomain() {
		return origDomain;
	}
	public void setOrigDomain(String origDomain) {
		this.origDomain = origDomain;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "RspMessageHead [transactionId=" + transactionId + ", origDomain=" + origDomain + ", serviceName="
				+ serviceName + ", actionCode=" + actionCode + ", response=" + response + "]";
	}
	
	
	
	
	
}
