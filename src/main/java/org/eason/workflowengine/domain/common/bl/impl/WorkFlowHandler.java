package org.eason.workflowengine.domain.common.bl.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.eason.infrastructure.common.lock.DistributeLock;
import org.eason.workflowengine.domain.common.bl.IWorkFlowHandler;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.Workflow;
import org.springframework.amqp.core.AmqpTemplate;

import com.google.gson.Gson;


/**
 * WorkFlowHandler is a runnable task requires to create instance if required in Runtime, so 
 * can not managed by Spring.
 * @author shensiduanxing
 *
 */
public class WorkFlowHandler implements IWorkFlowHandler {
	private Logger logger = Logger.getLogger(WorkFlowHandler.class);
	
	private Workflow workflow;
	private TaskService taskService;
	private DistributeLock distributeLock;
	private AmqpTemplate amqpTemplate;
	private String taskQueueName;
	
	public WorkFlowHandler(Workflow workflow, 
			TaskService taskService,
			DistributeLock distributeLock,
			AmqpTemplate amqpTemplate,
			String taskQueueName){
		this.workflow = workflow;
		this.taskService = taskService;
		this.distributeLock = distributeLock;
		this.amqpTemplate = amqpTemplate;
		this.taskQueueName = taskQueueName;
	}
	
    public void run() {
    	//should sync the same workflow exeuction, before execute get a lock
    	//after execution, release the lock.
    	logger.info(String.format("Go to execute WorkFlow %s-%s Tasks @ %s", 
    			this.workflow.getName(), this.workflow.getId(), new Date()));
    	
    	String lockName = String.format("workflow-%s-%s", this.workflow.getId(), this.workflow.getName());
		if(distributeLock.lock(lockName, 5, 5)){
			try{
				//1. taskService get Runnable Tasks
				List<Task> tasks = this.taskService.getWorkflowRunnableTasks(this.workflow.getId());
				
				//2. send task to taskqueue and mark task status as Queued
				if(tasks!=null && tasks.size()>0){
					for(Task task : tasks){
						String message = String.format("task %s-%s-%s is runnable!", 
								this.workflow.getName(), 
								this.workflow.getId(),
								task.getName());
						logger.info(message);
						this.amqpTemplate.convertAndSend(this.taskQueueName, new Gson().toJson(task));
						taskService.setTaskQueued(task);
					}
				}
			}catch(Exception e){
				logger.error(e);
			}finally{
				distributeLock.unlock(lockName);
			}
		}else{
            logger.error(String.format("Can not get lock of %s", lockName));			
		}
	}
}
