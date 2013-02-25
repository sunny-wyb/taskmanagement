<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.* , com.task.entity.* , java.io.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<form action = 'taskcontroller' method = 'post'>
	<table>
			<input type = 'hidden' name = 'parentCode' value = '<%=((Map)(session.getAttribute("__CONTENT__"))).get("parentCode")%>' />
			<tr>
				<td>StartTime</td>
				<td><input type = 'text' name = 'startTime' /></td>
			</tr>
			<tr>
				<td>EndTime</td>
				<td><input type = 'text' name = 'endTime' /></td>
			</tr>
			<tr>
				<td>Description</td>
				<td><input type = 'text' name = 'description' /></td>
			</tr>
			<tr>
				<td>taskType</td>
				<td><input type = 'text' name = 'taskType' /></td>
			</tr>
			<tr>
				<td><input type = 'submit' name = 'command' value = 'createNewTask'></td>
		</table>
	</form>
</body>
</html>
