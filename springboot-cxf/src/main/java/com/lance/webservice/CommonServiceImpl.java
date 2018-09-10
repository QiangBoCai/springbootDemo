package com.lance.webservice;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

/**
 * 接口实现
 * @author xiaotao
 *
 */

@WebService(serviceName = "CommonService",//暴露服务名称
				targetNamespace = "http://webservice.lance.com",//命名空间，一般是接口的包名倒序
				endpointInterface = "com.lance.webservice.ICommonService")//接口地址

@Component
public class CommonServiceImpl implements ICommonService {

	@Override
	public String sayHello(String name) {
		return "Hello ," +name;
	}

}
