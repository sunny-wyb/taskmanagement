package org.apache.jsp.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.task.entity.*;

public final class attachment_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("\r\n");

	String CONTENT_ROOT = request.getContextPath();
	Map content = (Map)session.getAttribute("__CONTENT__");
	String status = (String)content.get("__ATTACHMENT_STATUS__");

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");

	if ("preUpload".endsWith(status)) {

      out.write("\r\n");
      out.write("\r\n");
      out.write("<form action=\"taskcontroller\" method=\"post\"  enctype=\"multipart/form-data\">\r\n");
      out.write("\t\t<label for=\"filename_1\">File: </label>\r\n");
      out.write("        <input id=\"filename_1\" type=\"file\" name=\"filename_1\" size=\"50\"/><br/>\r\n");
      out.write("\t\t<input type=\"submit\" value=\"upload\" name=\"command\" />\r\n");
      out.write("</form>\r\n");
      out.write("<hr />\r\n");

	}

      out.write("\r\n");
      out.write("\r\n");

	if ("preComment".endsWith(status)) {

      out.write("\r\n");
      out.write("\t<form action=\"taskcontroller\" method=\"post\">\r\n");
      out.write("\t\t<input type='hidden' name='code' value='");
      out.print(content.get("code"));
      out.write("' />\r\n");
      out.write("\t\t<input type='hidden' name='attachmentDescCode' value='");
      out.print(content.get("attachmentDescCode") );
      out.write("' />\r\n");
      out.write("\t\t<label>attachmentDescCode :</label>\r\n");
      out.write("\t\t <label>");
      out.print(content.get("attachmentDescCode") );
      out.write("</label><br />\r\n");
      out.write("\t\t <label>File :</label>\r\n");
      out.write("\t\t <label>");
      out.print(content.get("attachmentFilename") );
      out.write("</label><br />\r\n");
      out.write("\t\t <label>size :</label>\r\n");
      out.write("\t\t <label>");
      out.print(content.get("size") );
      out.write("</label><br />\r\n");
      out.write("\t\t comments:<input type='text' name='comments' />\r\n");
      out.write("\t\t<input type='submit' name='command' value='comments'/>\r\n");
      out.write("\t</form>\r\n");
      out.write("\t<hr />\r\n");

	}

      out.write("\r\n");
      out.write("\r\n");

	if ("viewAttachment".equals(status)) {

      out.write(" \r\n");
      out.write("\t<a href=\"taskcontroller?command=preUpload&code=");
      out.print(content.get("code"));
      out.write("\">Upload</a><br />\r\n");
      out.write("\t<a href=\"taskcontroller?command=getTask&parentCode=");
      out.print(content.get("parentCode"));
      out.write("\">Back</a>\r\n");
      out.write("\t<hr />\r\n");

	}

      out.write("\r\n");
      out.write("\r\n");
      out.write("<table border = '1'>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td>code</td>\r\n");
      out.write("\t\t<td>attachmentClass</td>\r\n");
      out.write("\t\t<td>attachPath</td>\r\n");
      out.write("\t\t<td>realFilename</td>\r\n");
      out.write("\t\t<td>physicalFilename</td>\r\n");
      out.write("\t\t<td>size</td>\r\n");
      out.write("\t\t<td>uploadTime</td>\r\n");
      out.write("\t\t<td>comments</td>\r\n");
      out.write("\t\t<td>operation</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t\r\n");
      out.write("\t");

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
	
      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
