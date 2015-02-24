package org.eason.workflowengine.ui.resteasy.model;

import java.util.List;

import org.eason.workflowengine.domain.common.model.Task;

import com.google.gson.Gson;

public class GetTasksResponse {
	
    private List<Task> tasks;

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
    
	public static GetTasksResponse fromJson(String jsonGetTasksResponse){
		return new Gson().fromJson(jsonGetTasksResponse, GetTasksResponse.class);
	}
    
    
}
