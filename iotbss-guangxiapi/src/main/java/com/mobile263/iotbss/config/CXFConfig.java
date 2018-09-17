package com.mobile263.iotbss.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mobile263.iotbss.webservice.IPayFee;
import com.mobile263.iotbss.webservice.ISERVICE_ONorOFF;
import com.mobile263.iotbss.webservice.IStopReplyThe;

@Configuration
public class CXFConfig {
	@Autowired
	private Bus bus;
	
	
	@Autowired
	IPayFee payFee;

	@Autowired
	IStopReplyThe stopReplyThe;

	@Autowired
	ISERVICE_ONorOFF service_ONorOFF;

	//签约套餐
	@Bean
	public Endpoint publish263_PayFee(){
		EndpointImpl endpoint = new EndpointImpl(bus,payFee);
		endpoint.publish("/263_PayFee");//发布服务
		return endpoint;
	}
		
	//停复机
	@Bean
	public Endpoint publishStopReplyThe(){
		EndpointImpl endpoint = new EndpointImpl(bus,stopReplyThe);
		endpoint.publish("/StopReplyThe");//发布服务
		return endpoint;
	}
	//限速
	@Bean
	public Endpoint publishService_ONorOFF(){
		EndpointImpl endpoint = new EndpointImpl(bus,service_ONorOFF);
		endpoint.publish("/SERVICE_ONorOFF");//发布服务
		return endpoint;
	}
}
