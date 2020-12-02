<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AO Blockchain Platform</title>
</head>
<body>
	<h1>Welcome to AO Blockchain Platform.</h1>
	<h2>Enter your ID and password.</h2>
	<form action="/aobcp/" method="post">
		<table>
			<tr>
				<td>User Name</td>
				<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<td>PASSWORD</td>
				<td><input type="password" name="pwd" /></td>
			</tr>
		</table>
		<input type="submit" value="LOGIN" />
	</form>
	
	<form action="/aobcp/registUser" method="get">
		<input type="submit" value="新規登録はこちら" />
	</form>
</body>
</html>