package org.eason.workflowengine;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.bl.impl.WorkflowService;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.ITask;
import org.eason.workflowengine.domain.common.model.Workflow;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:workflow-test-context.xml")
public class WorkFlowServiceIntegrationTest {
	private Logger logger = Logger.getLogger(WorkFlowServiceIntegrationTest.class);

	@Autowired
	WorkflowService workFlowServiceRef;
	@Autowired
    Workflow testUpgradeFarmWorkFlow;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testBasicByUsingSpring() {
	    logger.info("Using Spring Unit Test Framework");
//	    GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
//	    Gson gson = gb.create();
//	    
//	    List<Task> tasks = testUpgradeFarmWorkFlow.getTasks();
//	    for(Task task : tasks){
//	    	task.setCreateTime(System.currentTimeMillis());
//	    	logger.info(gson.toJson(task));
//	    }
//	
//        String strJSON = gson.toJson(testUpgradeFarmWorkFlow);
//        logger.info(strJSON);
//        
//		workFlowServiceRef.createWorkflow(testUpgradeFarmWorkFlow);
//		
//		ScheduledExecutorService scheduledExecutorService  = Executors.newScheduledThreadPool(2);
//		long initialDelay = 0;
//		long period = 5;
//		WorkFlowEngineServiceScheduleTask workFlowEngineServiceScheduleTask = new WorkFlowEngineServiceScheduleTask(workFlowServiceRef);
//		scheduledExecutorService.scheduleAtFixedRate(workFlowEngineServiceScheduleTask, initialDelay, period, TimeUnit.SECONDS);
//		try {
//			scheduledExecutorService.awaitTermination(1, TimeUnit.MINUTES);
//		} catch (InterruptedException e) {
//			logger.error(e);
//		}
//		tasks = testUpgradeFarmWorkFlow.getTasks();
//		for(ITask task : tasks){
//			logger.info(String.format("Task %s started @ %s , end @ %s, status=%s", 
//					task.getName(), new Date(task.getStartTime()), 
//					new Date(task.getDoneTime()), task.status()));
//			assert task.isDone();
//		}
        
	}
}


class WorkFlowEngineServiceScheduleTask implements Runnable{
	private WorkflowService workFlowEngineService;
	
	public WorkFlowEngineServiceScheduleTask(WorkflowService workFlowEngineService){
		this.workFlowEngineService = workFlowEngineService;
	}

	public void run() {
		this.workFlowEngineService.executeWorkFlows();
	}
	
}