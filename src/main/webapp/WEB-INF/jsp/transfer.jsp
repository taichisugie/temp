<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AO Blockchain Platform</title>
</head>
<body>
	<h1>Remittance page</h1>
	<h2>Please enter the remittance address and amount.</h2>
	<form action="/aobcp/send" method="post">
		<table>
			<tr>
				<td>Your AO Address </td>
				<td><input type="text" name="sender_addr" /></td>
			</tr>
			<tr>
				<td>Destination AO address</td>
				<td><input type="text" name="receiver_addr" /></td>
			</tr>
			<tr>
				<td>Amount(unit:AO)</td>
				<td><input type="text" name="amount" /></td>
			</tr>
		</table>
		<input type="submit" value="send" />
	</form>
</body>
</html>