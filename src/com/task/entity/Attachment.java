package com.task.entity;

import java.sql.Timestamp;

public class Attachment {
	/*
	 * code int(10) auto_increment primary key,
	attachmentClass int(12) not null,
	attachPath varchar(255) not null,
	realFilename varchar(255) not null,
	physicalFilename varchar(255) not null,
	comments varchar(2048)
	 */
	
	private int code;
	private int attachmentClass;
	private String attachPath;
	private String realFilename;
	private String physicalFilename;
	private long size;
	private Timestamp uploadTime;
	private String comments;
	
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Attachment(int code , int attachmentClass , String attachPath , String realFilename , String physicalFilename , String comments , long size , Timestamp uploadTime) {
		this.code = code;
		this.attachmentClass = attachmentClass;
		this.attachPath = attachPath;
		this.realFilename = realFilename;
		this.physicalFilename = physicalFilename;
		this.comments = comments;
		this.size = size;
		this.uploadTime = uploadTime;
	}
	
	public Attachment(int attachmentClass , String attachPath , String realFilename , String physicalFilename , String comments , long size , Timestamp uploadTime) {
		this(-1 , attachmentClass , attachPath , realFilename , physicalFilename , comments , size , uploadTime);
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getAttachmentClass() {
		return attachmentClass;
	}
	public void setAttachmentClass(int attachmentClass) {
		this.attachmentClass = attachmentClass;
	}
	public String getAttachPath() {
		return attachPath;
	}
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	public String getRealFilename() {
		return realFilename;
	}
	public void setRealFilename(String realFilename) {
		this.realFilename = realFilename;
	}
	public String getPhysicalFilename() {
		return physicalFilename;
	}
	public void setPhysicalFilename(String physicalFilename) {
		this.physicalFilename = physicalFilename;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
