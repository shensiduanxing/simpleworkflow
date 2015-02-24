package org.eason.testapp.taskexecutor;

import java.util.concurrent.ExecutorService;

import org.eason.infrastructure.integration.IWorkFlowRestClient;
import org.eason.testapp.taskhandler.CommonForkJoinTaskHandler;
import org.eason.workflowengine.domain.common.model.ITask;
import org.eason.workflowengine.domain.common.model.Task;


public class ForkJoinTaskExecutor implements ITaskExecutor {

	private ExecutorService executorService;
	private IWorkFlowRestClient workFlowRestClient;
	
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	public void setWorkFlowRestClient(IWorkFlowRestClient workFlowRestClient){
		this.workFlowRestClient = workFlowRestClient;
	}
	
	public void execute(Task task) {
		CommonForkJoinTaskHandler commonForkJoinTaskHandler = new CommonForkJoinTaskHandler(task, this.workFlowRestClient);
		executorService.execute(commonForkJoinTaskHandler);
	}

}
