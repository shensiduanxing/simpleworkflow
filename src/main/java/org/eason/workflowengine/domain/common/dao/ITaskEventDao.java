package org.eason.workflowengine.domain.common.dao;

import java.util.List;

import org.eason.workflowengine.domain.common.model.TaskEvent;

public interface ITaskEventDao {
    public TaskEvent save(TaskEvent taskEvent);
    public TaskEvent getById(long taskEventId);
    public void delete(long taskEventId);
    public List<TaskEvent> getTaskEvents(long workflowId, long taskId);
    public List<TaskEvent> getTaskEvents(long workflowId, long taskId, List<String> names);
    public List<TaskEvent> getWorkflowTaskEvents(long workflowId);
}
