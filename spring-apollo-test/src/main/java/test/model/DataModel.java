package test.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataModel {
	private static final Logger logger = LoggerFactory.getLogger(DataModel.class);
	private String driver;
	private String user;
	private String password;
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		logger.info("updating driver, old value: {}, new value: {}", this.driver, driver);
		this.driver = driver;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		logger.info("updating user, old value: {}, new value: {}", this.user, user);
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		logger.info("updating password, old value: {}, new value: {}", this.password, password);
		this.password = password;
	}
	
	@Override
	public String toString() {
	    return String.format("[DataModel] driver: %s, user: %s, password: %s", driver, user, password);
	}

}
