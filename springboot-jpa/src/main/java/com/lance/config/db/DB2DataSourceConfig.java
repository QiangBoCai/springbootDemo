package com.lance.config.db;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 为数据源建立 配置类，且支持JdbcTemplate
 *  
 * 实际项目中一般都会用到orm(spring data jpa) + jdbcTemplate混用。
 * 
 *  ①JdbcTemplate与Repository使用同样的DataSource,保证了 connection 的唯一性
 *  ②事务交由JpaTransactionManager管理 
 *  
 * 
 * 
 */
@Configuration
public class DB2DataSourceConfig {

	/*
	 * 根据配置db2.datasource产生一个DruidDataSource 
	 */
	

	private static final String DATABASE_DRIVER = "db2.datasource.driverClassName";
	private static final String DATABASE_URL = "db2.datasource.url";
	private static final String DATABASE_USER = "db2.datasource.username";
	private static final String DATABASE_PASSWORD = "db2.datasource.password";
	private static final String INITIALSIZE = "db2.datasource.initialSize";
	private static final String MINIDLE = "db2.datasource.minIdle";
	private static final String MAXACTIVE = "db2.datasource.maxActive";
	
	@Resource
	private Environment env;



	
	/*
	 * 根据配置db2.datasource产生一个DataSource 
	 */
    @Bean(name = "db2DataSource")//注入db2数据源
    @Qualifier("db2DataSource")
    public DataSource db2DataSource() {
		DruidDataSource source = new DruidDataSource();
		source.setDriverClassName(env.getRequiredProperty(DATABASE_DRIVER));
		source.setUrl(env.getRequiredProperty(DATABASE_URL));
		source.setUsername(env.getRequiredProperty(DATABASE_USER));
		source.setPassword(env.getRequiredProperty(DATABASE_PASSWORD));
		
		source.setInitialSize(Integer.valueOf(env.getRequiredProperty(INITIALSIZE)));
		source.setMinIdle(Integer.valueOf(env.getRequiredProperty(MINIDLE)));
		source.setMaxActive(Integer.valueOf(env.getRequiredProperty(MAXACTIVE)));
		return source;
    }
    
    
    
    /*
     * JdbcTemplate支持
     * 为不同JdbcTemplate注入对应的datasource 
     */
    
    
    @Bean(name = "db2JdbcTemplate")
    public JdbcTemplate db2JdbcTemplate(@Qualifier("db2DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

	    
}
