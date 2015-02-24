package org.eason.workflowengine.ui.resteasy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.TaskResult;

import com.google.gson.Gson;

@XmlRootElement
public class GetTaskResultResponse {
	
	private TaskResult taskResult;
	
	@XmlElement
	public TaskResult getTaskResult() {
		return taskResult;
	}
	public void setTaskResult(TaskResult taskResult) {
		this.taskResult = taskResult;
	}
	
	public static GetTaskResultResponse fromJson(String jsonGetTaskResultResponse){
		return new Gson().fromJson(jsonGetTaskResultResponse, GetTaskResultResponse.class);
	}
    
}
