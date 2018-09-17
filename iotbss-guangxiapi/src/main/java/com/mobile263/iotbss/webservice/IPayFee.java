package com.mobile263.iotbss.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.mobile263.iotbss.pojo.ReqMessageBody;
import com.mobile263.iotbss.pojo.ReqMessageHead;
import com.mobile263.iotbss.pojo.RspMessage;
import com.mobile263.iotbss.pojo.RspMessageBody;

@WebService(name = "263_PayFee",//定义服务，用在类上
targetNamespace = "http://webservice.iotbss.mobile263.com")//命名空间，一般是接口的包名倒序
public interface IPayFee {

	@WebMethod(operationName="UNI_BSS")
	@WebResult(name="UNI_BSS")
	public RspMessage PayFee(@WebParam(name="UNI_HEAD")ReqMessageHead requestHead,
			@WebParam(name="UNI_BODY")ReqMessageBody request);
}
