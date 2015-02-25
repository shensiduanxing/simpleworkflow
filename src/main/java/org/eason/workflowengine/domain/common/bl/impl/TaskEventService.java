package org.eason.workflowengine.domain.common.bl.impl;

import java.util.List;

import org.eason.workflowengine.domain.common.bl.ITaskEventService;
import org.eason.workflowengine.domain.common.dao.ITaskEventDao;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.TaskEvent;
import org.eason.workflowengine.domain.common.model.TaskEventName;
import org.eason.workflowengine.domain.common.model.TaskStatus;

/**
 * TaskEventListener will also trigger WorkFlowService to execute 
 * @author shensiduanxing
 *
 */
public class TaskEventService implements ITaskEventService {
	
	private ITaskEventDao taskEventDao;
	private TaskService taskService;
	private WorkflowService workflowService;
	
	public void setTaskEventDao(ITaskEventDao taskEventDao){
		this.taskEventDao = taskEventDao;
	}
	
	public void setTaskService(TaskService taskService){
		this.taskService = taskService;
	}
	
	public void setWorkflowService(WorkflowService workflowService){
		this.workflowService = workflowService;
	}
	
	
	public TaskEvent save(TaskEvent taskEvent){
		TaskEvent taskEventEntity = this.taskEventDao.save(taskEvent);
		this.onEvent(taskEvent);
		return taskEventEntity;
	}
	
	public TaskEvent getTaskEvent(long taskEventId){
		TaskEvent taskEvent = this.taskEventDao.getById(taskEventId);
		return taskEvent;
	}
	
	public void deleteTaskEvent(long taskEventId){
		this.taskEventDao.delete(taskEventId);
	}
	
	public List<TaskEvent> getTaskEvents(long workflowId, long taskId){
		List<TaskEvent> taskEvents = this.taskEventDao.getTaskEvents(workflowId, taskId);
		return taskEvents;
	}
	
	public List<TaskEvent> getWorkflowTaskEvents(long workflowId){
		List<TaskEvent> taskEvents = this.taskEventDao.getWorkflowTaskEvents(workflowId);
		return taskEvents;
	}
	
	public void onEvent(TaskEvent taskEvent){
		//To Do: make it async to make sure the performance, such as send the event to queue
		//then TaskEventListener fetch event from queue and handle it.
		long taskId = taskEvent.getTaskId();
		long workflowId = taskEvent.getWorkflowId();
		String eventName = taskEvent.getName();
		
		if(eventName.equals(TaskEventName.START)){
			//If event is start, set task status as Running
			taskService.updateTaskStatusRunning(workflowId, taskId);
		}else if(eventName.equals(TaskEventName.DONE)){
			//If event is done, set task status as Done, save Task Done event data as Task's Result,
			//trigger workflow execution
			//1. update task status to done
            taskService.updateTaskStatusDone(workflowId, taskId);
            if(this.workflowService.isWorkflowCompleted(workflowId)){
            	this.workflowService.stopWorkflow(workflowId);
            }else{
				//2. trigger workflow execution
				this.workflowService.executeWorkFlow(workflowId);
            }
		}else if (eventName.equals(TaskEventName.FAILED)){
			//If event is failed, set task status as Failed, save Task Failed event data as Task's Result
			taskService.updateTaskStatusFailed(workflowId, taskId);
			if(this.workflowService.isWorkflowCompleted(workflowId)){
            	this.workflowService.stopWorkflow(workflowId);
            }else{
			    this.workflowService.executeWorkFlow(workflowId);
            }
		}else if (eventName.equals(TaskEventName.TIMEOUT)){
			taskService.updateTaskStatusTimeout(workflowId, taskId);
			if(this.workflowService.isWorkflowCompleted(workflowId)){
            	this.workflowService.stopWorkflow(workflowId);
            }else{
			    this.workflowService.executeWorkFlow(workflowId);
            }
		}
	}
	
	
	
}
