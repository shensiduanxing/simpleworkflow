package org.eason.workflowengine.domain.common.bl.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.bl.IWorkFlowExecutor;
import org.eason.workflowengine.domain.common.dao.ITaskEventDao;
import org.eason.workflowengine.domain.common.dao.IWorkflowDao;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.TaskStatus;
import org.eason.workflowengine.domain.common.model.Workflow;
import org.eason.workflowengine.domain.common.model.WorkflowStatus;

/**
 * Use Cases:
 * 1. Schedule WorkFlow
 * 2. Execute WorkFlow
 * @author shensiduanxing
 */

public class WorkflowService {
	private Logger logger = Logger.getLogger(WorkflowService.class);
	/**
	 * This ExecutorService will be injected by Spring, created by Spring container
	 */
	private IWorkflowDao workFlowDao;
	private IWorkFlowExecutor workFlowExecutor;
	private TaskService taskService;
	
	public void setWorkFlowDao(IWorkflowDao workFlowDao){
		this.workFlowDao = workFlowDao;
	}
	
	public void setWorkFlowExecutor(IWorkFlowExecutor workFlowExecutor){
		this.workFlowExecutor = workFlowExecutor;
	}
	
	public void setTaskService(TaskService taskService){
		this.taskService = taskService;
	}
	
	public Workflow createWorkflow(Workflow workflow){
		Workflow workFlow = this.workFlowDao.createWorkflow(workflow);
		return workFlow;
	}
	
	public void deleteWorkflow(int id){
		this.workFlowDao.deleteWorkflow(id);
	}
	
	public Workflow getWorkflow(long id){
		Workflow workflow = this.workFlowDao.getWorkFlow(id);
		return workflow;
	}
	
	public void executeWorkFlows(){
		List<Workflow> workFlows = this.workFlowDao.getUnFinishedWorkFlows();
		if(workFlows!=null && workFlows.size()>0){
			for(Workflow workFlow : workFlows){
				this.workFlowExecutor.execute(workFlow);
			}
		}else{
			logger.info("No workFlows requiring execution!");
		}
	}
	
	public void startRunnableWorkflows(){
		this.workFlowDao.updateRunnableWorkFlowsAsRunning();
	}
	
	public void stopFinishedWorkflows(){
		List<Workflow> workFlows = this.workFlowDao.getUnFinishedWorkFlows();
		if(workFlows!=null && workFlows.size()>0){
			for(Workflow workFlow : workFlows){
				if(isWorkflowCompleted(workFlow.getId())){
					this.stopWorkflow(workFlow.getId());
				}
			}
		}
	}
	
	public void executeWorkFlow(long workFlowId){
		Workflow workFlow = workFlowDao.getWorkFlow(workFlowId);
		if(workFlow!=null){
			this.workFlowExecutor.execute(workFlow);
		}else{
			logger.error(String.format("Can not find workflow by id %s", workFlowId));
		}
	}
	
	public void cancelWorkflow(long workflowId){
		workFlowDao.updateWorkflowStatus(workflowId, WorkflowStatus.CANCELED);
	}
	
	public void suspendWorkflow(long workflowId){
		workFlowDao.updateWorkflowStatus(workflowId, WorkflowStatus.SUSPENDED);
	}
	
	public void resumeWorkflow(long workflowId){
		workFlowDao.updateWorkflowStatus(workflowId, WorkflowStatus.CANCELED);
	}
	
	public void stopWorkflow(long workflowId){
		List<Task> tasks = taskService.getWorkflowTasks(workflowId);
		String status = TaskStatus.DONE;
		for(Task task : tasks){
			String tmpStatus = task.getStatus();
			if(tmpStatus.equals(TaskStatus.FAILED)){
				status = TaskStatus.FAILED;
				break;
			}else if (tmpStatus.equals(TaskStatus.TIMEOUT)){
				if(status.equals(TaskStatus.DONE) || status.equals(TaskStatus.TIMEOUT)){
				    status = TaskStatus.TIMEOUT;
				}else if (status.equals(TaskStatus.FAILED)){
					status = TaskStatus.FAILED;
				}
			}
		}
		workFlowDao.updateWorkflowStatus(workflowId, status);
	}
	
	public boolean isWorkflowCompleted(long workflowId){
		boolean isCompleted = true;
		List<Task> tasks = taskService.getWorkflowUnfinishedTasks(workflowId);
		if(tasks.size()>0){
			isCompleted = false;
		}
		return isCompleted;
	}
	

}