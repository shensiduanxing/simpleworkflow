package org.eason.workflowengine.domain.common.model;

/**
 * ServiceTask defines the task that perform business logic
 * ServiceTask can only join 0..1 ForkJoinTask or JoinServiceTask, as long as the 
 * ForkJoinTask or JoinServiceTask is done, this ServiceTask is runnable.
 * @author shensiduanxing
 *
 */
public interface IServiceTask extends ITask{
	public void setJoinedTask(ITask joinTask); //as long as this join task is done interval time , then service task is runnable.
	public void setJoinInterval(long joinInterval);
}
