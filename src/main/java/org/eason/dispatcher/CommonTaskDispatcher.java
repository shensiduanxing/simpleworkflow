package org.eason.dispatcher;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eason.testapp.taskexecutor.ITaskExecutor;
import org.eason.workflowengine.domain.common.dao.IWorkflowDao;
import org.eason.workflowengine.domain.common.model.ITask;
import org.eason.workflowengine.domain.common.model.Task;


public class CommonTaskDispatcher implements ITaskDispatcher {
	private Logger logger = Logger.getLogger(CommonTaskDispatcher.class);
	
    private Map<String, ITaskExecutor> taskExecutorsMap;
    private IWorkflowDao workFlowDao;
	
	public void setTaskExecutorsMap(Map<String, ITaskExecutor> taskExecutorsMap){
		this.taskExecutorsMap = taskExecutorsMap;
	}
	
	public void setWorkFlowDao(IWorkflowDao workFlowDao){
		this.workFlowDao = workFlowDao;
	}
	
	public void dispatch(Task task) {
		//Get Task Executor
		ITaskExecutor taskExecutor = this.getTaskExecutor(task.getType());
		//Execute Task Handler
		if(taskExecutor!=null){
			taskExecutor.execute(task);
		    logger.info(String.format("Task %s is dispatched to execute @ %s", task.getName(), new Date()));
		    //this.workFlowDao.setTaskDispatched(task.getId(), task.getWorkFlowId());
		}else{
			logger.info(String.format("Can not find taskExecutor, task type %s is not handled.", task.getType()));
		}
	}
	
	private ITaskExecutor getTaskExecutor(String taskType){
    	return taskExecutorsMap.get(taskType);
    }

}
