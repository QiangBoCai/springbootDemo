package com.lance.db.dao.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.lance.db.entity.db1.Student;


/**
 * 使用JdbcTemplate操作数据库
 * @author xiaotao
 *
 */
@Repository
public class StudentDao {
	
	//@Resource 
	//private JdbcTemplate jdbcTemplate;
	@Autowired
    @Qualifier("db1JdbcTemplate")
    private JdbcTemplate db1jdbcTemplate;
    @Autowired
    @Qualifier("db2JdbcTemplate")
    private JdbcTemplate db2jdbcTemplate;

	 /**
     * 通过id获取Student对象.
     * @param id
     * @return
     */
    public Student getStudentById(long id){
       String sql = "select *from Student where id=?";
       RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
       
     //  return jdbcTemplate.queryForObject(sql, rowMapper,id);
       return db1jdbcTemplate.queryForObject(sql, rowMapper,id);
    }
	
    

    
    
    
}
