package com.lance;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


/**
 * 程序入口
 * @author xiaotao
 *
 */

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})

public class Application  {

	private static  Logger logger = LoggerFactory.getLogger(Application.class);
   

	public static void main(String[] args){
		logger.debug("enter main method");
		
		SpringApplication.run(Application.class, args);
		
	}
}
