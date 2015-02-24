package org.eason.testapp.taskhandler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.eason.infrastructure.integration.IWorkFlowRestClient;
import org.eason.workflowengine.domain.common.model.CommonForkJoinTask;
import org.eason.workflowengine.domain.common.model.ITask;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.ui.resteasy.model.SaveTaskEventRequest;
import org.eason.workflowengine.ui.resteasy.model.SaveTaskEventRequestHelper;


public class CommonServiceTaskHandler implements ITaskHandler {
	private Logger logger = Logger.getLogger(CommonForkJoinTask.class);
	
	private Task serviceTask;
	private IWorkFlowRestClient workFlowRestClient;
	
	public CommonServiceTaskHandler(Task serviceTask, IWorkFlowRestClient workFlowRestClient){
		this.serviceTask = serviceTask;
		this.workFlowRestClient = workFlowRestClient;
	}
	
	public void run() {
		// Here will put a default implementation, just log execution done.
		//before run requires to set expire time
		//after run complete, requires to call complete
		logger.info(String.format("Start to execute Task %s @ %s", serviceTask.getName(), new Date()));
		SaveTaskEventRequest saveTaskEventRequest = SaveTaskEventRequestHelper.createSaveStartEventRequest(serviceTask);
		workFlowRestClient.saveTaskEvent(saveTaskEventRequest.getVoTaskEvent());
		
		long now = System.currentTimeMillis();
		this.serviceTask.setStartTime(System.currentTimeMillis());
		//do bussiness logic in side or outside, below emulate
		
		while(System.currentTimeMillis() - now < 15000){
			try {
				Thread.sleep(10000);
				logger.info(String.format("%s is working @ %s", this.serviceTask.getName(), new Date()));
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
		
		//if the task is executed in another process, the task status is report by external process, 
		//this step is performed by the task status report Handler.
		//Here should request TaskDAO to save task to DB.
		this.serviceTask.setEndTime(System.currentTimeMillis());
		logger.info(String.format("%s is done @ %s", this.serviceTask.getName(), new Date()));
		saveTaskEventRequest = SaveTaskEventRequestHelper.createSaveDoneEventRequest(serviceTask);
		workFlowRestClient.saveTaskEvent(saveTaskEventRequest.getVoTaskEvent());
	}
	
}
