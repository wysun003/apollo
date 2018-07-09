package com.roadArchitectWeb.Test;

import java.sql.Connection;
import java.sql.SQLException;

import com.roadArchitectWeb.dao.UserDao;
import com.roadArchitectWeb.dao.UserDaoImpl;
import com.roadArchitectWeb.entity.UserAddr;
import com.roadArchitectWeb.util.ConnectionFactory;

public class UserDaoTest {
	public  static void main(String[] args) throws SQLException{
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
	
		UserDao userDao = new UserDaoImpl();
		UserAddr userAddr = new UserAddr();
//		userAddr.setCountry("China");
//		userAddr.setCity("hangzhou");
//		userAddr.setUserid("10");
//		userDao.save(conn,userAddr);
		
//		userAddr.setId(5);
//		userAddr.setCity("nanjing");
//		userAddr.setCountry("USA");
//		userAddr.setUserid("11");
//		userDao.update(conn, userAddr);
		
//		userDao.delete(conn, 4);
		
//		userAddr = userDao.singalSelect(conn,5);
	}
}
