package com.lance.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lance.bean.User;

/**
 *springboot 返回json数据：
 *方式1，推荐方式
 *	使用@RestController注解 实现REST API ，避免在所有的@RequestMapping方法上加上@ResponseBody注解
 *  无法返回jsp页面，配置的视图解析器InternalResourceViewResolver不起作用，返回的内容就是Return 里的内容
 *
 *方式2
 *	使用@Controller+在@RequestMapping方法上加上@ResponseBody注解
 *
 *
 */

@RestController //相当于 @Controller+@ResponseBody
//@Controller
public class UserController {
	
	private  Logger logger = LoggerFactory.getLogger(getClass());
	
	/*
	 * 请求地址：http://127.0.0.1:8080/testjson
	 * 返回 JSON eg:{"id":1,"createTime":"2018-06-29 14:47:07","name":"Lance","age":18,"address":"宇宙","endTime":null}
	 */
	@RequestMapping("/testJson")
	//@ResponseBody //使用 ResponseBody 把java对象转换为指定格式的数据并return
	public Object testJson(){
		logger.debug("enter testJson page");
		User user = new User();
		user.setId(1);
		user.setCreateTime(new Date());
		user.setName("Lance");
		user.setAge(18);
		user.setAddress("宇宙");
		user.setEndTime(new Date());
		return user;
	}
	
	

}
