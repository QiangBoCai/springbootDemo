package com.lance.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
