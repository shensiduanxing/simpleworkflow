package org.eason.workflowengine.domain.common.model;

import java.util.List;
/**
 * This type task will not be dispatched to external task executor, only dispatched to 
 * internal task executor.
 * @author shensiduanxing
 *
 */
public class CommonForkJoinTask extends Task {
    private List<IServiceTask> joinedServiceTasks;
    
    public CommonForkJoinTask(){}
    
    public CommonForkJoinTask(long id, String name, String data, long workFlowId){    	
    	super.setId(id);
    	super.setName(name);
    	super.setData(data);
    	super.setWorkflowId(workFlowId);
    	super.setType(TaskType.FORK_JOIN_TASK);
    	super.setCreateTime(System.currentTimeMillis());
    	super.setDispatchTime(0L);
    	super.setStartTime(0L);
    	super.setEndTime(0L);
    	super.setTimeoutTime(0L);
    }
    
    public void setJoinedServiceTasks(List<IServiceTask> joinedServiceTasks){
    	this.joinedServiceTasks = joinedServiceTasks;
    }

	public boolean isRunnable() {
		if(!this.isDispatched() && !this.isRunning() && !this.isDone() && !this.isTimeout()){
			if(this.joinedServiceTasks.size()==0)
				return true;
			else{
				//All joinedServiceTask must be done or timeout
				boolean runnable = true;
				for(IServiceTask joinedServiceTask : this.joinedServiceTasks){
					if(!(joinedServiceTask.isDone() || joinedServiceTask.isTimeout())){
						runnable = false;
					}
				}
				return runnable;
			}
		}else{
			return false;
		}
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
