<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
</head>
<body>
<center>
	<form action="login" method="post">
		用户名：<input type="text" name="username" /><br /> 
		密码：<input type="password" name="password" /> 
		<input type="submit" value="登陆" />
		<a href="regist">注册</a>
	</form>
</center>
</body>
</html>