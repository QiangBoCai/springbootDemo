package com.mobile263.iotbss.webservice;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.mobile263.iotbss.pojo.ReqMessageBody;
import com.mobile263.iotbss.pojo.ReqMessageHead;
import com.mobile263.iotbss.pojo.Response;
import com.mobile263.iotbss.pojo.RspMessage;
import com.mobile263.iotbss.pojo.RspMessageBody;
import com.mobile263.iotbss.pojo.RspMessageHead;


@WebService(name = "263_PayFee",//定义服务，用在类上
targetNamespace = "http://webservice.iotbss.mobile263.com",//命名空间，一般是接口的包名倒序
endpointInterface = "com.mobile263.iotbss.webservice.IPayFee")//接口地址
@Component
public class PayFeeImpl implements IPayFee {


	@Override
	public RspMessage PayFee(ReqMessageHead requestHead, ReqMessageBody request) {

		RspMessage rspMessage = new RspMessage();
		RspMessageHead rspHead = new RspMessageHead();
		rspHead.setTransactionId(request.getTransactionId());
		rspHead.setOrigDomain("263");
		rspHead.setActionCode("0");
		rspHead.setResponse(new Response("0000","成功"));
		rspMessage.setRspMessageHead(rspHead);
		
		RspMessageBody rspBody = new RspMessageBody();
		rspBody.setTransactionId(request.getTransactionId());
		rspBody.setResultCode("0");
		rspBody.setResultStr("ok");
		rspBody.setToken("testToken");
		rspBody.setOrderId("111111111111");
		rspMessage.setRspMessageBody(rspBody);
		
		return rspMessage;
	}

}
