package org.eason.workflowengine.ui.resteasy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.TaskEvent;

@XmlRootElement
public class VoTaskEvent {
	private Long taskId;
    private Long taskWorkFlowId;
    private String taskEventName;
    private String taskEventData;
    private long taskEventTime;
    
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
	
	public TaskEvent toTaskEvent(){
		TaskEvent taskEvent = new TaskEvent();
		taskEvent.setTaskId(taskId);
		taskEvent.setWorkflowId(taskWorkFlowId);
		taskEvent.setName(taskEventName);
		taskEvent.setData(taskEventData);
		taskEvent.setEventTime(taskEventTime);
		return taskEvent;
	}
}
