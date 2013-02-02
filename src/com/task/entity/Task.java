package com.task.entity;

import java.sql.Timestamp;


public interface Task {
	public boolean isTaskpackage();
	public boolean isSimpleTask();
	
	public int getCode();
	public Timestamp getCreateTime();
	public Timestamp getStartTime();
	public Timestamp getEndTime();
	public int getParentCode();
	public String getDescription();
	public int getTaskType();
	public int getAttachment();
	public boolean isFinished();
	public Timestamp getFinishTime();
	
	public void setFinished(boolean isFinished);
	public void setFinishTime(Timestamp finishTime);
}
