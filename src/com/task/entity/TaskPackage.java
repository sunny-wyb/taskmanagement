package com.task.entity;

import java.sql.Timestamp;

public class TaskPackage implements Task{
	private int code;
	private int parentCode;
	private Timestamp createTime;
	private Timestamp startTime;
	private Timestamp endTime;
	private String description;
	private int attachment;
	private int taskType = 1;
	private boolean isFinished;
	private Timestamp finishTime;
	
	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public TaskPackage(int code , Timestamp createTime , Timestamp startTime , Timestamp endTime , String description , int attachment , int parentCode , Timestamp finishTime , boolean isFinished) {
		this.code = code;
		this.createTime = createTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.attachment = attachment;
		this.parentCode = parentCode;
		this.taskType = 1;
		this.isFinished = isFinished;
		this.finishTime = finishTime;
	}
	
	public TaskPackage(Timestamp createTime , Timestamp startTime , Timestamp endTime , String description , int attachment , int parentCode , Timestamp finishTime , boolean isFinished) {
		this(-1 , createTime , startTime , endTime , description , attachment , parentCode , finishTime , isFinished);
	}
	
	public int getParentCode() {
		return parentCode;
	}
	public void setParentCode(int parentCode) {
		this.parentCode = parentCode;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAttachment() {
		return attachment;
	}
	public void setAttachment(int attachment) {
		this.attachment = attachment;
	}
	@Override
	public boolean isSimpleTask() {
		return false;
	}
	@Override
	public boolean isTaskpackage() {
		return true;
	}
}
