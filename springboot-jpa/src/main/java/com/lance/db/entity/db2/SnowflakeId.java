package com.lance.db.entity.db2;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * 自定义主键自动生成策略
 * 
 * */
public class SnowflakeId implements IdentifierGenerator{

	public SnowflakeId() {
    }
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// TODO Auto-generated method stub
		return   getId() + "";
	}

	private String getId() {

		return null;
	}


}

