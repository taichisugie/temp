<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AO Blockchain Platform</title>
</head>
<body>
	<h1>ユーザー登録画面</h1>
	<h2>ユーザー名とパスワードください</h2>
	<form action="/aobcp/registUser" method="post">
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
		<input type="submit" value="登録" />
	</form>
</body>
</html>