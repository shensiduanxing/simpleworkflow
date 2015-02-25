package org.eason.workflowengine.domain.common.dao.impl.mysql;

import java.util.List;

import org.eason.workflowengine.domain.common.dao.ITaskDao;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.TaskExample;
import org.eason.workflowengine.domain.common.model.TaskResult;
import org.eason.workflowengine.domain.common.model.TaskStatus;
import org.eason.workflowengine.domain.common.model.TaskWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskMysqlDao implements ITaskDao {
    
	@Autowired
	TaskMapper taskMapper;
	
	public TaskWithBLOBs createTask(TaskWithBLOBs task){
		taskMapper.insert(task);
		return task;
	}
	
	public List<Task> getWorkflowTasks(long workflowId) {
		TaskExample example = new TaskExample();
		TaskExample.Criteria criteria = example.createCriteria();
		criteria.andWorkflowIdEqualTo(workflowId);
		List<Task> tasks = taskMapper.selectByExampleWithBLOBs(example);
		return tasks;
	}
	
	public List<Task> getWorkflowTasks(long workflowId, List<String> statuses) {
		TaskExample example = new TaskExample();
		TaskExample.Criteria criteria = example.createCriteria();
		criteria.andWorkflowIdEqualTo(workflowId);
		criteria.andStatusIn(statuses);
		List<Task> tasks = taskMapper.selectByExampleWithBLOBs(example);
		return tasks;
	}

	public Task getTask(long workflowId, long taskId) {
		TaskExample example = new TaskExample();
		TaskExample.Criteria criteria = example.createCriteria();
		criteria.andWorkflowIdEqualTo(workflowId);
		criteria.andIdEqualTo(taskId);
		List<Task> tasks = taskMapper.selectByExampleWithBLOBs(example);
		if(tasks!=null && tasks.size()>0){
			return tasks.get(0);
		}else{
			return null;
		}
	}

	public void updateTaskStatus(long taskId, long workflowId, String status) {
		TaskExample example = new TaskExample();
		TaskExample.Criteria criteria = example.createCriteria();
		criteria.andWorkflowIdEqualTo(workflowId);
		criteria.andIdEqualTo(taskId);
		
		TaskWithBLOBs task = new TaskWithBLOBs();
		task.setStatus(status);
		if(status.equals(TaskStatus.QUEUED)){
		    task.setQueuedTime(System.currentTimeMillis());
		}else if (status.equals(TaskStatus.DISPATCHED)){
			task.setDispatchTime(System.currentTimeMillis());
		}else if (status.equals(TaskStatus.RUNNING)){
			task.setStartTime(System.currentTimeMillis());
		}else if (status.equals(TaskStatus.DONE) || status.equals(TaskStatus.FAILED)){
			task.setEndTime(System.currentTimeMillis());
		}else if (status.equals(TaskStatus.TIMEOUT)){
			task.setTimeoutTime(System.currentTimeMillis());
		}
		taskMapper.updateByExampleSelective(task, example);
	}

	public void setTaskDispatched(long taskId, long workflowId) {
	    this.updateTaskStatus(taskId, workflowId, TaskStatus.DISPATCHED);
	}

	public void setTaskRunning(long taskId, long workflowId) {
		this.updateTaskStatus(taskId, workflowId, TaskStatus.RUNNING);
	}

	public void setTaskDone(long taskId, long workflowId) {
		this.updateTaskStatus(taskId, workflowId, TaskStatus.DONE);
	}

	public void setTaskFailed(long taskId, long workflowId) {
		this.updateTaskStatus(taskId, workflowId, TaskStatus.FAILED);
	}

	public void setTaskTimeout(long taskId, long workflowId) {
		this.updateTaskStatus(taskId, workflowId, TaskStatus.TIMEOUT);
	}

	

}
