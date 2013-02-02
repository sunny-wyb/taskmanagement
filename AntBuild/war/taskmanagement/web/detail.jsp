<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.* , com.task.entity.* , java.io.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- 
		code int(10) auto_increment primary key,
	parentCode int(10),
	createTime datetime,
	startTime datetime,
	endTime dateTime,
	finishTime dateTime,
	description varchar(2048),
	attachment int(10),
	taskType int(1),
	isFinished boolean
	 -->
	<% 
		Map content = (Map)session.getAttribute("__CONTENT__");
		Task task = (Task)content.get("__TASK__");
		PrintWriter writer = response.getWriter();
		writer.println("code : " + task.getCode() + "<br />");
		writer.println("parentCode : " + task.getParentCode() + "<br />");
		writer.println("createTime : " + task.getCreateTime() + "<br />");
		writer.println("startTime : " + task.getStartTime() + "<br />");
		writer.println("endTime : " + task.getEndTime() + "<br />");
		if (task.getFinishTime() == null) {
			writer.println("finishTime : " + "" + "<br />");
		}
		else {
			writer.println("finishTime : " + task.getFinishTime() + "<br />");
		}
		writer.println("description : " + task.getDescription() + "<br />");
		writer.println("attachment : " + task.getAttachment() + "<br />");
		writer.println("taskType : " + task.getTaskType() + "<br />");
		writer.println("isFinished : " + task.isFinished() + "<br />");
	%>
</body>
</html>