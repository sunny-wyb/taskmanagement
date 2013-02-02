package org.apache.jsp.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.io.*;
import com.task.entity.*;

public final class tasks_jsp extends org.apache.jasper.runtime.HttpJspBase
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

	String CONTENT_ROOT = request.getContextPath();
	Map content = (Map)session.getAttribute("__CONTENT__");

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(CONTENT_ROOT);
      out.write("/web/css/style.css\" />\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<form action=\"taskcontroller\" method=\"get\">\r\n");

			
			out.println("<input type = 'hidden' name = 'parentCode' value = '" + content.get("parentCode") + "'>");
			
		
      out.write(" \r\n");
      out.write("\t\t<input type=\"submit\" value=\"fillInfo\" name=\"command\" />\r\n");
      out.write("</form>\r\n");
      out.write("<table border='1' width='100%'>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td>code</td>\r\n");
      out.write("\t\t<td>Type</td>\r\n");
      out.write("\t\t<td>startTime</td>\r\n");
      out.write("\t\t<td>endTime</td>\r\n");
      out.write("\t\t<td>finishTime</td>\r\n");
      out.write("\t\t<td>description</td>\r\n");
      out.write("\t\t<td>attachment</td>\r\n");
      out.write("\t\t<td>operation</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t");

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
		
	
      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
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
