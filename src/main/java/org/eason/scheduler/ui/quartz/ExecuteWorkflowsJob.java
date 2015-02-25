package org.eason.scheduler.ui.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.bl.impl.WorkflowService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ExecuteWorkflowsJob extends QuartzJobBean {
	private static Logger _logger = Logger.getLogger(ExecuteWorkflowsJob.class);
	
	private WorkflowService workflowService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		_logger.info("Execute ExecuteWorkflowsJob at " + simpleDateFormat.format(new Date()));
		workflowService.startRunnableWorkflows();
		workflowService.stopFinishedWorkflows();
		workflowService.executeWorkFlows();
	}
	
	public WorkflowService getWorkflowService() {
		return workflowService;
	}
	public void setWorkflowService(WorkflowService workflowService) {
		this.workflowService = workflowService;
	}

}