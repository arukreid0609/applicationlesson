<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Product" %>
<% Product product = (Product)session.getAttribute("product"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="">
		<tr><th>製品名</th><td><%=product.getName() %></td></tr>
		<tr><th>価格</th><td><%=product.getPrice() %></td></tr>
	</table>
	<form action="Update" method="post">
		製品名：<br>
		<input type="text" name="name" value="<%=product.getName()%>"><br>
		価格：<br>
		<input type="number" name="price" value="<%=product.getPrice()%>"><br>
		<input type="submit" value="更新">
	</form>
</body>
</html>