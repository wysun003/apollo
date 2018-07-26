# apollo
不同类型项目接入apollo配置中心 demo

一、spring boot:
在bootstrap阶段注入默认application namespace的配置示例，在application.properties文件中添加如下配置：

apollo.bootstrap.enabled = true

首先在启动类main方法中添加apollo相关配置属性
System.setProperty("app.id", "insurancegateway");
System.setProperty("env", "fat");

并在类上添加如下注解：

（1）只有默认的application namespace

添加如下注解：
@Configuration  
@EnableApolloConfig

（2）除了默认的application，还有其他的多个namespace（如dev,database，dubbo)

@Configuration  
@EnableApolloConfig({"dev","database"，"dubbo"})//application默认会加载，可以不用添加在此，不区分大小写

使用方法：

@Autowired
private Environment env;
env.getProperty("spring.profiles.active")；

二、普通java项目接入：

(非maven项目)引入jar包：apollo-client.jar、apollo-core.jar、slf4j-api.jar(一定要加这个包，否则初始化apollo时会报错)、guava.jar、aopalliance.jar

项目中添加监听器用于启动项目时设置apollo相关配置属性，如：
System.setProperty("app.id", "java-web-test");//设置应用项目appId
System.setProperty("env", "fat");//设置使用环境

使用方法（API使用方式）：

Config config = ConfigService.getAppConfig();//获取默认namespace
driver = config.getProperty("driver-class-name", null);
Config configDatabase = ConfigService.getConfig("database");//获取非的namespace，例如 database
dirver2 = configDatabase.getProperty("driver",null);

三、spring项目

1.在spring.xml文件中配置namespace，相关配置如下：
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:apollo="http://www.ctrip.com/schema/apollo"
    xmlns:p="http://www.springframework.org/schema/p"  
    xmlns:context="http://www.springframework.org/schema/context"  
    xmlns:mvc="http://www.springframework.org/schema/mvc"  
    xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans.xsd    
                        http://www.springframework.org/schema/context    
                        http://www.springframework.org/schema/context/spring-context.xsd    
                        http://www.springframework.org/schema/mvc    
                        http://www.springframework.org/schema/mvc/spring-mvc.xsd
                        http://www.ctrip.com/schema/apollo
                        http://www.ctrip.com/schema/apollo.xsd">
    <apollo:config/>
    <apollo:config namespaces="database,test"/>   
    ...
    ...
</beans>

同二中普通java项目接入的配置一样，用监听器设置初始化相关appid、env 配置，利用API使用方法。

注意事项：

现有的demo暂未实现监听功能，如数据库连接改变时（可以实时获取到新的值），但是目前项目中暂未实现用新值进行重新连接数据库

----------------------------------------------------------------------------------------------------------------------------
apollo上配置数据库URL连接时，不能用转义字符 &amp; 
