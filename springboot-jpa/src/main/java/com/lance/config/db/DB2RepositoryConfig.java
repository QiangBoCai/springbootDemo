package com.lance.config.db;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement  //开启事务管理
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryDB2",
                       transactionManagerRef = "transactionManagerDB2", 
					   basePackages = {"com.lance.db.dao.db2"
					   })
public class DB2RepositoryConfig {

	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	@Qualifier("db2DataSource")
	private DataSource db2DataSource;
	

	
	/*
	 * 获取配置里面 列名生成策略
	 * HibernateSettings类其实就是配置列名生成策略的，我们已经在yml里配置过了，这里直接new 一个空类过去就行
	 * @return
	 */
	private Map<String, Object> getVendorProperties() {
        return jpaProperties.getHibernateProperties(new HibernateSettings());
    }
	
	
	
	/*
     * 设置实体类所在位置
     */
	@Bean(name = "entityManagerFactoryDB2")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryDB2(EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(db2DataSource)
				.properties(getVendorProperties())
				.packages("com.lance.db.entity.db2"
				) // 设置实体类所在位置
				.persistenceUnit("db2PersistenceUnit")
				.build();
	}
	
	
	/*
	 * 注入实体类管理
	 * @param builder
	 * @return
	 */
	@Bean(name = "entityManagerDB2")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactoryDB2(builder).getObject().createEntityManager();
		
	}
	

	/*
	 * 注入事务管理
	 */
	@Bean(name = "transactionManagerDB2")
	PlatformTransactionManager transactionManagerDB2(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(entityManagerFactoryDB2(builder).getObject());
	}

}
