package test.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppConfig implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		System.setProperty("app.id", "java-web-test");
		System.setProperty("env", "fat");
		System.out.println("初始化apollo配置信息");
	}
	
	

}
