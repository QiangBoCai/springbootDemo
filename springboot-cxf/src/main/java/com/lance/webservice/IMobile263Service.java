package com.lance.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.lance.pojo.ReqMessage;
import com.lance.pojo.RspMessage;

@WebService(name = "Mobile263Service",//定义服务，用在类上
targetNamespace = "http://webservice.lance.com")//命名空间，一般是接口的包名倒序
public interface IMobile263Service {

	@WebMethod(operationName="UNI_BSS")
	@WebResult(name="UNI_BODY")
	public RspMessage PayFee(@WebParam(name="UNI_BODY")ReqMessage request);
}
