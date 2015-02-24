package org.eason.workflowengine.ui.resteasy;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.bl.impl.TaskService;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.TaskResult;
import org.eason.workflowengine.ui.resteasy.model.GetTaskResultResponse;
import org.eason.workflowengine.ui.resteasy.model.GetTasksResponse;
import org.eason.workflowengine.ui.resteasy.model.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

@Controller
@Path(TaskResource.TASK_RESOURCE_URL)
public class TaskResource {
    public static final String  TASK_RESOURCE_URL = "/ws/task";
    
    private Logger logger = Logger.getLogger(TaskResource.class);
    
    @Autowired
    TaskService taskService;
          
    @GET
    @Path("/workflow/{workflowId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getWorkflowTasks(@PathParam("workflowId") int workflowId){
    	List<Task> tasks = taskService.getWorkflowTasks(workflowId);
    	GetTasksResponse getTasksResponse = new GetTasksResponse();
    	getTasksResponse.setTasks(tasks);
    	return new Gson().toJson(getTasksResponse);
    }
    
    @GET
    @Path("/workflow/{workflowId}/task/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String  getTask(@PathParam("workflowId") int workflowId, @PathParam("taskId") int taskId){
    	Task task = taskService.getTask(workflowId, taskId);
    	return new Gson().toJson(task);
    }
    
    @GET
    @Path("/workflow/{workflowId}/task/{taskId}/result")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTaskResult(@PathParam("workflowId") int workflowId, @PathParam("taskId") int taskId){
    	GetTaskResultResponse getTaskResultResponse = new GetTaskResultResponse();
    	TaskResult taskResult=null;
    	boolean success = true;
    	String errorMessage = "";
		try {
			taskResult = taskService.getTaskResult(workflowId, taskId);
			getTaskResultResponse.setTaskResult(taskResult);
		} catch (Exception e1) {
			logger.error(e1);
			success = false;
			errorMessage = String.format("workflow %s cancelled failed! error: %s", workflowId, e1.toString());
		}
    	if(success==true){
    		return new Gson().toJson(getTaskResultResponse);
    	}else{
		    return ResponseHelper.getJsonResponse(success, errorMessage);
    	}
    }
        
}
