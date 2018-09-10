package com.lance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication //(等价于)@Configuration + @EnableAutoConfiguration + @ComponentScan。
public class SpringBootCXFApplication  extends SpringBootServletInitializer{

	//springboot默认logback日志工具和slf4j
	private static Logger logger = LoggerFactory.getLogger(SpringBootCXFApplication.class);
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootCXFApplication.class);
	}

	
	
	public static void main(String[] args) {
		logger.debug("enter main method");
		
		SpringApplication.run(SpringBootCXFApplication.class, args);
		
	}


	
}
