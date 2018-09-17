package com.mobile263.iotbss.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.mobile263.iotbss.pojo.ReqMessageBody;
import com.mobile263.iotbss.pojo.RspMessageBody;

@WebService(name = "StopReplyThe",//定义服务，用在类上
targetNamespace = "http://webservice.iotbss.mobile263.com")//命名空间，一般是接口的包名倒序
public interface IStopReplyThe {

	@WebMethod(operationName="UNI_BSS")
	@WebResult(name="UNI_BODY")
	public RspMessageBody StopReplyThe(@WebParam(name="UNI_BODY")ReqMessageBody request);
}
