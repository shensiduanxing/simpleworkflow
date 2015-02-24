package org.eason.workflowengine.ui.resteasy.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.Workflow;
import org.eason.workflowengine.domain.common.model.WorkflowStatus;

@XmlRootElement
public class VoWorkflow {
	
	private String name;
    private String data;
    private List<VoTask> voTasks;
    private List<VoSequenceFlow> voSequenceFlows;
    private String appId;
    private String appTaskQueue;
    
    private long scheduledStartTime;

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
	public List<VoTask> getVoTasks() {
		return voTasks;
	}

	public void setVoTasks(List<VoTask> voTasks) {
		this.voTasks = voTasks;
	}

	@XmlElement
	public List<VoSequenceFlow> getVoSequenceFlows() {
		return voSequenceFlows;
	}

	public void setVoSequenceFlows(List<VoSequenceFlow> voSequenceFlows) {
		this.voSequenceFlows = voSequenceFlows;
	}

	@XmlElement
	public long getScheduledStartTime() {
		return scheduledStartTime;
	}

	public void setScheduledStartTime(long scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}
	
	@XmlElement
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@XmlElement
	public String getAppTaskQueue() {
		return appTaskQueue;
	}

	public void setAppTaskQueue(String appTaskQueue) {
		this.appTaskQueue = appTaskQueue;
	}

	public Workflow toWorkFlow(){
    	Workflow workFlow = new Workflow();
    	workFlow.setName(this.name);
    	workFlow.setStatus(WorkflowStatus.CREATED);
    	workFlow.setCreateTime(System.currentTimeMillis());
    	workFlow.setScheduledStartTime(this.scheduledStartTime);
    	workFlow.setStartTime(0L);
    	workFlow.setEndTime(0L);
    	workFlow.setAppId(appId);
    	workFlow.setAppTaskQueue(appTaskQueue);
    	return workFlow;
    }
    
}
