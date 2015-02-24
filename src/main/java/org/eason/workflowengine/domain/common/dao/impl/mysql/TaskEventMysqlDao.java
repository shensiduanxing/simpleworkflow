package org.eason.workflowengine.domain.common.dao.impl.mysql;

import java.util.List;

import org.eason.workflowengine.domain.common.dao.ITaskEventDao;
import org.eason.workflowengine.domain.common.model.TaskEvent;
import org.eason.workflowengine.domain.common.model.TaskEventExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskEventMysqlDao implements ITaskEventDao {
    
	@Autowired
	TaskEventMapper taskEventMapper;

	public TaskEvent save(TaskEvent taskEvent) {
		taskEvent.setCreateTime(System.currentTimeMillis());
		taskEventMapper.insert(taskEvent);
		return taskEvent;
	}

	public List<TaskEvent> getTaskEvents(long workflowId, long taskId) {
		TaskEventExample example = new TaskEventExample();
		TaskEventExample.Criteria criteria = example.createCriteria();
		criteria.andWorkflowIdEqualTo(workflowId);
		criteria.andTaskIdEqualTo(taskId);
		List<TaskEvent> taskEvents = taskEventMapper.selectByExampleWithBLOBs(example);
		return taskEvents;
	}

	public List<TaskEvent> getWorkflowTaskEvents(long workflowId) {
		TaskEventExample example = new TaskEventExample();
		TaskEventExample.Criteria criteria = example.createCriteria();
		criteria.andWorkflowIdEqualTo(workflowId);
		List<TaskEvent> taskEvents = taskEventMapper.selectByExampleWithBLOBs(example);
		return taskEvents;
	}
	
	public List<TaskEvent> getTaskEvents(long workflowId, long taskId, List<String> names){
		TaskEventExample example = new TaskEventExample();
		TaskEventExample.Criteria criteria = example.createCriteria();
		criteria.andWorkflowIdEqualTo(workflowId);
		criteria.andTaskIdEqualTo(taskId);
		criteria.andNameIn(names);
		List<TaskEvent> taskEvents = taskEventMapper.selectByExampleWithBLOBs(example);
		return taskEvents;
	}

	public TaskEvent getById(long taskEventId) {
		TaskEvent taskEvent = taskEventMapper.selectByPrimaryKey(taskEventId);
		return taskEvent;
	}

	public void delete(long taskEventId) {
		taskEventMapper.deleteByPrimaryKey(taskEventId);
	}

}
