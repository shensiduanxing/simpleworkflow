package org.eason.testapp.taskhandler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.eason.infrastructure.integration.IWorkFlowRestClient;
import org.eason.workflowengine.domain.common.model.CommonForkJoinTask;
import org.eason.workflowengine.domain.common.model.ITask;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.TaskType;
import org.eason.workflowengine.ui.resteasy.model.SaveTaskEventRequest;
import org.eason.workflowengine.ui.resteasy.model.SaveTaskEventRequestHelper;


public class CommonForkJoinTaskHandler implements ITaskHandler {
	private Logger logger = Logger.getLogger(CommonForkJoinTask.class);
	
    private Task forkJoinTask;
    private IWorkFlowRestClient workFlowRestClient;
	
    public CommonForkJoinTaskHandler(Task forkJoinTask, IWorkFlowRestClient workFlowRestClient){
    	this.forkJoinTask = forkJoinTask;
    	this.workFlowRestClient = workFlowRestClient;
    }
	
	public void run() {
		logger.info(String.format("Task %s is started @ %s", forkJoinTask.getName(), new Date()));
		SaveTaskEventRequest taskEventForm = SaveTaskEventRequestHelper.createSaveStartEventRequest(forkJoinTask);
		workFlowRestClient.saveTaskEvent(taskEventForm.getVoTaskEvent());
		if(forkJoinTask.getType().equals(TaskType.FORK_JOIN_TASK)){
			// Here will put a default implementation, just log execution done.
			//before run requires to set expire time
			//after run complete, requires to call complete
			long now = System.currentTimeMillis();
			forkJoinTask.setStartTime(now);
			forkJoinTask.setTimeoutTime(now + 60000);// expire in 1 min.
			//do bussiness logic in side or outside
			forkJoinTask.setEndTime(System.currentTimeMillis());
			this.forkJoinTask.setEndTime(System.currentTimeMillis());
			logger.info(String.format("Task %s is finished @ %s!", this.forkJoinTask.getName(), new Date()));
			
			taskEventForm = SaveTaskEventRequestHelper.createSaveDoneEventRequest(forkJoinTask);
			workFlowRestClient.saveTaskEvent(taskEventForm.getVoTaskEvent());
		}else{
			logger.error(String.format("Wrong Task Type %s", forkJoinTask.getType()));
		}
	}
	
}
