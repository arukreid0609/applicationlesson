<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Product,java.util.*" %>
<% 
List<Product> list = (List<Product>)application.getAttribute("list");
String err = (String)request.getAttribute("err");
String msg = (String)request.getAttribute("msg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>練習問題7</title>
</head>
<body>
	<p>商品を登録してください。</p>
	<% if(err != null){ %>
	<p><%=err %></p>
	<% } %>
	<% if(msg != null){ %>
	<p><%=msg %></p>
	<% } %>

	<form action="Main" method="post">
		製品名：<br>
		<input type="text" name="name"><br>
		価格：<br>
		<input type="number" name="price"><br>
		<input type="submit" value="登録">
	</form>

	<% if(!list.isEmpty()){ %>
	<table border="">
		<tr><th>製品名</th><th>価格</th><th>登録日</th></tr>
		<% for(Product p:list){ %>
		<tr><td><%=p.getName() %></td><td><%=p.getPrice() %></td><td><%=p.getUpdated() %></td></tr>
		<% } %>
	</table>
	<% } %>
</body>
</html>