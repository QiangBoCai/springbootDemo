package com.lance.webservice;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.lance.pojo.User;

@WebService(serviceName = "UserService",//暴露服务名称
			targetNamespace = "http://webservice.lance.com",//命名空间，一般是接口的包名倒序
			endpointInterface = "com.lance.webservice.IUserService")//接口地址
@Component
public class UserServiceImpl implements IUserService {

	@Override
	public String getName(Long userId) {
		return "lance:"+userId;
	}

	@Override
	public User getUser(long userId) {
		User user = new User();
		user.setUserId(new Long(21));
		user.setUsername("lance");
		//user.setEmail("1213test@qq.com");
		return user;
	}

}
