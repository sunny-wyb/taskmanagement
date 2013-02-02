<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*, java.io.*, com.task.entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String CONTENT_ROOT = request.getContextPath();
	Map content = (Map)session.getAttribute("__CONTENT__");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<%=CONTENT_ROOT%>/web/css/style.css" />


<title>Insert title here</title>
</head>
<body>

<form action="taskcontroller" method="get">
<%
			
			out.println("<input type = 'hidden' name = 'parentCode' value = '" + content.get("parentCode") + "'>");
			
		%> 
		<input type="submit" value="fillInfo" name="command" />
</form>
<table border='1' width='100%'>
	<tr>
		<td>code</td>
		<td>Type</td>
		<td>startTime</td>
		<td>endTime</td>
		<td>finishTime</td>
		<td>description</td>
		<td>attachment</td>
		<td>operation</td>
	</tr>
	<%
		List<Task> tasks = (List<Task>) content.get("tasks");
	
		for (Task task : tasks) {
			if (task.isFinished()) {
				out.println("<tr class = 'finished'>");
			}
			else {
				out.println("<tr>");
			}
			out.println("<td class='code'>" + task.getCode() + "</td>");
			out.println("<td class='type'>" + task.isTaskpackage()
					+ "</td>");
			out.println("<td>" + task.getStartTime() + "</td>");
			out.println("<td>" + task.getEndTime() + "</td>");
			out.println("<td class='finishTime'>" + task.getFinishTime()
					+ "</td>");
			out.println("<td>" + task.getDescription() + "</td>");
			out.println("<td class='attachment'>" + task.getAttachment()
					+ "</td>");
			out.println("<td>");
			if (task.isSimpleTask()) {
				out
					.println("<a href = 'taskcontroller?command=finish&code=" + task.getCode() + "'>Finish</a>");
			}
			out
					.println("<a href = 'taskcontroller?command=modify&code=" + task.getCode() + "'>Modify</a>");
			out
					.println("<a href = 'taskcontroller?command=delete&code=" + task.getCode() + "'>Delete</a>");
			if (task.isTaskpackage()) {
				out
					.println("<a href = 'taskcontroller?command=extend&code=" + task.getCode() + "'>Extend</a>");
			}
			out.println("<a href = 'taskcontroller?command=viewAttachment&code=" + task.getCode() +"'>ViewAttachment</a>");
			out.println("</tr>");
		}
		
	%>
</table>

</body>
</html>