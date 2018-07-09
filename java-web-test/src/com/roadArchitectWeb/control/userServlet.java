package com.roadArchitectWeb.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.roadArchitectWeb.Service.userService;
import com.roadArchitectWeb.entity.UserInfo;
import com.roadArchitectWeb.util.ConnectionFactory;

public class userServlet extends HttpServlet {
	userService userService = new userService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("userName");
		String passWord = req.getParameter("passWord");
		UserInfo user = new UserInfo();
		user.setUsername(userName);
		user.setPassword(passWord);
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		try {
			if(userService.checkUser(conn, user)){
				System.out.println("userServlet.doPost()"+"登陆成功");
				RequestDispatcher rd = req.getRequestDispatcher("/login/welcome.jsp");
				req.setAttribute("userName", userName);
				req.setAttribute("passWord", passWord);
				req.setAttribute("login", "1");
				rd.forward(req, resp);
			}else{
				System.out.println("userServlet.doPost()"+"登陆失败");
				RequestDispatcher rd = req.getRequestDispatcher("/login/welcome.jsp");
				req.setAttribute("userName", userName);
				req.setAttribute("passWord", passWord);
				rd.forward(req, resp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
