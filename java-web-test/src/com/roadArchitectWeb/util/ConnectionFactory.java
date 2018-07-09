package com.roadArchitectWeb.util;

import java.sql.Connection;
import java.sql.DriverManager;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
public class ConnectionFactory {
	
	private static Connection conn = null;
	private static String driver;
	private static String dburl;
	private static String user;
	private static String password;
	private static ConnectionFactory connectionFactory = new ConnectionFactory();
	private static Config config = ConfigService.getAppConfig();
	
	static{
		driver = config.getProperty("driver-class-name", null);
		dburl = config.getProperty("datasource.url",null);
		user = config.getProperty("datasource.user",null);
		password = config.getProperty("datasource.password",null);
	}
	
	private ConnectionFactory(){
		
	}
	
	public Connection getConnection(){
		try{ 
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static ConnectionFactory getInstance(){
		return connectionFactory;
	}
}
