package com.mobile263.iotbss.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.mobile263.iotbss.pojo.ReqMessageBody;
import com.mobile263.iotbss.pojo.RspMessageBody;

@WebService(name = "SERVICE_ONorOFF",//定义服务，用在类上
targetNamespace = "http://webservice.iotbss.mobile263.com")//命名空间，一般是接口的包名倒序
public interface ISERVICE_ONorOFF {

	@WebMethod(operationName="UNI_BSS")
	@WebResult(name="UNI_BODY")
	public RspMessageBody SERVICE_ONorOFF(@WebParam(name="UNI_BODY")ReqMessageBody request);
}
