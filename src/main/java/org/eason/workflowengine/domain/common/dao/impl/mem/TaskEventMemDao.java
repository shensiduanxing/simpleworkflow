package org.eason.workflowengine.domain.common.dao.impl.mem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.dao.ITaskEventDao;
import org.eason.workflowengine.domain.common.model.TaskEvent;

public class TaskEventMemDao implements ITaskEventDao {
	private Logger logger = Logger.getLogger(TaskEventMemDao.class);
	private Map<Long, TaskEvent> taskEventTable;
	private AtomicInteger taskEventIdIncrementer = new AtomicInteger(0);
	
	public Map<Long, TaskEvent> getTaskEventTable(){
		return this.taskEventTable;
	}
	
	public void setTaskEventTable(Map<Long, TaskEvent> taskEventTable){
		this.taskEventTable = taskEventTable;
	}
	
	public TaskEvent save(TaskEvent taskEvent) {
		logger.info(String.format("Save taskEvent %s of WorkFlow %s, Task %s", 
				taskEvent.getName(), taskEvent.getWorkflowId(), taskEvent.getTaskId()));
		int id = taskEventIdIncrementer.getAndIncrement();
		taskEvent.setId(Long.valueOf(id));
		taskEvent.setCreateTime(System.currentTimeMillis());
		taskEventTable.put(Long.valueOf(id), taskEvent);
		return taskEventTable.get(Long.valueOf(id));
	}

	public List<TaskEvent> getTaskEvents(long workflowId, long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskEvent> getWorkflowTaskEvents(long workflowId) {
		// TODO Auto-generated method stub
		return null;
	}

}
