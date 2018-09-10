package com.lance.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lance.db.entity.db1.Student;
import com.lance.db.entity.db2.StudentEntity;
import com.lance.services.StudentService;
import com.lance.vo.StudentVO;


@RestController 
public class StudentController {
	
	private  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	
	StudentService studentService;
	
	
	/*@RequestMapping("/saveStudent")
	public String testSave(){
		logger.debug("enter saveStudent page");
		StudentEntity student = new StudentEntity();
		student.setUsername("Lance");
		student.setPassword("123");
		student.setCreateTime(new Date());
		student.setAge(18);
		
		try{
			studentService.save(student);
			return "saveStudent Success";
		}catch (Exception e) {
			return "saveStudent Fail";
		}
		
	}*/
	@RequestMapping("/save")
	public String testSave(){
		logger.debug("enter save page");
		
			studentService.saveDB1();
			try{
				studentService.saveDB2();
				return "saveDB2 Success";
			}catch (Exception e) {
				return "saveDB2 Fail";
			}
			
	}
	
	/***
	 * test JdbcTemplate 
	 * @param id
	 * @return
	 */
	//地址：http://127.0.0.1:8080/getById?id=1
	@RequestMapping("/getById")
	public StudentVO getById(long id){
		logger.debug("enter getById page");
		StudentVO studentVO = new StudentVO();
		
		Student student =studentService.getById(id);
		if(student!=null)
		{
			studentVO.setId(student.getId());
			studentVO.setUsername(student.getUsername());
			studentVO.setAge(student.getAge());
		}
		return studentVO;

	}
	

}
