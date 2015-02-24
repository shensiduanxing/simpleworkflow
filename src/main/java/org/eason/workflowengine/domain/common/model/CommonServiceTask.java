package org.eason.workflowengine.domain.common.model;


public class CommonServiceTask extends Task {
    private ITask joinedTask;
    private long joinInterval=0L;
    
    public CommonServiceTask(){}
    
    public CommonServiceTask(long id, String name, String data, long workFlowId){
    	super.setId(id);
    	super.setName(name);
    	super.setData(data);
    	super.setWorkflowId(workFlowId);
    	super.setType(TaskType.SERVICE_TASK);
    	super.setCreateTime(System.currentTimeMillis());
    	super.setDispatchTime(0L);
    	super.setStartTime(0L);
    	super.setEndTime(0L);
    	super.setTimeoutTime(0L);
    }
   
	public boolean isRunnable() {
		long now = System.currentTimeMillis();
		if(this.getStartTime()==0 && !this.isDispatched() && !this.isRunning() && !this.isDone() && !this.isTimeout()){
			if(this.joinedTask==null){
				return true;
			}else if(this.joinedTask.isDone()){
				if(now - this.joinedTask.getDoneTime() > this.joinInterval){
					return true;
				}else{
				    return false;
				}
			}else if (this.joinedTask.isTimeout()){
				if(now - this.joinedTask.getStartTime() - this.joinedTask.getTimeoutTime() > this.joinInterval){
					return true;
				}else{
				    return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public void setJoinedTask(ITask joinedTask) {
		this.joinedTask = joinedTask;
	}
	
	public void setJoinInterval(long joinInterval){
		this.joinInterval = joinInterval;
	}

	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	public String getWorkFlowId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWorkFlowId(String workFlowId) {
		// TODO Auto-generated method stub
		
	}

	public String getTaskType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTaskType(String taskType) {
		// TODO Auto-generated method stub
		
	}

	public void setCreateTime(long createTime) {
		// TODO Auto-generated method stub
		
	}

	public void setDispatchTime(long dispatchTime) {
		// TODO Auto-generated method stub
		
	}

	public void setStartTime(long startTime) {
		// TODO Auto-generated method stub
		
	}

	public long getDoneTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setDoneTime(long doneTime) {
		// TODO Auto-generated method stub
		
	}

	public void setTimeoutTime(long timeoutTime) {
		// TODO Auto-generated method stub
		
	}

	public boolean isInitiated() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isDispatched() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isFailed() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isTimeout() {
		// TODO Auto-generated method stub
		return false;
	}

	public String status() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
