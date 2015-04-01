
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<form action="login" method="post">
	<table>
		<tr>
			<td><label for="username">Uživatelské jméno:</label></td>
			<td><input type="text" id="username" name="username" autofocus required/>
			</td>
		</tr>
		<tr>
			<td><label for="password">Heslo:</label></td>
			<td><input type="password" id="password" name="password" required/></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="submit">Přihlásit</button>
			</td>
		</tr>
	</table>
</form>
</body>
</html>