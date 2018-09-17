package com.lance.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Webservoce接口
 * Web Service注解介绍：
@WebService: name定义服务名称，targetNamespace命名空间
@WebMethod:operationName定义方法，即请求xml的标签名
@WebResult:定义返回值，用在方法上
@WebParam:定义参数，用在方法上
 */
@WebService(name = "CommonService",//定义服务名称
			targetNamespace = "http://webservice.lance.com")//命名空间，一般是接口的包名倒序
public interface ICommonService {
	@WebMethod (operationName="SAY_HELLO")//定义方法，即请求xml的标签名,如过没有这个注解，默认是 方法名
	@WebResult(name = "RESULT_TAG" ,targetNamespace = "") //定义方法返回值，用在方法上,name 定义返回xml中的标签名
	public String sayHello(@WebParam(name="HelloName")String name ); //定义参数 用在方法上,

}
