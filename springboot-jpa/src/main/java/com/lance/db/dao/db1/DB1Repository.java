package com.lance.db.dao.db1;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lance.db.entity.db1.Student;

/**
 * 使用JpaRepository操作数据库
 * @author xiaotao
 *
 */
public interface  DB1Repository  extends JpaRepository<Student, Long> {
	@SuppressWarnings("unchecked")
	Student save(Student student);
	
	
}
