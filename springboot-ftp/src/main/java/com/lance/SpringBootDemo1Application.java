package com.lance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication //(等价于)@Configuration + @EnableAutoConfiguration + @ComponentScan。
public class SpringBootDemo1Application  extends SpringBootServletInitializer{

	//springboot默认logback日志工具和slf4j
	private static Logger logger = LoggerFactory.getLogger(SpringBootDemo1Application.class);
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootDemo1Application.class);
	}

	
	
	public static void main(String[] args) {
		logger.debug("enter main method");
		
		//SpringApplication.run(SpringBootDemo1Application.class, args);
		
	}


	
}
