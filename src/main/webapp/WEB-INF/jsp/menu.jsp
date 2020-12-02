<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/aobcp/src/main/webapp/css/index.css">
<title>Insert title here</title>
</head>
<body>
	<h1>MENU</h1>
	<form action="/aobcp/menu" method="post">
		<table>
			<tr>
				<td>Please select a purpose</td>
				<td><select name="purpose">
						<option value="0"></option>		
						<option value="1">Your AO address list</option>
						<option value="2">Create AO address</option>
						<option value="3">All transaction history</option>
						<option value="4">Send coin</option>
						<option value="5">Your coin history</option>
				</select></td>
			</tr>
		</table>
		<input type="submit" value="result" />
	</form>
</body>
</html>