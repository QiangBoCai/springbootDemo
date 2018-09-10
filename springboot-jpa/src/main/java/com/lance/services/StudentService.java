package com.lance.services;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lance.db.dao.db1.DB1Repository;
import com.lance.db.dao.db2.DB2Repository;
import com.lance.db.dao.student.StudentDao;
import com.lance.db.entity.db1.Student;
import com.lance.db.entity.db2.StudentEntity;

@Service
public class StudentService {
	
	
	//@Resource
	//private DB1Repository  studentRepository ;
	
	//@Transactional()
	   // public void save(Student student){
			
		//	studentRepository.save(student);
			
	   // }
		
	
	@Resource
	
	private StudentDao studentdao;
	
	 @Autowired
	 private DB1Repository db1Repository;
	 @Autowired
	 private DB2Repository db2Repository;

	
	 @Transactional(value="transactionManagerDB1")
	 public void saveDB1(){
	     Student student = new Student();
	     student.setUsername("lance1");
	     student.setAge(28);
	     student.setCreateTime(new Date());
	     student.setPassword("123456");
	     db1Repository.save(student);

			
	 }
	 

	 @Transactional(value="transactionManagerDB2")
	 public void saveDB2() {
	     StudentEntity studentEntity = new StudentEntity();
	     studentEntity.setUsername("lance2");
	     studentEntity.setAge(28);
	     studentEntity.setCreateTime(new Date());
	     studentEntity.setPassword("123456");
	     db2Repository.save(studentEntity);
	 }	 
	
    public Student getById(long id){
    	
        return studentdao.getStudentById(id);
    }
}
