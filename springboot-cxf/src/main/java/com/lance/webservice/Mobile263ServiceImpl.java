package com.lance.webservice;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.lance.pojo.ReqMessage;
import com.lance.pojo.RspMessage;
import com.lance.pojo.User;


@WebService(name = "Mobile263Service",//定义服务，用在类上
targetNamespace = "http://webservice.lance.com",//命名空间，一般是接口的包名倒序
endpointInterface = "com.lance.webservice.IMobile263Service")//接口地址
@Component
public class Mobile263ServiceImpl implements IMobile263Service {

	@Override
	public RspMessage PayFee(ReqMessage request) {
		RspMessage rsp = new RspMessage();
		rsp.setTransactionId(request.getTransactionId());
		rsp.setResultCode("0");
		rsp.setResultStr("ok");
		rsp.setToken("testToken");
		rsp.setOrderId("111111111111");
		return rsp;
	}

}
