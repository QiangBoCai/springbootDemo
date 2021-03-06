package com.mobile263.iotbss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class SpringBootCXFApplication  extends SpringBootServletInitializer{

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
