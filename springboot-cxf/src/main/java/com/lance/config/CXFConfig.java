package com.lance.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lance.webservice.ICommonService;
import com.lance.webservice.IMobile263Service;
import com.lance.webservice.IUserService;

@Configuration
public class CXFConfig {
	@Autowired
	private Bus bus;
	
	@Autowired
	ICommonService commonService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IMobile263Service mobile263Service;
	/**  JAX-WS**/

	@Bean
	public Endpoint publishCommonService(){
		EndpointImpl endpoint = new EndpointImpl(bus,commonService);
		//相当于把Commonservice接口发布在了路径/services/CommonService，可以修改cxf.path 默认services  
		//wsdl文档路径为http://ip:port/services/CommonService?wsdl
		endpoint.publish("/CommonService");//发布服务
		return endpoint;
	}
	
	
	@Bean
	public Endpoint publishUserService(){
		EndpointImpl endpoint = new EndpointImpl(bus,userService);
		endpoint.publish("/UserService");//发布服务
		return endpoint;
	}
	
	
	

	
	@Bean
	public Endpoint publish263Service(){
		EndpointImpl endpoint = new EndpointImpl(bus,mobile263Service);
		endpoint.publish("/Mobile263Service");//发布服务
		return endpoint;
	}
		

}
