package com.lance.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.lance.pojo.User;

@WebService(name = "UserService",//定义服务，用在类上
targetNamespace = "http://webservice.lance.com")//命名空间，一般是接口的包名倒序
public interface IUserService {
	
	
	@WebMethod
	@WebResult(name="USER_NAME")
	public String getName(@WebParam(name="USER_ID")Long userId);
	
	@WebMethod 
	@WebResult(name="USER")
	public User getUser(@WebParam(name="USER_ID")long userId);
	
}
