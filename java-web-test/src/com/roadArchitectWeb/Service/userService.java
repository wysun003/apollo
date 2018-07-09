package com.roadArchitectWeb.Service;

import java.sql.Connection;
import java.sql.SQLException;

import com.roadArchitectWeb.dao.UserDao;
import com.roadArchitectWeb.dao.UserDaoImpl;
import com.roadArchitectWeb.entity.UserInfo;

public class userService {
	UserDao userDao = new UserDaoImpl();
	public  boolean checkUser(Connection conn,UserInfo userInfo) throws SQLException
	{
		Boolean status = false;
		try {
			conn.setAutoCommit(false);
			status = userDao.queryUser(conn, userInfo);
			conn.commit();
		 } catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
		return status;
	}
	
}
