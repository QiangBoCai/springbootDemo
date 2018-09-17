package com.mobile263.iotbss.pojo;

import java.io.Serializable;

public class RspMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private RspMessageHead rspMessageHead;
	
	private RspMessageBody rspMessageBody;

	public RspMessageHead getRspMessageHead() {
		return rspMessageHead;
	}

	public void setRspMessageHead(RspMessageHead rspMessageHead) {
		this.rspMessageHead = rspMessageHead;
	}

	public RspMessageBody getRspMessageBody() {
		return rspMessageBody;
	}

	public void setRspMessageBody(RspMessageBody rspMessageBody) {
		this.rspMessageBody = rspMessageBody;
	}

	@Override
	public String toString() {
		return "RspMessage [rspMessageHead=" + rspMessageHead + ", rspMessageBody=" + rspMessageBody + "]";
	}
	
	
}
