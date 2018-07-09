package com.roadArchitectWeb.control;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

public class Test {
	public static void main(String[] args) {
		System.setProperty("app.id", "insurancegateway");
    	System.setProperty("env", "fat");
		System.out.println(System.getProperty("app.id"));
		System.out.println(System.getProperty("env"));
		Config config = ConfigService.getAppConfig();
	}

}
