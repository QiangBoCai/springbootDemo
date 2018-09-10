package com.lance.config.db;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 为数据源建立 配置类，且支持JdbcTemplate的crud操作
 *  
 * 实际项目中一般都会用到(spring data jpa(hibernate jpa)) + jdbcTemplate混用。
 * 
 *  ①JdbcTemplate与Repository使用同样的DataSource,保证了 connection 的唯一性
 *  ②事务交由JpaTransactionManager管理 
 *  
 * 
 * 
 */
@Configuration
public class DB1DataSourceConfig {

	/*
	 * 根据配置db1.datasource产生一个DruidDataSource 
	 */
	

	private static final String DATABASE_DRIVER = "db1.datasource.driverClassName";
	private static final String DATABASE_URL = "db1.datasource.url";
	private static final String DATABASE_USER = "db1.datasource.username";
	private static final String DATABASE_PASSWORD = "db1.datasource.password";
	private static final String INITIALSIZE = "db1.datasource.initialSize";
	private static final String MINIDLE = "db1.datasource.minIdle";
	private static final String MAXACTIVE = "db1.datasource.maxActive";
	
	@Resource
	private Environment env;
	
	@Bean(name = "db1DataSource") //注入db1数据源
	@Primary //在有多个数据源的情况下，必须要指定其中一个为主数据源
    @Qualifier("db1DataSource") 
	//置文件里的属性名是不需要必须写成spring.datasource.xxx的形式的，写成a.b.c.url也没有问题，只要在配置bean时指定前缀为a.b.c
    public DataSource db1DataSource() {
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
    
    @Bean(name = "db1JdbcTemplate")
    public JdbcTemplate db1JdbcTemplate(@Qualifier("db1DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    

	    
}
