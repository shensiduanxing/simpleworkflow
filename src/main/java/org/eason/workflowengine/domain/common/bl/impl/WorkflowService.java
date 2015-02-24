package org.eason.workflowengine.domain.common.bl.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.bl.IWorkFlowExecutor;
import org.eason.workflowengine.domain.common.dao.ITaskEventDao;
import org.eason.workflowengine.domain.common.dao.IWorkflowDao;
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
	
	public void setWorkFlowDao(IWorkflowDao workFlowDao){
		this.workFlowDao = workFlowDao;
	}
	
	public void setWorkFlowExecutor(IWorkFlowExecutor workFlowExecutor){
		this.workFlowExecutor = workFlowExecutor;
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
	

}