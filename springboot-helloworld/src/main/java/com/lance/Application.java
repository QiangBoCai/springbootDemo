package com.lance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication  //让spring boot自动给程序进行必要的配置
public class Application  extends SpringBootServletInitializer{//1.继承SpringBootServletInitializer

	//spring-boot-parent 默认使用logback 和slf4j 日志工具
	private static  Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Override //2.重写configure方法
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	
	public static void main(String[] args){//3.main方法入口，如果不使用内置tomcat，可以省略main函数
		logger.debug("enter main method");
		
		SpringApplication.run(Application.class, args);
	}
}
