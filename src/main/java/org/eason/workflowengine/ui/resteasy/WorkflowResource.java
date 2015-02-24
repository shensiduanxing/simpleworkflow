package org.eason.workflowengine.ui.resteasy;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.bl.impl.SequenceFlowService;
import org.eason.workflowengine.domain.common.bl.impl.TaskEventService;
import org.eason.workflowengine.domain.common.bl.impl.TaskService;
import org.eason.workflowengine.domain.common.bl.impl.WorkflowService;
import org.eason.workflowengine.domain.common.model.SequenceFlow;
import org.eason.workflowengine.domain.common.model.TaskStatus;
import org.eason.workflowengine.domain.common.model.TaskWithBLOBs;
import org.eason.workflowengine.domain.common.model.Workflow;
import org.eason.workflowengine.ui.resteasy.exception.ErrorResponse;
import org.eason.workflowengine.ui.resteasy.model.GetWorkflowResponse;
import org.eason.workflowengine.ui.resteasy.model.ResponseHelper;
import org.eason.workflowengine.ui.resteasy.model.SuccessResponse;
import org.eason.workflowengine.ui.resteasy.model.VoSequenceFlow;
import org.eason.workflowengine.ui.resteasy.model.VoTask;
import org.eason.workflowengine.ui.resteasy.model.VoWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

@Controller
@Path(WorkflowResource.WORKFLOW_ENGINE_URL)
public class WorkflowResource {
    public static final String  WORKFLOW_ENGINE_URL = "/ws/workflow";
    
    private Logger logger = Logger.getLogger(WorkflowResource.class);
    
    @Autowired
    WorkflowService workflowService;
    
    @Autowired
    TaskService taskService;
    
    @Autowired
    TaskEventService taskEventService;
    
    @Autowired
    SequenceFlowService sequenceFlowService;
  
    @GET
    @Path("/{id}")
    public String getWorkFlow(@PathParam("id") long id){
    	//Get appId from oauth get url
    	Workflow workFlow = this.workflowService.getWorkflow(id);
    	GetWorkflowResponse getWorkFlowResponse = null;
    	if(workFlow!=null){
    		getWorkFlowResponse = GetWorkflowResponse.fromWorkFlow(workFlow);
    	}
    	return new Gson().toJson(getWorkFlowResponse);
    }
        
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createWorkFlow(VoWorkflow voWorkflow){
    	Workflow workFlow = voWorkflow.toWorkFlow();
    	workFlow.setCreateTime(System.currentTimeMillis());
    	Workflow retWorkFlow = this.workflowService.createWorkflow(workFlow);
    	long workflowId = retWorkFlow.getId();
    	List<VoTask> voTasks = voWorkflow.getVoTasks();
    	if(voTasks!=null && voTasks.size()>0){
    		for(VoTask voTask : voTasks){
    			TaskWithBLOBs task = voTask.toTask();
    			task.setWorkflowId(workflowId);
    			task.setStatus(TaskStatus.CREATED);
    			task.setCreateTime(System.currentTimeMillis());
    			this.taskService.createTask(task);
    		}
    	}
    	
    	List<VoSequenceFlow> voSequenceFlows = voWorkflow.getVoSequenceFlows();
    	if(voSequenceFlows!=null && voSequenceFlows.size()>0){
    		for(VoSequenceFlow voSequenceFlow : voSequenceFlows){
    			SequenceFlow sequenceFlow = voSequenceFlow.toSequenceFlow();
    			sequenceFlow.setWorkflowId(workflowId);
    			sequenceFlow.setCreateTime(System.currentTimeMillis());
    			this.sequenceFlowService.createSequenceFlow(sequenceFlow);
    		}
    	}
        
    	String jsonWorkflow = new Gson().toJson(retWorkFlow);
    	return jsonWorkflow;
    }
    
    @DELETE
    @Path("/{workflowId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteWorkFlow(@PathParam("workflowId") int workflowId){
    	boolean success = true;
    	String message = String.format("workflow %s is deleted successfully!", workflowId);
    	try {
			this.workflowService.deleteWorkflow(workflowId);
		} catch (Exception e) {
			message = e.toString();
			success = false;
		}
    	return ResponseHelper.getJsonResponse(success, message);
    }
        
    @POST
    @Path("/{workflowId}/cancel")
    @Produces(MediaType.APPLICATION_JSON)
    public String cancelWorkFlow(@PathParam("workflowId") long workflowId){
        boolean success = true;
    	String message = String.format("workflow %s is cancelled successfully!", workflowId);
    	try {
    		this.workflowService.cancelWorkflow(workflowId);
		} catch (Exception e) {
			message = String.format("workflow %s cancelled failed! error: %s", workflowId, e.toString());
			success = false;
		}
    	return ResponseHelper.getJsonResponse(success, message);
    }
    
    @POST
    @Path("/{workflowId}/suspend")
    @Produces(MediaType.APPLICATION_JSON)
    public String suspendWorkFlow(@PathParam("workflowId") long workflowId){
    	boolean success = true;
    	String message = String.format("workflow %s is suspended successfully!", workflowId);
    	try {
    		this.workflowService.suspendWorkflow(workflowId);
		} catch (Exception e) {
			message = String.format("workflow %s suspended failed! error: %s", workflowId, e.toString());
			success = false;
		}
    	return ResponseHelper.getJsonResponse(success, message);
    }
    
    @POST
    @Path("/{workflowId}/resume")
    @Produces(MediaType.APPLICATION_JSON)
    public String resumeWorkFlow(@PathParam("workflowId") int workflowId){
    	boolean success = true;
    	String message = String.format("workflow %s is resumed successfully!", workflowId);
    	try {
    		this.workflowService.resumeWorkflow(workflowId);
		} catch (Exception e) {
			message = String.format("workflow %s resumed failed! error: %s", workflowId, e.toString());
			success = false;
		}
    	return ResponseHelper.getJsonResponse(success, message);
    }
    
    
    
}
