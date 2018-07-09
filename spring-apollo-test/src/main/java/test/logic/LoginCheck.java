package test.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.env.Environment;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;

import test.dao.Dao;
import test.model.DataModel;

public class LoginCheck {

	public static String check(String username,String password){
        try {
            Connection conn = new Dao().getConnection();
            PreparedStatement p = conn.prepareStatement("select * from userinfo where username=? and password=?");
            p.setString(1, username);
            p.setString(2, password);
            ResultSet rs = p.executeQuery();
            if(rs.next()){
                String user_name = rs.getString("username");
                Dao.close(rs, p, conn);
                return user_name;
            }
            Dao.close(rs, p, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
	
	public void test() {

		System.out.println("test---------");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");		
		DataModel xmls = ctx.getBean(DataModel.class);
		
		String password = xmls.getPassword();
		System.out.println("password = "+password);
	}
	
	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
		
		DataModel xmls = ctx.getBean(DataModel.class);
		
		System.out.println("DataModel Demo. Input any key except quit to print the values. Input quit to exit.");
	    while (true) {
	      System.out.print("> ");
	      String input = new BufferedReader(new InputStreamReader(System.in, Charsets.UTF_8)).readLine();
	      if (!Strings.isNullOrEmpty(input) && input.trim().equalsIgnoreCase("quit")) {
	        System.exit(0);
	      }

	      System.out.println(xmls.toString());
	    }
	}
	
}
