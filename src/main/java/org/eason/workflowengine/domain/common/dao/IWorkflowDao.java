package org.eason.workflowengine.domain.common.dao;

import java.util.List;

import org.eason.workflowengine.domain.common.model.Workflow;


public interface IWorkflowDao {
	public Workflow createWorkflow(Workflow workflow);
	public void deleteWorkflow(long id);
	public Workflow getWorkFlow(long workflowId);
	public void updateWorkflowStatus(long workflowId, String status);
	public List<Workflow> getUnFinishedWorkFlows();
	public List<Workflow> getRunnableWorkFlows();
	public void updateRunnableWorkFlowsAsRunning();
}
