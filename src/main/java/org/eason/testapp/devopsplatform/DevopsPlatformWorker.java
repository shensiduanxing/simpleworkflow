package org.eason.testapp.devopsplatform;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.bl.impl.TaskEventService;
import org.eason.workflowengine.domain.common.bl.impl.TaskService;
import org.eason.workflowengine.domain.common.model.TaskEvent;
import org.eason.workflowengine.domain.common.model.TaskEventName;
import org.eason.workflowengine.domain.common.model.TaskWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class DevopsPlatformWorker {
	
	private Logger logger = Logger.getLogger(DevopsPlatformWorker.class);
	
	@Autowired
    TaskService taskService;
	
	@Autowired
    TaskEventService taskEventService;
    
	public void handle(String jsonTask){
    	logger.info("DevopsPlatformWorker-Received: " + jsonTask);
    	
    	Gson gson = new Gson();
    	TaskWithBLOBs task = gson.fromJson(jsonTask, TaskWithBLOBs.class);
    	//handle task, save task events, task result is reported in Done or Failed event
    	
    	//Phase 1: call TaskService directly to save task events and result
    	//Phase 2: call WorkflowRestClient to call Rest API to save task events and result
    	//call back api parameters: 
    	//url: http://localhost:8080/ws/taskevent 
    	//username: eason
    	//password: eason2014
    	this.saveTaskEvent(task, TaskEventName.START, "");
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.error(e);
		}
    	this.saveTaskEvent(task, TaskEventName.DONE, "{'result':'upgrade successfully!'}");
    }
	
	private void saveTaskEvent(TaskWithBLOBs task, String eventName, String eventData){
		TaskEvent taskEvent = new TaskEvent();
		taskEvent.setWorkflowId(task.getWorkflowId());
    	taskEvent.setTaskId(task.getId());
    	taskEvent.setName(eventName);
    	taskEvent.setData(eventData);
    	taskEvent.setEventTime(System.currentTimeMillis());
    	taskEventService.save(taskEvent);
	}
	
}
