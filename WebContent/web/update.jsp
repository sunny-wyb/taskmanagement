<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Map content = (Map)session.getAttribute("__CONTENT__");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action = 'taskcontroller' method = 'post'>
		<table>
			<input type = 'hidden' name = 'code' value = '<%=((Map)(session.getAttribute("__CONTENT__"))).get("code")%>' />
			
			<tr>
				<td>StartTime</td>
				<td><input type = 'text' name = 'startTime' value = '<%=content.get("startTime") %>'/></td>
			</tr>
			<tr>
				<td>EndTime</td>
				<td><input type = 'text' name = 'endTime'  value = '<%=content.get("endTime") %>'/></td>
			</tr>
			<tr>
				<td>Description</td>
				<td><input type = 'text' name = 'description'  value = '<%=content.get("description") %>'/></td>
			</tr>
			<tr>
				<td>taskType</td>
				<td><input type = 'text' name = 'taskType'  value = '<%=content.get("taskType") %>'/></td>
			</tr>
			<tr>
				<td><input type = 'submit' name = 'command' value = 'updateTask'></td>
		</table>
	</form>
</body>
</html>