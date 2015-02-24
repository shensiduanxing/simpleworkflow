package org.eason.workflowengine.ui.resteasy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.Workflow;
import org.eason.workflowengine.domain.common.model.WorkflowStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@XmlRootElement
public class CreateWorkflowRequest {

    private VoWorkflow voWorkflow;

    @XmlElement
    public VoWorkflow getVoWorkflow() {
		return voWorkflow;
	}

	public void setVoWorkflow(VoWorkflow voWorkflow) {
		this.voWorkflow = voWorkflow;
	}

	public static CreateWorkflowRequest fromJson(String jsonCreateWorkFlowRequest){
    	Gson gson = new GsonBuilder().create();
    	CreateWorkflowRequest createWorkFlowRequest = gson.fromJson(jsonCreateWorkFlowRequest, CreateWorkflowRequest.class);
    	return createWorkFlowRequest;
    }
    
	public String toJson(){
    	Gson gson = new GsonBuilder().create();
    	return gson.toJson(this);
    }
    
}
