<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="dto.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
	UserInfoDTO u = (UserInfoDTO) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AO Blockchain Platform</title>
</head>
<body>
	<h1><%= u.getUsername().toUpperCase() %>'s AO ADDRESS</h1>
	<h2>Please copy the address to use and use it.</h2>
	
	<ul>
<%
	if(u.getAddrList() != null && u.getAddrList().size()>0){
%>
<%
	for (int i=0; i<u.getAddrList().size(); i++) {
%>
		<li><%=u.getAddrList().get(i)%></li>
<% } %>
<% }else{ %>
	<li>アドレスを持っていませんね、作ってくだしあ</li>
<% } %>

	</ul>

	<a href="/aobcp/">
    <button type="button">back</button>
	</a>
</body>
</html>