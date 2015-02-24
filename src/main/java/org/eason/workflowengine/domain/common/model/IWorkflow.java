package org.eason.workflowengine.domain.common.model;

import java.util.List;

public interface IWorkflow {
	public String getId();
	public void setId(String id);
	
	public String getName();
	public void setName(String name);
	
	public String getData();
	public void setData(String data);
	
	public String getResultData();
	public void setResultData(String resultData);
	
	public long getCreateTime();
	public void setCreateTime(long createTime);
	
	public long getScheduledStartTime();
	public void setScheduledStartTime(long scheduledStartTime);
	
	public long getStartTime();
	public void setStartTime(long startTime);
	
	public long getDoneTime();
    public void setDoneTime(long doneTime);
    
    public long getTimeoutTime();
    public void setTimeoutTime(long expireTime);
    
    public String getStatus();
	public void setStatus(String status);
	
	public List<Task> getTasks();
	public void setTasks(List<Task> tasks);
	
    public void addTask(Task task);
    public List<ITask> getRunnableTasks();
    public boolean isDone();
    public String getWorkFlowStatusReport();
}
