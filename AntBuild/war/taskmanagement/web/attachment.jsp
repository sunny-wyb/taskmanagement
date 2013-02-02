<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "java.util.*, com.task.entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String CONTENT_ROOT = request.getContextPath();
	Map content = (Map)session.getAttribute("__CONTENT__");
	String status = (String)content.get("__ATTACHMENT_STATUS__");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
	if ("preUpload".endsWith(status)) {
%>

<form action="taskcontroller" method="post"  enctype="multipart/form-data">
		<label for="filename_1">File: </label>
        <input id="filename_1" type="file" name="filename_1" size="50"/><br/>
		<input type="submit" value="upload" name="command" />
</form>
<hr />
<%
	}
%>

<%
	if ("preComment".endsWith(status)) {
%>
	<form action="taskcontroller" method="post">
		<input type='hidden' name='code' value='<%=content.get("code")%>' />
		<input type='hidden' name='attachmentDescCode' value='<%=content.get("attachmentDescCode") %>' />
		<label>attachmentDescCode :</label>
		 <label><%=content.get("attachmentDescCode") %></label><br />
		 <label>File :</label>
		 <label><%=content.get("attachmentFilename") %></label><br />
		 <label>size :</label>
		 <label><%=content.get("size") %></label><br />
		 comments:<input type='text' name='comments' />
		<input type='submit' name='command' value='comments'/>
	</form>
	<hr />
<%
	}
%>

<%
	if ("viewAttachment".equals(status)) {
%> 
	<a href="taskcontroller?command=preUpload&code=<%=content.get("code")%>">Upload</a><br />
	<a href="taskcontroller?command=getTask&parentCode=<%=content.get("parentCode")%>">Back</a>
	<hr />
<%
	}
%>

<table border = '1'>
	<tr>
		<td>code</td>
		<td>attachmentClass</td>
		<td>attachPath</td>
		<td>realFilename</td>
		<td>physicalFilename</td>
		<td>size</td>
		<td>uploadTime</td>
		<td>comments</td>
		<td>operation</td>
	</tr>
	
	<%
		List<Attachment> attachments = (List<Attachment>)content.get("attachments");
		for (Attachment attachment : attachments) {
			out.println("<tr>");
			out.println("<td>" + attachment.getCode() + "</td>");
			out.println("<td>" + attachment.getAttachmentClass() + "</td>");
			out.println("<td>" + attachment.getAttachPath() + "</td>");
			out.println("<td>" + attachment.getRealFilename() + "</td>");
			out.println("<td>" + attachment.getPhysicalFilename() + "</td>");
			out.println("<td>" + attachment.getSize() + "</td>");
			out.println("<td>" + attachment.getUploadTime() + "</td>");
			out.println("<td>" + attachment.getComments() + "</td>");
			out.println("<td>");
			out.println("<a href='taskcontroller?command=deleteAttachment&code=" + content.get("code") +"&attachmentDescCode=" + attachment.getCode() +"'>Delete</a>");
			out.println("<a href='taskcontroller?command=downloadAttachment&code=" + content.get("code") +"&attachmentDescCode=" + attachment.getCode() +"'>Download</a>");
			out.println("</td>");
			out.println("</tr>");
		}
	%>
</table>
</body>
</html>