package org.eason.workflowengine.domain.common.dao.impl.mysql;

import java.util.List;

import org.eason.workflowengine.domain.common.dao.IWorkflowDao;
import org.eason.workflowengine.domain.common.model.ITask;
import org.eason.workflowengine.domain.common.model.Workflow;
import org.eason.workflowengine.domain.common.model.WorkflowStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.eason.workflowengine.domain.common.model.WorkflowExample;

@Repository
public class WorkflowMysqlDao implements IWorkflowDao {
	
	@Autowired
	WorkflowMapper workflowMapper;
	
	public Workflow createWorkflow(Workflow workflow){
		workflowMapper.insert(workflow);
		return workflow;
	}
		
	public void deleteWorkflow(long id) {
		workflowMapper.deleteByPrimaryKey(id);
	}
	
	public Workflow getWorkFlow(long workflowId){
		Workflow workflow = workflowMapper.selectByPrimaryKey(workflowId);
		return workflow;
	}
	
	public void updateWorkflowStatus(long workflowId, String status){
		Workflow workflow = workflowMapper.selectByPrimaryKey(workflowId);
		workflow.setStatus(status);
		
		WorkflowExample example = new WorkflowExample();
		WorkflowExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(workflowId);

		workflowMapper.updateByExample(workflow, example);
	}
	
	public List<Workflow> getUnFinishedWorkFlows(){
		WorkflowExample example = new WorkflowExample();
		WorkflowExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(WorkflowStatus.RUNNING);
		List<Workflow> workflows = workflowMapper.selectByExample(example);
		return workflows;
	}
	
	public List<Workflow> getRunnableWorkFlows(){
		WorkflowExample example = new WorkflowExample();
		WorkflowExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(WorkflowStatus.CREATED);
		criteria.andScheduledStartTimeLessThanOrEqualTo(System.currentTimeMillis());
		List<Workflow> workflows = workflowMapper.selectByExample(example);
		return workflows;
	}
	
	public void updateRunnableWorkFlowsAsRunning(){
		WorkflowExample example = new WorkflowExample();
		WorkflowExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(WorkflowStatus.CREATED);
		criteria.andScheduledStartTimeLessThanOrEqualTo(System.currentTimeMillis());		
		Workflow workflow = new Workflow();
		workflow.setStatus(WorkflowStatus.RUNNING);
		workflow.setStartTime(System.currentTimeMillis());
		workflowMapper.updateByExampleSelective(workflow, example);
	}
	
	public void updateWorkflowsAsTimeout(){
		
	}

}
