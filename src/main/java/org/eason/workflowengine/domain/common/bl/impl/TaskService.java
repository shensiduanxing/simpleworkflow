package org.eason.workflowengine.domain.common.bl.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.dao.ISequenceFlowDao;
import org.eason.workflowengine.domain.common.dao.ITaskDao;
import org.eason.workflowengine.domain.common.dao.ITaskEventDao;
import org.eason.workflowengine.domain.common.model.SequenceFlow;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.TaskEvent;
import org.eason.workflowengine.domain.common.model.TaskEventName;
import org.eason.workflowengine.domain.common.model.TaskResult;
import org.eason.workflowengine.domain.common.model.TaskStatus;
import org.eason.workflowengine.domain.common.model.TaskWithBLOBs;

public class TaskService {
	
	private Logger logger = Logger.getLogger(TaskService.class);
	
	private ITaskDao taskDao;
	private ITaskEventDao taskEventDao;
	private ISequenceFlowDao sequenceFlowDao;
	
	
	public void setTaskDao(ITaskDao taskDao){
		this.taskDao = taskDao;
	}
	
	public void setTaskEventDao(ITaskEventDao taskEventDao){
		this.taskEventDao = taskEventDao;
	}
	
	public void setSequenceFlowDao(ISequenceFlowDao sequenceFlowDao){
		this.sequenceFlowDao = sequenceFlowDao;
	}
	
	public TaskWithBLOBs createTask(TaskWithBLOBs task){
		TaskWithBLOBs taskEntity = taskDao.createTask(task);
		return taskEntity;
	}
	
	public List<Task> getWorkflowTasks(long workflowId){
		List<Task> tasks = this.taskDao.getWorkflowTasks(workflowId);
		return tasks;
	}
	
	public Task getTask(long workflowId, long taskId){
		Task task = this.taskDao.getTask(workflowId, taskId);
		return task;
	}
	
	public List<Task> getWorkflowRunnableTasks(long workflowId){
		List<Task> tasks = this.taskDao.getWorkflowTasks(workflowId);
		
		Map<String, List<Task>> taskDependencyMap = this.getTaskDependencyMap(workflowId);
		//for each task, get task's dependent tasks and check if all dependent tasks done
		List<Task> runnableTasks = new ArrayList<Task>();

		if(tasks!=null && tasks.size()>0){
			for(Task task : tasks){
				if(this.isTaskRunnable(task, taskDependencyMap)){
					runnableTasks.add(task);
				}
			}
		}
		
		return runnableTasks;
	}
	
	private Map<String, List<Task>> getTaskDependencyMap(long workflowId){
		Map<String, Task> taskNameTaskMap = this.getTaskNameTaskMap(workflowId);
		logger.debug(String.format("Workflow %s taskNameTaskMap size=%s", workflowId, taskNameTaskMap.size()));
		Map<String, List<Task>> taskDependencyMap = new HashMap<String, List<Task>>();
		List<SequenceFlow> sequenceFlows = this.sequenceFlowDao.getSequenceFlows(workflowId);
		logger.debug(String.format("Workflow %s sequenceFlows size=%s",workflowId, sequenceFlows.size()));
		if(sequenceFlows!=null && sequenceFlows.size()>0){
			for(SequenceFlow sequenceFlow : sequenceFlows){
				String fromTaskName = sequenceFlow.getFromTask();
				String toTaskName = sequenceFlow.getToTask();
				List<Task> dependingTasks = taskDependencyMap.get(toTaskName);
				if(dependingTasks==null){
					Task fromTask = taskNameTaskMap.get(fromTaskName);
					dependingTasks = new ArrayList<Task>();
					dependingTasks.add(fromTask);
					taskDependencyMap.put(toTaskName, dependingTasks);
				}else{
					Task fromTask = taskNameTaskMap.get(fromTaskName);
					dependingTasks.add(fromTask);
					taskDependencyMap.put(toTaskName, dependingTasks);
				}
			}
		}
		return taskDependencyMap;
	}
	
	private Map<String, Task> getTaskNameTaskMap(long workflowId){
		Map<String, Task> taskNameTaskMap = new HashMap<String, Task>();
		List<Task> tasks = this.taskDao.getWorkflowTasks(workflowId);
		if(tasks!=null && tasks.size()>0){
			for(Task task : tasks){
				taskNameTaskMap.put(task.getName(), task);
			}
		}
		return taskNameTaskMap;
	}
	
	public boolean isTaskRunnable(Task task, Map<String, List<Task>> taskDependencyMap){
		boolean isRunnable = true;
		String taskStatus = task.getStatus();
		String taskUUID = String.format("%s-%s", task.getName(), task.getWorkflowId());
		logger.debug(String.format("Check if task %s is runnable", taskUUID));
		if(taskStatus.equals(TaskStatus.CREATED)){
			String taskName = task.getName();
			List<Task> dependingTasks = taskDependencyMap.get(taskName);
			
			if(dependingTasks!=null && dependingTasks.size()>0){
				logger.debug(String.format("task %s depending tasks size=%s", taskUUID, dependingTasks.size()));
				for(Task dependingTask : dependingTasks){
					String dependingTaskStatus = dependingTask.getStatus();
					if(dependingTaskStatus.equals(TaskStatus.CREATED) || 
							dependingTaskStatus.equals(TaskStatus.QUEUED) ||
							dependingTaskStatus.equals(TaskStatus.DISPATCHED) || 
							dependingTaskStatus.equals(TaskStatus.RUNNING)){
						isRunnable = false;
					}
				}
			}else{
				logger.debug(String.format("task %s has not depending tasks", taskUUID));
				isRunnable = true;
			}
		}else{
			isRunnable = false;
		}
		
		return isRunnable;
	}
	
	public void setTaskQueued(Task task){
		this.taskDao.updateTaskStatus(task.getId(), task.getWorkflowId(), TaskStatus.QUEUED);
	}
	
	public void setTaskDispatched(Task task){
		this.taskDao.updateTaskStatus(task.getId(), task.getWorkflowId(), TaskStatus.DISPATCHED);
	}
	
	public TaskResult getTaskResult(long workflowId, long taskId){
		TaskResult taskResult = null;
		List<String> names = new ArrayList<String>();
		names.add(TaskEventName.DONE);
		names.add(TaskEventName.FAILED);
		names.add(TaskEventName.TIMEOUT);
		List<TaskEvent> taskEvents = this.taskEventDao.getTaskEvents(workflowId, taskId, names);
		if(taskEvents!=null){
			if(taskEvents.size()==1){
				TaskEvent taskEvent = taskEvents.get(0);
				taskResult = new TaskResult();
				taskResult.setTaskId(taskEvent.getTaskId());
				taskResult.setWorkflowId(taskEvent.getWorkflowId());
				taskResult.setStatus(taskEvent.getName());
				taskResult.setResult(taskEvent.getData());
			}else{
				for(TaskEvent taskEvent : taskEvents){
					if(taskEvent.getName().equals(TaskEventName.DONE) || 
							taskEvent.getName().equals(TaskEventName.FAILED)){
						taskResult = new TaskResult();
						taskResult.setTaskId(taskEvent.getTaskId());
						taskResult.setWorkflowId(taskEvent.getWorkflowId());
						taskResult.setStatus(taskEvent.getName());
						taskResult.setResult(taskEvent.getData());
					}
				}
			}
		}
		return taskResult;
	}
	
	public void updateTaskStatusRunning(long workflowId, long taskId){
		this.taskDao.setTaskRunning(taskId, workflowId);
	}
	
	public void updateTaskStatusDone(long workflowId, long taskId){
		this.taskDao.setTaskDone(taskId, workflowId);
	}
	
	public void updateTaskStatusFailed(long workflowId, long taskId){
		this.taskDao.setTaskFailed(taskId, workflowId);
	}
	
	public void updateTaskStatusTimeout(long workflowId, long taskId){
		this.taskDao.setTaskTimeout(taskId, workflowId);
	}

}
