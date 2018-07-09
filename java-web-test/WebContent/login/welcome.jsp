<%@ page language="java" 
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<%
	String userName = request.getAttribute("userName").toString();
	if(null!=request.getAttribute("login") && !"".equals(request.getAttribute("login").toString()))
		out.println(userName+" success,欢迎来到登陆界面");
	else
		out.println(userName+" fail,请回到登陆界面");
%>
</body>
</html>