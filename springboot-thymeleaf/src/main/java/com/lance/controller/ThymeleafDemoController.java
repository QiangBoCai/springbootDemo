package com.lance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ThymeleafDemoController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 测试Hello World
	 */
	
	
	@RequestMapping(value = "/hello",method = RequestMethod.GET)
	public String hello(ModelMap modelMap){
		 modelMap.put("name", "world");
		return "index";
	}
	
	
	
	
	
	
}
