package org.eason.workflowengine.ui.resteasy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.TaskEvent;

import com.google.gson.Gson;

@XmlRootElement
public class GetTaskEventResponse {
    
	private Long id;
	private Long taskId;
    private Long workflowId;
    private String name;
    private String data;
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
	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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

	public static GetTaskEventResponse fromJson(String jsonGetTaskEventResponse){
    	GetTaskEventResponse getTaskEventResponse = new Gson().fromJson(jsonGetTaskEventResponse, GetTaskEventResponse.class);
        return getTaskEventResponse;
    }
    
    public TaskEvent toTaskEvent(){
    	TaskEvent taskEvent = new TaskEvent();
    	taskEvent.setId(id);
    	taskEvent.setName(name);
    	taskEvent.setTaskId(taskId);
    	taskEvent.setWorkflowId(workflowId);
    	taskEvent.setData(data);
    	taskEvent.setEventTime(taskEventTime);
    	taskEvent.setCreateTime(createTime);
    	return taskEvent;
    }
    
}
