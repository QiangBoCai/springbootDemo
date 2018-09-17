package com.lance.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

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

	/**
	 * 发布服务
	 * 默认 wsdl文档路径为http://ip:port/services/CommonService?wsdl
	 * cxf.path 默认services  
	 * 可以修改cxf.path:/
	 * eg:http://localhost:8080/CommonService?wsdl
	 * */
	@Bean
	public Endpoint publishCommonService(){
		EndpointImpl endpoint = new EndpointImpl(bus,commonService);
		//相当于把Commonservice接口发布在了路径/services/CommonService
		endpoint.publish("/CommonService");//发布服务
		return endpoint;
	}
	
	

	 @Bean
	   public ServletRegistrationBean dispatcherServlet() {
		  return new ServletRegistrationBean(new CXFServlet(), "/services/*");
	 }
	 
	 /**
	     * 同时支持rest请求的Servlet,rest请求以“api”为url前缀
	     * @return
	     */
    @Bean
    public ServletRegistrationBean dispatcherRestServlet() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        //替换成自己想买的controller包路径
        context.scan("com.lance.controller");
        DispatcherServlet disp = new DispatcherServlet(context);
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(disp);
        registrationBean.setLoadOnStartup(1);
        //映射路径自定义
        registrationBean.addUrlMappings("/api/*");
        registrationBean.setName("rest");
        return registrationBean;
    }

}
