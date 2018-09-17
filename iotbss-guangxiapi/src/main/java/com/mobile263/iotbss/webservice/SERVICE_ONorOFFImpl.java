package com.mobile263.iotbss.webservice;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.mobile263.iotbss.pojo.ReqMessageBody;
import com.mobile263.iotbss.pojo.RspMessageBody;


@WebService(name = "SERVICE_ONorOFF",//定义服务，用在类上
targetNamespace = "http://webservice.iotbss.mobile263.com",//命名空间，一般是接口的包名倒序
endpointInterface = "com.mobile263.iotbss.webservice.ISERVICE_ONorOFF")//接口地址
@Component
public class SERVICE_ONorOFFImpl implements ISERVICE_ONorOFF {

	@Override
	public RspMessageBody SERVICE_ONorOFF(ReqMessageBody request) {
		RspMessageBody rsp = new RspMessageBody();
		rsp.setTransactionId(request.getTransactionId());
		rsp.setResultCode("0");
		rsp.setResultStr("ok");
		rsp.setToken("testToken");
		rsp.setOrderId("111111111111");
		return rsp;
	}

}
