package com.roadArchitectWeb.control;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListenerUtil implements ServletContextListener {
	public void contextInitialized(ServletContextEvent servletcontextevent) {
		System.out.println("-------");
		System.setProperty("app.id", "java-web-test");
		System.setProperty("env", "fat");
	}
	public void contextDestroyed(ServletContextEvent servletcontextevent)
    {
    }
	/*public void contextInitialzed(ServletContextEvent sce) {
		System.out.println("-------");
		System.setProperty("app.id", "java-web-test");
		System.setProperty("env", "fat");
	}*/

}
