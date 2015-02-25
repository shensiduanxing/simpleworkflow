package org.eason.workflowengine.domain.common.bl.impl;

import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.eason.infrastructure.common.lock.DistributeLock;
import org.eason.workflowengine.domain.common.bl.IWorkFlowExecutor;
import org.eason.workflowengine.domain.common.model.Workflow;
import org.springframework.amqp.core.AmqpTemplate;


public class WorkFlowExecutor implements IWorkFlowExecutor {
	private Logger logger = Logger.getLogger(WorkFlowExecutor.class);
    private ExecutorService executorService;
    private TaskService taskService;
    private DistributeLock distributeLock;
    private AmqpTemplate amqpTemplate;
    private String taskQueueName;
	
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	public void setTaskService(TaskService taskService){
		this.taskService = taskService;
	}
	
	public void setDistributeLock(DistributeLock distributeLock){
		this.distributeLock = distributeLock;
	}
	
	public void setAmqpTemplate(AmqpTemplate amqpTemplate){
		this.amqpTemplate = amqpTemplate;
	}
	
	public void setTaskQueueName(String taskQueueName){
		this.taskQueueName = taskQueueName;
	}
	
	public void execute(Workflow workFlow) {
		WorkFlowHandler workFlowHandler = new WorkFlowHandler(workFlow, this.taskService, 
				this.distributeLock, this.amqpTemplate, this.taskQueueName);
		this.executorService.execute(workFlowHandler);
	}

}
