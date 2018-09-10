package com.lance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lance.SpringBootDemo1Application;

@Controller
public class HelloWorldController {
	private static Logger logger = LoggerFactory.getLogger(SpringBootDemo1Application.class);
	
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		logger.debug("enter hello page");
		return "Hello World";
	}
	
}
