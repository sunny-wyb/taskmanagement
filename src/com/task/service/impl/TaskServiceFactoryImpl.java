package com.task.service.impl;

import com.task.service.TaskService;

public class TaskServiceFactoryImpl{
	public static TaskService getTaskService() {
		return new TaskServiceImpl();
	}
}
