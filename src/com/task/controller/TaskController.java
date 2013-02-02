package com.task.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.task.entity.Attachment;
import com.task.entity.Task;
import com.task.service.TaskService;
import com.task.service.TaskServiceFactory;
import com.task.service.impl.TaskServiceImpl;

public class TaskController extends HttpServlet {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -661759381330017347L;
	private static final Logger logger = Logger.getLogger(TaskController.class);
	
	private static String contextPath = "";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		contextPath = config.getServletContext().getContextPath();
		PropertyConfigurator.configure(config.getServletContext().getRealPath(".") + "/WEB-INF/cfg/log4j.properties");
		System.out.println(System.getProperty("user.dir"));
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		logger.info(request.getParameterMap().toString());
		if (!isMultipart) {
			String command = request.getParameter("command");
			if (command == null || command.length() == 0) {
				command = "getTask";
			}

			if ("getTask".equals(command)) {
				// TODO consinder index.jsp
				int parentCode;
				if (request.getParameter("parentCode") == null) {
					parentCode = 2;
				} else {
					parentCode = Integer.parseInt(request
							.getParameter("parentCode"));
				}

				TaskService service = TaskServiceFactory.getTaskService();
				try {
					List<Task> tasks = service.getSubTasks(parentCode);
					Map<String, Object> content = new HashMap<String, Object>();
					content.put("parentCode", parentCode);
					content.put("tasks", tasks);
					HttpSession session = request.getSession();
					session.setAttribute("__CONTENT__", content);
					request.getRequestDispatcher("web/tasks.jsp").forward(
							request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if ("fillInfo".equals(command)) {
				int parentCode = Integer.parseInt(request
						.getParameter("parentCode"));
				Map<String, Object> content = new HashMap<String, Object>();
				content.put("parentCode", parentCode);
				HttpSession session = request.getSession();
				session.setAttribute("__CONTENT__", content);
				try {
					request.getRequestDispatcher("web/create.jsp").forward(
							request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if ("createNewTask".equals(command)) {
				int parentCode = Integer.parseInt(request
						.getParameter("parentCode"));
				Timestamp startTimestamp = Timestamp.valueOf(request
						.getParameter("startTime"));
				Timestamp endTimestamp = Timestamp.valueOf(request
						.getParameter("endTime"));
				String description = request.getParameter("description");
				int taskType = Integer.parseInt(request
						.getParameter("taskType"));
				TaskService service = TaskServiceFactory.getTaskService();
				try {
					Task task = service.createTask(parentCode, startTimestamp,
							endTimestamp, description, 0, taskType);
					service.saveTask(task);
					response.sendRedirect("taskcontroller?command=getTask&parentCode="
							+ parentCode);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("finish".equals(command)) {
				int code = Integer.parseInt(request.getParameter("code"));
				TaskService service = TaskServiceFactory.getTaskService();
				try {
					service.finishedTask(code);
					Task task = service.getTask(code);
					int parentCode = task.getParentCode();
					response.sendRedirect("taskcontroller?command=getTask&parentCode="
							+ parentCode);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("delete".equals(command)) {
				int code = Integer.parseInt(request.getParameter("code"));
				TaskService service = TaskServiceFactory.getTaskService();
				Task task;
				try {
					task = service.getTask(code);
					if (task.isSimpleTask())
						service.deleteSimpleTask(task.getCode());
					else
						service.deleteTaskPackage(task.getCode());

					int parentCode = task.getParentCode();
					response.sendRedirect("taskcontroller?command=getTask&parentCode="
							+ parentCode);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("extend".equals(command)) {
				int parentCode = Integer.parseInt(request.getParameter("code"));
				try {
					response.sendRedirect("taskcontroller?command=getTask&parentCode="
							+ parentCode);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("viewAttachment".equals(command) || ("preUpload".equals(command))) {
				int code = Integer.parseInt(request.getParameter("code"));
				TaskService service = TaskServiceFactory.getTaskService();
				Task task;
				try {
					task = service.getTask(code);
					List<Attachment> attachments = service.getAttachments(task.getAttachment());
					Map content = new HashMap();
					content.put("code", code);
					content.put("parentCode", task.getParentCode());
					content.put("__ATTACHMENT_STATUS__", command);
					content.put("attachments", attachments);
					HttpSession session = request.getSession();
					session.setAttribute("__CONTENT__", content);
					try {
						request.getRequestDispatcher("web/attachment.jsp").forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if ("preComment".equals(command)) {
				
				//TODO session is the new or not?
				HttpSession session = request.getSession();
				Map content = (Map)session.getAttribute("__CONTENT__");
				content.put("__ATTACHMENT_STATUS__", command);
				session.setAttribute("__CONTENT__", content);
				try {
					request.getRequestDispatcher("web/attachment.jsp").forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if ("comments".equals(command)) {
				int code = Integer.parseInt(request.getParameter("code"));
				int attachmentDescCode = Integer.parseInt(request.getParameter("attachmentDescCode"));
				String comments = request.getParameter("comments");
				TaskService service = TaskServiceFactory.getTaskService();
				Attachment attachment = null;
				try {
					attachment = service.getAttachment(attachmentDescCode);
					Task task = service.getTask(code);
					attachment.setComments(comments);
					service.saveAttachment(attachment);
					service.saveAttachment(task.getAttachment() , attachment);
					response.sendRedirect("taskcontroller?command=viewAttachment&code=" + code);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if ("deleteAttachment".endsWith(command)) {
				int code = Integer.parseInt(request.getParameter("code"));
				int attachmentDescCode = Integer.parseInt(request.getParameter("attachmentDescCode"));
				TaskService service = TaskServiceFactory.getTaskService();
				try {
					Task task = service.getTask(code);
					Attachment attachment = service.getAttachment(attachmentDescCode);
					service.deleteAttachment(task.getAttachment() , attachment);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					response.sendRedirect("taskcontroller?command=viewAttachment&code=" + code);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if ("downloadAttachment".equals(command)) {
				response.reset();
				int attachmentDescCode = Integer.parseInt(request.getParameter("attachmentDescCode"));
				TaskService service = TaskServiceFactory.getTaskService();
				Attachment attachment;
				try {
					attachment = service.getAttachment(attachmentDescCode);
					File file = new File(attachment.getAttachPath() , attachment.getPhysicalFilename());
					if (file.exists()) {
						response.setContentType("application/x-msdownload");
						response.addHeader("Content-Disposition", "attachment; filename=\""
								+ attachment.getRealFilename() + "\"");
						int fileLength = (int) file.length();
						response.setContentLength(fileLength);
						if (fileLength != 0) {
							InputStream in = new FileInputStream(file);
							byte[] buffer = new byte[4096];
							ServletOutputStream servletOS = response.getOutputStream();
							int readLength = -1;

							while ((readLength = in.read(buffer)) != -1) {
								servletOS.write(buffer, 0, readLength);
							}

							in.close();
							servletOS.flush();
							servletOS.close();
						}
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			if (isMultipart) {
				TaskService service = TaskServiceFactory.getTaskService();
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				try {
					List<FileItem> items = upload.parseRequest(request);
					Iterator iterator = items.iterator();
					while (iterator.hasNext()) {
						FileItem item = (FileItem) iterator.next();

						if (!item.isFormField()) {
							String fileName = item.getName();
							// TODO filesize int is it ok?
							int size = (int) item.getSize();
							String root = TaskServiceImpl.ATTACHMENT_PATH;
							File path = new File(root + "/uploads");
							if (!path.exists()) {
								boolean status = path.mkdirs();
							}
							
							Attachment attachment = service.createNewAttachment(0, path.getAbsolutePath() , fileName, "", size);
							int attachmentDescCode = service.saveAttachment(attachment);
							File uploadedFile = new File(path + "/"
									+ attachment.getPhysicalFilename());
							item.write(uploadedFile);
							//TODO session is the new or not?
							HttpSession session = request.getSession();
							Map content = (Map)session.getAttribute("__CONTENT__");
							content.put("__ATTACHMENT_STATUS__", "comments");
							content.put("attachmentDescCode", attachmentDescCode);
							content.put("attachmentFilename", fileName);
							content.put("size", size);
							response.sendRedirect("taskcontroller?command=preComment");
						}
					}
				} catch (FileUploadException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
