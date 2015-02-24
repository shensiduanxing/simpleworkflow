package org.eason.workflowengine.domain.common.model;


public interface ITask {
	public String getId();
	public void setId(String id);
	
	public String getWorkFlowId();
    public void setWorkFlowId(String workFlowId);
	
	public String getName();
    public void setName(String name);
    
    public String getData();
    public void setData(String data);
    
    public String getTaskType();
    public void setTaskType(String taskType);
    
    public long getCreateTime();
    public void setCreateTime(long createTime);
    
    public long getDispatchTime();
    public void setDispatchTime(long dispatchTime);
    
    public long getStartTime();
    public void setStartTime(long startTime);
    
    public long getDoneTime();
    public void setDoneTime(long doneTime);
    
    public long getTimeoutTime();
    public void setTimeoutTime(long timeoutTime);
    
    public boolean isInitiated();
    public boolean isRunnable();
    public boolean isDispatched();
    public boolean isRunning();
    public boolean isDone();
    public boolean isFailed();
    public boolean isTimeout();
    
    public String status();
    public void setStatus(String status);
    
    
}
