# apollo
不同类型项目接入apollo配置中心 demo

一、spring boot:

首先在启动类main方法中添加apollo相关配置属性
System.setProperty("app.id", "insurancegateway");
System.setProperty("env", "fat");

并在类上添加如下注解：

（1）只有默认的application namespace

添加如下注解：
@Configuration  
@EnableApolloConfig()

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

Config config = ConfigService.getAppConfig();//默认namespace
driver = config.getProperty("driver-class-name", null);
Config configDatabase = ConfigService.getConfig("database");//自己创建的namespace为database
dirver2 = configDatabase.getProperty("driver",null);

三、spring项目

1.在spring.xml文件中配置namespace时，启动项目本地C:\opt\data目录下生成approperties缓存文件
2.不在spring.xml中配置namespace，api获取时才在C:\opt\data目录下生成缓存文件
同二中普通java项目接入的配置一样，用监听器设置相关配置，利用API使用方法

注意事项：

现有的demo暂未实现监听功能，如数据库连接改变时（可以实时获取到新的值），但是目前项目中暂未实现用新值进行重新连接数据库
