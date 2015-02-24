package org.eason.testapp.taskexecutor;

import java.util.concurrent.ExecutorService;

import org.eason.infrastructure.integration.IWorkFlowRestClient;
import org.eason.testapp.taskhandler.CommonServiceTaskHandler;
import org.eason.workflowengine.domain.common.model.Task;


public class ServiceTaskExecutor implements ITaskExecutor {
    private ExecutorService executorService;
    private IWorkFlowRestClient workFlowRestClient;
	
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	public void setWorkFlowRestClient(IWorkFlowRestClient workFlowRestClient){
		this.workFlowRestClient = workFlowRestClient;
	}

	public void execute(Task task) {
		//Executor should check task type and task timeout value
		//if task does not have timeout value, refuse to execute.
		CommonServiceTaskHandler commonServiceTaskHandler = new CommonServiceTaskHandler(task, this.workFlowRestClient);
		executorService.execute(commonServiceTaskHandler);
	}
}
