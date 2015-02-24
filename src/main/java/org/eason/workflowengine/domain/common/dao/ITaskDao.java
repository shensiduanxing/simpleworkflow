package org.eason.workflowengine.domain.common.dao;

import java.util.List;

import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.TaskResult;
import org.eason.workflowengine.domain.common.model.TaskWithBLOBs;

public interface ITaskDao {
	public TaskWithBLOBs createTask(TaskWithBLOBs task);
	public Task getTask(long workFlowId, long taskId);
	public List<Task> getWorkflowTasks(long workflowId);
	public void updateTaskStatus(long taskId, long workFlowId, String status);
	public void setTaskDispatched(long taskId, long workFlowId);
	public void setTaskRunning(long taskId, long workFlowId);
	public void setTaskDone(long taskId, long workFlowId);
	public void setTaskFailed(long taskId, long workFlowId);
	public void setTaskTimeout(long taskId, long workFlowId);
}
