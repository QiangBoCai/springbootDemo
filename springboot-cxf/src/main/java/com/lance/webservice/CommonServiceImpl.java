package com.lance.webservice;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

/**
 * 接口实现
 *
 */

@WebService(serviceName = "CommonService",//serviceName 的值与接口的name值一致
				targetNamespace = "http://webservice.lance.com",//命名空间，一般是接口的targetNamespace一致
				endpointInterface = "com.lance.webservice.ICommonService")//接口地址，接口的完整类名

@Component
public class CommonServiceImpl implements ICommonService {

	@Override
	public String sayHello(String name) {
		return "Hello ," +name;
	}

}
