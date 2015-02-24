package org.eason.workflowengine.ui.resteasy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.TaskEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@XmlRootElement
public class SaveTaskEventResponse {
	private Long id;
	private Long taskId;
    private Long taskWorkFlowId;
    private String taskEventName;
    private String taskEventData;
    private long taskEventTime;
    private long createTime;
    
    @XmlElement
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@XmlElement
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	@XmlElement
	public Long getTaskWorkFlowId() {
		return taskWorkFlowId;
	}
	public void setTaskWorkFlowId(Long taskWorkFlowId) {
		this.taskWorkFlowId = taskWorkFlowId;
	}
	
	@XmlElement
	public String getTaskEventName() {
		return taskEventName;
	}
	public void setTaskEventName(String taskEventName) {
		this.taskEventName = taskEventName;
	}
	
	@XmlElement
	public String getTaskEventData() {
		return taskEventData;
	}
	public void setTaskEventData(String taskEventData) {
		this.taskEventData = taskEventData;
	}
	
	@XmlElement
	public long getTaskEventTime() {
		return taskEventTime;
	}
	public void setTaskEventTime(long taskEventTime) {
		this.taskEventTime = taskEventTime;
	}
	
	@XmlElement
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
    
	public static SaveTaskEventResponse fromTaskEvent(TaskEvent taskEvent){
		SaveTaskEventResponse saveTaskEventResponse = new SaveTaskEventResponse();
		saveTaskEventResponse.setId(taskEvent.getId());
		saveTaskEventResponse.setTaskId(taskEvent.getTaskId());
		saveTaskEventResponse.setTaskWorkFlowId(taskEvent.getWorkflowId());
		saveTaskEventResponse.setTaskEventName(taskEvent.getName());
		saveTaskEventResponse.setTaskEventData(taskEvent.getData());
		saveTaskEventResponse.setTaskEventTime(taskEvent.getEventTime());
		saveTaskEventResponse.setCreateTime(taskEvent.getCreateTime());
		return saveTaskEventResponse;
	}
	
	public String toJson(){
		GsonBuilder gsonBuilder=new GsonBuilder().setPrettyPrinting();
		Gson gson=gsonBuilder.create();
		return gson.toJson(this);
	}
	
	public TaskEvent toTaskEvent(){
		TaskEvent taskEvent = new TaskEvent();
		taskEvent.setId(id);
		taskEvent.setName(taskEventName);
		taskEvent.setTaskId(taskId);
		taskEvent.setWorkflowId(taskWorkFlowId);
		return taskEvent;
	}
}
