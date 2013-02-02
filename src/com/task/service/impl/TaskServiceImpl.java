package com.task.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.task.common.PropertyManager;
import com.task.entity.Attachment;
import com.task.entity.SimpleTask;
import com.task.entity.Task;
import com.task.entity.TaskPackage;
import com.task.service.DataAccess;
import com.task.service.TaskService;

public class TaskServiceImpl implements TaskService{
	
	public static final int FILE_SUFFIX_LENGTH = PropertyManager.getInteger("file_suffix_length" , 15);
	public static final String ATTACHMENT_PATH = PropertyManager.getString("attachment_path" , "C:/a526726/taskmanagement");
	
	/*
	 create table attachment_desc(
	code int(10) auto_increment primary key,
	attachmentClass int(12) not null,
	attachPath varchar(255) not null,
	realFilename varchar(255) not null,
	physicalFilename varchar(255) not null,
	comments varchar(2048)
);
	 */
	private DataAccess dataAccess = new MySqlDataAccess();
	
	@Override
	public Attachment createNewAttachment(int attachClass , String  attachmentPath , String realFilename , String comments , int size) throws ClassNotFoundException, SQLException {
		int sequenceNumber = dataAccess.getNextSequenceNumber();
		String physicalFilename = realFilename + getNumberString(sequenceNumber , FILE_SUFFIX_LENGTH);
		Attachment attachment = new Attachment(attachClass , attachmentPath , realFilename , physicalFilename , comments , size , new Timestamp(System.currentTimeMillis()));
		return attachment;
	}


	@Override
	public void deleteAttachment(int attachmentDescCode) throws ClassNotFoundException, SQLException {
		dataAccess.deleteAttachment(attachmentDescCode);
	}

	@Override
	public void deleteSimpleTask(int simpleTaskCode) throws ClassNotFoundException, SQLException {
		dataAccess.deleteSimpleTask(simpleTaskCode);
	}

	@Override
	public void deleteTaskPackage(int taskPackageCode) throws ClassNotFoundException, SQLException {
		dataAccess.deleteTaskPackage(taskPackageCode);
	}

	@Override
	public Attachment getAttachment(int attachmentDescCode) throws ClassNotFoundException, SQLException {
		return dataAccess.getAttachment(attachmentDescCode);
	}

	@Override
	public List<Attachment> getAttachments(int attachmentIndex) throws ClassNotFoundException, SQLException {
		return dataAccess.getAttachments(attachmentIndex);
	}

	@Override
	public SimpleTask getSimpleTask(int simpleTaskCode) throws ClassNotFoundException, SQLException {
		return dataAccess.getSimpleTask(simpleTaskCode);
	}

	@Override
	public List<Task> getSubTasks(int taskPackageCode) throws ClassNotFoundException, SQLException {
		return dataAccess.getSubTasks(taskPackageCode);
	}

	@Override
	public List<Task> getTasksByStartTime(Timestamp Timestamp) throws ClassNotFoundException, SQLException {
		return dataAccess.getTasksByStartTime(Timestamp);
	}

	@Override
	public List<Task> getTasksByStartTime(Timestamp startTimestamp, Timestamp endTimestamp) throws ClassNotFoundException, SQLException {
		return dataAccess.getTasksByStartTime(startTimestamp, endTimestamp);
	}

	@Override
	public int saveAttachment(Attachment attachment) throws ClassNotFoundException, SQLException {
		return dataAccess.saveAttachment(attachment);
	}

	@Override
	public int saveTask(Task task) throws ClassNotFoundException, SQLException {
		return dataAccess.saveTask(task);
	}

	private String getNumberString(int number , int length) {
		int numbers[] = new int[length];
		for (int i=0 ; i<length ; i++) {
			numbers[i] = 0;
		}
		
		int index = length - 1;
		while (number > 0) {
			int temp = number % 10;
			number %= 10;
			number /= 10;
			numbers[index--] = temp;
		}
		
		StringBuilder str = new StringBuilder(20);
		for (int i=0 ; i<length ; i++) {
			str.append(numbers[i]);
		}
		return str.toString();
	}

/*
 * Timestamp createTime , Timestamp startTime , Timestamp endTime , String description , int attachment , int parentCode(non-Javadoc)
 */
	@Override
	public Task createTask(int parent, Timestamp startTimestamp,
			Timestamp endTimestamp, String description, int attachmentCode , int type) {
		Task task = null;
		if (type == 0) {
			//TODO attachmentID
			task = new SimpleTask(new Timestamp(System.currentTimeMillis()) , startTimestamp , endTimestamp , description , attachmentCode , parent , null , false);
		}
		else {
			task = new TaskPackage(new Timestamp(System.currentTimeMillis()) , startTimestamp , endTimestamp , description , attachmentCode , parent , null , false);
		}
		return task;
	}


	@Override
	public Task getTask(int code) throws ClassNotFoundException, SQLException {
		return dataAccess.getTask(code);
	}


	@Override
	public void finishedTask(int code) throws ClassNotFoundException,
			SQLException {
		dataAccess.finishedTask(code);
	}


	@Override
	public void saveAttachment(int attachmentIndex, Attachment attachment)
			throws ClassNotFoundException, SQLException {
		dataAccess.saveAttachment(attachmentIndex, attachment);
	}


	@Override
	public void deleteAttachment(int attachmentIndex, Attachment attachment)
			throws ClassNotFoundException, SQLException {
		dataAccess.deleteAttachment(attachmentIndex, attachment);
	}
}
