package com.lance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication //(等价于)@Configuration + @EnableAutoConfiguration + @ComponentScan。

public class SpringBootWebMagicApplication  {

	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootWebMagicApplication.class, args);
		
	}


	
}
