package org.eason.workflowengine.ui.resteasy.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.IWorkflow;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.Workflow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@XmlRootElement
public class GetWorkflowResponse {
	private Long id;
	private String name;
    
    private List<Task> tasks;
    
    private String appId;
    private String appTaskQueue;

	private long createTime;
    private long scheduledStartTime;
    private long dispatchedTime;
    private long startTime;
    private long doneTime;
    private long timeoutTime;
    
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
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
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
	public long getTimeoutTime() {
		return timeoutTime;
	}

	public void setTimeoutTime(long timeoutTime) {
		this.timeoutTime = timeoutTime;
	}

	@XmlElement
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    public static GetWorkflowResponse fromWorkFlow(Workflow workFlow){
    	GetWorkflowResponse getWorkFlowResponse = new GetWorkflowResponse();
    	getWorkFlowResponse.setId(workFlow.getId());
    	getWorkFlowResponse.setName(workFlow.getName());
    	//getWorkFlowResponse.setTasks(workFlow.getTasks());
    	getWorkFlowResponse.setStatus(workFlow.getStatus());
    	getWorkFlowResponse.setCreateTime(workFlow.getCreateTime());
    	getWorkFlowResponse.setScheduledStartTime(workFlow.getScheduledStartTime());
    	getWorkFlowResponse.setDoneTime(workFlow.getEndTime());
    	return getWorkFlowResponse;
    }
    
    public Workflow toWorkflow(){
    	Workflow workflow = new Workflow();
    	workflow.setId(id);
    	workflow.setName(name);
    	workflow.setAppId(appId);
    	workflow.setAppTaskQueue(appTaskQueue);
    	workflow.setStatus(status);
    	workflow.setScheduledStartTime(scheduledStartTime);
    	workflow.setCreateTime(createTime);
    	workflow.setDispatchTime(dispatchedTime);
    	workflow.setStartTime(startTime);
    	workflow.setEndTime(doneTime);
    	return workflow;
    }
    
    public String toJson(){
    	GsonBuilder gsonBuilder=new GsonBuilder().setPrettyPrinting();
    	gsonBuilder.registerTypeAdapter(GetWorkflowResponse.class, new GetWorkFlowResponseSerializer());
    	Gson gson=gsonBuilder.create();
    	String jsonGetWorkFlowResponse=gson.toJson(this);
    	return jsonGetWorkFlowResponse;
    }
    
    public static GetWorkflowResponse fromJson(String jsonGetWorkFlowResponse){
    	GsonBuilder gsonBuilder=new GsonBuilder().setPrettyPrinting();
    	gsonBuilder.registerTypeAdapter(GetWorkflowResponse.class, new GetWorkFlowResponseSerializer());
    	Gson gson=gsonBuilder.create();
    	GetWorkflowResponse getWorkFlowResponse = gson.fromJson(jsonGetWorkFlowResponse, GetWorkflowResponse.class);
    	return getWorkFlowResponse;
    }
}
