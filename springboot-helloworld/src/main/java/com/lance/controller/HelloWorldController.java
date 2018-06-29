package com.lance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *web动态页面
 */

@Controller
public class HelloWorldController {
	
	private  Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping("/hello") //若"/"未配置，则默认指定为静态页面index.html
	@ResponseBody
	public String hello(){
		logger.debug("enter hello page");
		return "Hello World";
	}
	
}
