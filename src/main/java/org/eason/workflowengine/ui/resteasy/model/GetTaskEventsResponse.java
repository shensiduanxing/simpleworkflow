package org.eason.workflowengine.ui.resteasy.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.TaskEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@XmlRootElement
public class GetTaskEventsResponse {
	
	private List<TaskEvent> taskEvents;

	@XmlElement
	public List<TaskEvent> getTaskEvents() {
		return taskEvents;
	}

	public void setTaskEvents(List<TaskEvent> taskEvents) {
		this.taskEvents = taskEvents;
	}
	
	public static GetTaskEventsResponse fromJson(String jsonGetTaskEventsResponse){
		GetTaskEventsResponse getTaskEventsResponse = new Gson().fromJson(jsonGetTaskEventsResponse, GetTaskEventsResponse.class); 
		return getTaskEventsResponse;
	}

}
