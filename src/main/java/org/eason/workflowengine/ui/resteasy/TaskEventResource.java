package org.eason.workflowengine.ui.resteasy;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.bl.impl.TaskEventService;
import org.eason.workflowengine.domain.common.model.TaskEvent;
import org.eason.workflowengine.ui.resteasy.model.GetTaskEventResponse;
import org.eason.workflowengine.ui.resteasy.model.GetTaskEventsResponse;
import org.eason.workflowengine.ui.resteasy.model.ResponseHelper;
import org.eason.workflowengine.ui.resteasy.model.SaveTaskEventRequest;
import org.eason.workflowengine.ui.resteasy.model.SaveTaskEventResponse;
import org.eason.workflowengine.ui.resteasy.model.VoTaskEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
/**
 * TaskEventResource roles are both UI and Controller
 * @author shensiduanxing
 *
 */
@Controller
@Path(TaskEventResource.TASK_EVENT_RESOURCE_URL)
public class TaskEventResource {
    public static final String  TASK_EVENT_RESOURCE_URL = "/ws/taskevent";
    
    private Logger logger = Logger.getLogger(TaskEventResource.class);
    
    @Autowired
    TaskEventService taskEventService;
    
    //Register TaskEventListener to here, once got Event, notify TaskEventListener. 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String saveTaskEvent(VoTaskEvent voTaskEvent){
    	logger.info(String.format("Received TaskEvent %s of task %s of workflow %s", 
    			voTaskEvent.getTaskEventName(), voTaskEvent.getTaskId(), 
    			voTaskEvent.getTaskWorkFlowId()));
    	TaskEvent taskEvent = voTaskEvent.toTaskEvent();
    	TaskEvent savedTaskEvent = taskEventService.save(taskEvent);
    	SaveTaskEventResponse saveTaskEventResponse = SaveTaskEventResponse.fromTaskEvent(savedTaskEvent);
    	logger.info(saveTaskEventResponse.toJson());
    	return saveTaskEventResponse.toJson();
    }
    
    /**
     * curl -v -X GET -H "Content-Type: application/json" http://localhost:8080/ws/taskevent?workflowId=1&taskId=2
     * @param workflowId
     * @param taskId
     * @return
     */
    @GET
    @Path("/workflow/{workflowId}/task/{taskId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getTaskEvents(@PathParam("workflowId") long workflowId, @PathParam("taskId") long taskId){
    	GetTaskEventsResponse getTaskEventsResponse = new GetTaskEventsResponse();
    	List<TaskEvent> taskEvents = taskEventService.getTaskEvents(workflowId, taskId);
    	getTaskEventsResponse.setTaskEvents(taskEvents);
    	return new Gson().toJson(getTaskEventsResponse);
    }
    
    @GET
    @Path("/workflow/{workflowId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getWorkflowTaskEvents(@PathParam("workflowId") long workflowId){
    	GetTaskEventsResponse getTaskEventsResponse = new GetTaskEventsResponse();
    	List<TaskEvent> taskEvents = taskEventService.getWorkflowTaskEvents(workflowId);
    	getTaskEventsResponse.setTaskEvents(taskEvents);
    	return new Gson().toJson(getTaskEventsResponse);
    }
    
    @GET
    @Path("/{taskEventId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getTaskEvent(@PathParam("taskEventId") long taskEventId){
    	TaskEvent taskEvent = taskEventService.getTaskEvent(taskEventId);
    	if(taskEvent!=null){
    		return new Gson().toJson(taskEvent);
    	}else{
    		return "[]";
    	}
    }
    
    @DELETE
    @Path("/{taskEventId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteTaskEvent(@PathParam("taskEventId") long taskEventId){
    	boolean success = true;
    	String message = String.format("taskevent %s is deleted successfully!", taskEventId);
    	try {
			taskEventService.deleteTaskEvent(taskEventId);
		} catch (Exception e) {
			logger.error(e);
			success = false;
			message = String.format("workflow %s deleted failed! error: %s", taskEventId, e.toString());
		}
    	return ResponseHelper.getJsonResponse(success, message);
    }
    
}
