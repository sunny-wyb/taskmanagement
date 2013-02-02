package com.task.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.task.entity.Attachment;
import com.task.entity.SimpleTask;
import com.task.entity.Task;

public interface TaskService {
	/*
	 * public int saveAttachment(Attachment attachment)  throws ClassNotFoundException, SQLException;
	public List<Attachment> getAttachments(int attachmentIndex) throws ClassNotFoundException, SQLException;
	public Attachment getAttachment(int attachmentDescCode) throws ClassNotFoundException, SQLException;
	public void deleteAttachment(int attachmentDescCode) throws ClassNotFoundException, SQLException;
	
	public List<Task> getTasks(Timestamp Timestamp) throws ClassNotFoundException, SQLException;
	public List<Task> getTasks(Timestamp startTimestamp , Timestamp endTimestamp) throws ClassNotFoundException, SQLException;
	public List<Task> getSubTasks(int taskPackageCode) throws ClassNotFoundException, SQLException;
	public SimpleTask getSimpleTask(int simpleTaskCode) throws ClassNotFoundException, SQLException;
	public void deleteTaskPackage(int taskPackageCode) throws ClassNotFoundException, SQLException;
	public void deleteSimpleTasks(List<Integer> simpleTaskCodes) throws ClassNotFoundException, SQLException;
	public void deleteSimpleTask(int simpleTaskCode) throws ClassNotFoundException, SQLException;
	public int saveTask(Task task) throws ClassNotFoundException, SQLException;
	
	public int getNextSequenceNumber() throws ClassNotFoundException, SQLException;
	 */
	public Attachment createNewAttachment(int attachClass , String attachmentPath , String realFilename , String comments , int size) throws ClassNotFoundException, SQLException;
	public int saveAttachment(Attachment attachment) throws ClassNotFoundException, SQLException ;
	public List<Attachment> getAttachments(int attachmentIndex) throws ClassNotFoundException, SQLException ;
	public Attachment getAttachment(int attachmentDescCode) throws ClassNotFoundException, SQLException ;
	public void deleteAttachment(int attachmentDescCode) throws ClassNotFoundException, SQLException ;
	
	public List<Task> getTasksByStartTime(Timestamp Timestamp) throws ClassNotFoundException, SQLException ;
	public List<Task> getTasksByStartTime(Timestamp startTimestamp , Timestamp endTimestamp) throws ClassNotFoundException, SQLException ;
	public List<Task> getSubTasks(int taskPackageCode) throws ClassNotFoundException, SQLException ;
	public SimpleTask getSimpleTask(int simpleTaskCode) throws ClassNotFoundException, SQLException ;
	public Task getTask(int code) throws ClassNotFoundException, SQLException ;
	
	/*
	 * private int code;
	private int parentCode;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	private String description;
	private int attachment;
	 */
	public Task createTask(int parent , Timestamp startTimestamp , Timestamp endTimestamp , String description , int attachmentCode , int taskType) throws ClassNotFoundException, SQLException ;
	public int saveTask(Task task) throws ClassNotFoundException, SQLException ;
	public void deleteSimpleTask(int simpleTaskCode) throws ClassNotFoundException, SQLException ;
	public void deleteTaskPackage(int taskPackageCode) throws ClassNotFoundException, SQLException ;

	public void finishedTask(int code) throws ClassNotFoundException, SQLException ;
	
	public void saveAttachment(int attachmentIndex , Attachment attachment) throws ClassNotFoundException, SQLException;
	public void deleteAttachment(int attachmentIndex , Attachment attachment) throws ClassNotFoundException, SQLException;
}
