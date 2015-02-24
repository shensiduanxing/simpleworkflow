package org.eason.workflowengine.domain.common.dao.impl.mem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eason.workflowengine.domain.common.dao.IWorkflowDao;
import org.eason.workflowengine.domain.common.model.ITask;
import org.eason.workflowengine.domain.common.model.Workflow;


public class WorkFlowMemDao implements IWorkflowDao {
	
	private Map<String, Workflow> workFlowTable;
	private AtomicInteger workFlowIdIncrementer = new AtomicInteger(2);
    
	public Map<String, Workflow> getWorkFlowTable(){
		return this.workFlowTable;
	}
	
	public void setWorkFlowTable(Map<String, Workflow> workflowTable){
		this.workFlowTable = workflowTable;
	}
	
	public Workflow createWorkFlow(Workflow workflow){
		int workFlowId = workFlowIdIncrementer.getAndIncrement();
		workflow.setId(Long.valueOf(workFlowId));
		workFlowTable.put(String.valueOf(workFlowId), workflow);
		return workFlowTable.get(String.valueOf(workFlowId));
	}
	
	public Workflow getWorkFlow(String workflowId){
		Workflow workFlow = workFlowTable.get(workflowId);
		return workFlow;
	}
	
	public List<Workflow> getUnFinishedWorkFlows(){
		List<Workflow> workFlows = new ArrayList<Workflow>();
//		for(Entry<String, Workflow> entry: workFlowTable.entrySet()){
//			Workflow workflow = entry.getValue();
//			if(!workflow.isDone()){
//				workFlows.add(workflow);
//			}
//		}
		return workFlows;
	}
	
	public ITask getTask(long taskId, long workflowId){
		ITask retTask = null;
//		Workflow workFlow = workFlowTable.get(workflowId);
//		List<Task> tasks = workFlow.getTasks();
//		for(Task task : tasks){
//			if(task.getId().equals(taskId)){
//				retTask = task;
//			}
//		}
		return retTask;
	}
	
	public void updateTask(ITask updatedTask){
//		String workFlowId = updatedTask.getWorkFlowId();
//		String taskId = updatedTask.getId();
//		
//		Workflow workFlow = workFlowTable.get(workFlowId);
//		List<Task> tasks = workFlow.getTasks();
//		for(Task task : tasks){
//			if(task.getId().equals(taskId)){
//				task.setDispatchTime(updatedTask.getDispatchTime());
//				task.setStartTime(updatedTask.getStartTime());
//				task.setDoneTime(updatedTask.getDoneTime());
//				task.setStatus(updatedTask.status());
//			}
//		}
	}

	public Workflow createWorkflow(Workflow workflow) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteWorkflow(long id) {
		// TODO Auto-generated method stub
		
	}

	public Workflow getWorkFlow(long workflowId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateTaskStatus(long taskId, long workFlowId, String status) {
		// TODO Auto-generated method stub
		
	}

	public void setTaskDispatched(long taskId, long workFlowId) {
		// TODO Auto-generated method stub
		
	}

	public void setTaskStarted(long taskId, long workFlowId) {
		// TODO Auto-generated method stub
		
	}

	public void setTaskDone(long taskId, long workFlowId) {
		// TODO Auto-generated method stub
		
	}

	public void setTaskFailed(long taskId, long workFlowId) {
		// TODO Auto-generated method stub
		
	}

	public void setTaskTimeout(long taskId, long workFlowId) {
		// TODO Auto-generated method stub
		
	}

	public void updateWorkflowStatus(long workflowId, String status) {
		// TODO Auto-generated method stub
		
	}

	public List<Workflow> getRunnableWorkFlows() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateRunnableWorkFlowsAsRunning() {
		// TODO Auto-generated method stub
		
	}
	
}
