package com.lance;


import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.lance.db.utils.EasyPOIUtils;
import com.lance.db.vo.ResImsiVO;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;


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
