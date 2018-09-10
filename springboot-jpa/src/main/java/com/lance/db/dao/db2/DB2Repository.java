package com.lance.db.dao.db2;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lance.db.entity.db2.StudentEntity;

public interface  DB2Repository  extends JpaRepository<StudentEntity, Long> {
	@SuppressWarnings("unchecked")
	StudentEntity save(StudentEntity student);
}
