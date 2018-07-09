package test.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

public class Dao {
	
	// 获取数据库连接
    public Connection getConnection(){
    	
        Connection conn = null;
        String driver;
    	String dburl;
    	String user;
    	String password;
    	
    	System.out.println(System.getProperty("app.id"));
    	System.out.println(System.getProperty("env"));
    	
    	Config config = ConfigService.getAppConfig();
    	
    	driver = config.getProperty("driver-class-name", null);
		dburl = config.getProperty("datasource.url",null);
		user = config.getProperty("datasource.user",null);
		password = config.getProperty("datasource.password",null);
		
		System.out.println("driver:"+driver+"   dburl:"+dburl+"   user:"+user+"   password:"+password);
		
		Config configDatabase = ConfigService.getConfig("database");
		System.out.println("database:driver= "+configDatabase.getProperty("driver",null));
		System.out.println("database:url= "+configDatabase.getProperty("url",null));
		System.out.println("database:user= "+configDatabase.getProperty("user",null));
		System.out.println("database:password= "+configDatabase.getProperty("password",null));
		
		Config configTest = ConfigService.getConfig("test");
		System.out.println("test= "+configTest.getProperty("test",null));

        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(dburl,user,password);//大家分享代码的时候也不要暴露自己的数据库密码，这样是非常不安全的
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("数据库驱动加载出错");
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据库出错");
        }
        return conn;
    }
     //关闭相关通道
    public static void close(ResultSet rs,PreparedStatement p,Connection conn){
        try{
            if(!rs.isClosed()){
                rs.close();
            }
            if(!p.isClosed()){
                p.close();
            }
            if(!conn.isClosed()){
                conn.close();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据关闭出错");
        }
    }
    //关闭相关通道
    public static void close(PreparedStatement p,Connection conn){
        try{
            if(!p.isClosed()){
                p.close();
            }
            if(!conn.isClosed()){
                conn.close();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据关闭出错");
        }
    }
}
