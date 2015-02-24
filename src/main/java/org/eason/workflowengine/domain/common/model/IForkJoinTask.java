package org.eason.workflowengine.domain.common.model;

import java.util.List;

/**
 * For IForkJoinTask, it used for control the service tasks in parallal execution and join execution,
 * by 
 * 1. setting self joinTasks for controlling join execution.
 * 2. setting forkTask of ServiceTask by providing fork service task function.
 * it has 0..* join tasks, if join tasks are 0, then it is executable.
 * its join task can only be ServieTask
 * it can only for ServiceTask, fork more than 1 ServiceTask.
 * forkjoin task can join more than 1 tasks.
 * @author shensiduanxing
 *
 */
public interface IForkJoinTask extends ITask {
	public void setJoinedServiceTasks(List<IServiceTask> joinedServiceTasks);
}
