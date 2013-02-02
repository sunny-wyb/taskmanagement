package com.task.service;

import com.task.service.impl.TaskServiceFactoryImpl;

public class TaskServiceFactory {
	
	public static TaskService getTaskService(){
		return TaskServiceFactoryImpl.getTaskService();
	}
}
