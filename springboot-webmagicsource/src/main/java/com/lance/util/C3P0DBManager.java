package com.lance.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;



public class C3P0DBManager {
	private static DataSource ds;
	
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	private static C3P0DBManager dbManager = null;

	static
	{
		// c3p0自动识别配置文件classpath:c30p.properties 创建数据源
		ds = new ComboPooledDataSource();
	}

	/*
	 * 构造器，获取连接
	 * */
	private C3P0DBManager() 
	{
		getConnection();
		
	}
	/*获取C3P0 实例
	 * */
	public static C3P0DBManager getInstance() 
	{
		if(dbManager==null)
		{
			dbManager = new C3P0DBManager();
		}
		return dbManager ;
	}
	
	/* 获取连接
	 * */
	public static Connection getConnection() 
	{
		Connection conn = null;
		try 
		{
			// 先尝试从 当前线程上获取绑定的连接
			conn = tl.get();
			if (conn == null) 
			{ 	//如果线程上没有绑定连接,从数据源获取
				conn = ds.getConnection();
				//绑定连接到当前线程
				tl.set(conn);
			}
		} catch (Exception e) 
		{
			System.out.println("数据库连接异常");
		}
		return conn;
	}
	
	/*
	 * 关闭连接
	 * */
	public static void closeConnection(Connection conn)
	{
		try {
			if (conn != null)
			{
				conn.close();
			}
		} catch (Exception e)
		{
			System.out.println("数据库关闭异常");
		} finally {
			tl.remove(); // 千万注意，解除当前线程上绑定的链接（从容器中移除对应当前线程的链接）
		}
	}
	
	
	/*
	 * 查询数据
	 * */
	
	public static ResultSet executeQuery(Connection conn ,String sql,
			Object paras[]) throws Exception 
	{
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			setPreparedStatementParas(pstmt, paras);
			ResultSet rs = pstmt.executeQuery();
			return rs;
		} catch (SQLException ex) 
		{
			throw new Exception("** Error in executeQuery():" + ex.getMessage()
					+ "**", ex);
		}
	}
	
	/*
	 * PreparedStatement 参数列表与SQL ？绑定
	 * */
	
	public static void setPreparedStatementParas(PreparedStatement pstmt,Object paras[]) throws SQLException
	{
		
		if (paras != null && paras.length > 0)
		{
			for (int i = 0; i < paras.length; i++)
			{
				if (paras[i] == null) 
				{
					// 注意，SQL不支持java.sql.Types.NULL，所以使用java.sql.Types.VARCHAR代替
					pstmt.setNull(i + 1, java.sql.Types.VARCHAR);
				} 
				else
				{
					// begin from index 1
					pstmt.setObject(i + 1, paras[i]);
				}
			}
		}
	}
	
	
	/*
	 * 更新数据
	 * */
	
	public static int executeUpdate(Connection conn ,String sql,
			Object paras[]) throws Exception 
	{
		int num = 0 ;
		PreparedStatement pstmt= null ;
		try
		{
			 pstmt = conn.prepareStatement(sql);
			setPreparedStatementParas(pstmt, paras);
			num = pstmt.executeUpdate();
			return num;
		} catch (SQLException ex) 
		{
			throw new Exception("** Error in executeUpdate():" + ex.getMessage()
					+ "**", ex);
		}finally
		{
			pstmt.close();
		}
	}
	
	
	/*
	 * 事务手动设置
	 * */

	public static void setAutoCommit(Connection conn, boolean autoCommit) {
		try {
			// 得到当前线程上绑定连接开启事务
			conn = tl.get();
			if (conn != null) 
			{
				conn.setAutoCommit(autoCommit);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * 事务回滚
	 * */
	public static void rollback(Connection conn) {
		try 
		{
			conn = tl.get();
			if (conn != null) 
			{
				conn.rollback();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 事务提交
	 * */
	public static void commit(Connection conn) {
		try
		{
			conn = tl.get();
			if (conn != null) 
			{
				conn.commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
