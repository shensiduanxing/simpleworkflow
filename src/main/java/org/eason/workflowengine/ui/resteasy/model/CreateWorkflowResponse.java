package org.eason.workflowengine.ui.resteasy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.Workflow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@XmlRootElement
public class CreateWorkflowResponse {
	private Long id;
	private String name;
	
	private String appId;
	private String appTaskQueue;

	private long createTime;
    private long scheduledStartTime;
    private long dispatchedTime;
    private long startTime;
    private long doneTime;
    
    private String status;

    @XmlElement
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@XmlElement
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@XmlElement
	public long getScheduledStartTime() {
		return scheduledStartTime;
	}

	public void setScheduledStartTime(long scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}

	@XmlElement
	public long getDispatchedTime() {
		return dispatchedTime;
	}

	public void setDispatchedTime(long dispatchedTime) {
		this.dispatchedTime = dispatchedTime;
	}

	@XmlElement
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@XmlElement
	public long getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(long doneTime) {
		this.doneTime = doneTime;
	}

	@XmlElement
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
	public String toJson(){
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String jsonCreateWorkFlowRequest = gson.toJson(this);
    	return jsonCreateWorkFlowRequest;
    }
    
    public static CreateWorkflowResponse fromWorkFlow(Workflow workFlow){
    	CreateWorkflowResponse createWorkFlowResponse = new CreateWorkflowResponse();
    	createWorkFlowResponse.setId(workFlow.getId());
    	createWorkFlowResponse.setName(workFlow.getName());
    	createWorkFlowResponse.setStatus(workFlow.getStatus());
    	createWorkFlowResponse.setCreateTime(workFlow.getCreateTime());
    	createWorkFlowResponse.setScheduledStartTime(workFlow.getScheduledStartTime());
    	createWorkFlowResponse.setStartTime(workFlow.getStartTime());
    	createWorkFlowResponse.setDoneTime(workFlow.getEndTime());
    	return createWorkFlowResponse;
    }
    
    public Workflow toWorkflow(){
    	Workflow workflow = new Workflow();
    	workflow.setId(id);
    	workflow.setName(name);
    	workflow.setStatus(status);
    	workflow.setScheduledStartTime(scheduledStartTime);
    	workflow.setCreateTime(createTime);
    	workflow.setAppId(appId);
    	workflow.setAppTaskQueue(appTaskQueue);
    	return workflow;
    }
}
