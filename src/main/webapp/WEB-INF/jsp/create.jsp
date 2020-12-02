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
	<h1>Create AO ADDRESS</h1>
	<h2>Create a private key and public key and register them in the DB.</h2>
	<h2>The AO address is a string generated from the public key.</h2>
	<h2>When requesting the transfer of AO coins, you must provide your AO address to the other party.</h2>
	<h2>If you want to create a new AO address, click the "create" button.</h2>
	<h2>Press the "Your AO address list" button on the menu screen and confirm that the new AO address has been created.</h2>
	<form action="/aobcp/create" method="post">
		<input type="submit" value="create" onClick="done()"/>
	</form>
	
	<script>
        function done(){
        	alert("You have generated an address. Moves to the menu page.");
        }
    </script>
</body>
</html>