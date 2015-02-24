package org.eason.workflowengine.ui.resteasy;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.TaskEvent;
import org.eason.workflowengine.domain.common.model.TaskResult;
import org.eason.workflowengine.domain.common.model.Workflow;
import org.eason.workflowengine.domain.common.model.WorkflowStatus;
import org.eason.workflowengine.ui.resteasy.model.CreateWorkflowRequest;
import org.eason.workflowengine.ui.resteasy.model.CreateWorkflowResponse;
import org.eason.workflowengine.ui.resteasy.model.GetTaskResultResponse;
import org.eason.workflowengine.ui.resteasy.model.GetTasksResponse;
import org.eason.workflowengine.ui.resteasy.model.GetWorkflowResponse;
import org.eason.workflowengine.ui.resteasy.model.SaveTaskEventRequest;
import org.eason.workflowengine.ui.resteasy.model.VoSequenceFlow;
import org.eason.workflowengine.ui.resteasy.model.VoTask;
import org.eason.workflowengine.ui.resteasy.model.VoTaskEvent;
import org.eason.workflowengine.ui.resteasy.model.VoWorkflow;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class WorkflowRestServiceUnitTest {
	
	@Autowired
	WorkflowResource workflowResource;
	
	@Autowired
	TaskResource taskResource;
	
	@Autowired
	TaskEventResource taskEventResource;
	
	private Dispatcher dispatcher;
	
	private static Logger logger = Logger.getLogger(WorkflowRestServiceUnitTest.class);
	
	private Workflow testWorkFlow;

	@Before
	public void setUp() throws Exception {
		this.dispatcher = MockDispatcherFactory.createDispatcher();
	    dispatcher.getRegistry().addSingletonResource(this.workflowResource);
	    dispatcher.getRegistry().addSingletonResource(this.taskResource);
	    dispatcher.getRegistry().addSingletonResource(this.taskEventResource);
	    this.testWorkFlow = this.createWorkFlow();
	}

	@After
	public void tearDown() throws Exception {
		//this.deleteWorkflow(this.testWorkFlow);
	}
	
	@Test
	public void testCreateWorkflow(){
		Long workFlowId = this.testWorkFlow.getId();
		System.out.println("workFlow id is "+workFlowId);
		assertTrue(this.testWorkFlow!=null);
	}
	
	//@Test
	public void testGetWorkflow() throws URISyntaxException{
		Long workFlowId = this.testWorkFlow.getId();
		MockHttpRequest request = MockHttpRequest.get(String.format("/ws/workflow/%s", workFlowId));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    String jsonGetWorkflowResponse = response.getContentAsString();
	    System.out.println("jsonGetWorkflowResponse="+getPrettyJSON(jsonGetWorkflowResponse));
	    GetWorkflowResponse getWorkflowResponse = GetWorkflowResponse.fromJson(jsonGetWorkflowResponse);
	    Assert.assertEquals(response.getStatus(), 200);
	    Assert.assertEquals(this.testWorkFlow.getName(),  getWorkflowResponse.getName());
	}
	
	@Test
	public void testCancelWorkflow() throws URISyntaxException{
		Long workflowId = this.testWorkFlow.getId();
		MockHttpRequest request = MockHttpRequest.post(String.format("/ws/workflow/%s/cancel", workflowId));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    String jsonCancelWorkflowResponse = response.getContentAsString();
	    System.out.println("jsonCancelWorkflowResponse="+getPrettyJSON(jsonCancelWorkflowResponse));
	    Assert.assertEquals(response.getStatus(), 200);
	    Assert.assertTrue(jsonCancelWorkflowResponse.indexOf("successfully")!=-1);
	    
	    Workflow workflow = this.getWorkflow(workflowId);
	    System.out.println("testCancelledWorkflow.workflow.status="+workflow.getStatus());
	    Assert.assertEquals(workflow.getStatus(), WorkflowStatus.CANCELED);
	}
	
	@Test
	public void testGetTask() throws URISyntaxException{
		Long workflowId = this.testWorkFlow.getId();
		List<Task> tasks = this.getWorkflowTasks(workflowId);
		Task firstTask = tasks.get(0);
		long taskId = firstTask.getId();
		MockHttpRequest request = MockHttpRequest.get(String.format("/ws/task/workflow/%s/task/%s", workflowId, taskId));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    Assert.assertEquals(response.getStatus(), 200);
	    
	    String jsonGetTaskResponse = response.getContentAsString();
	    System.out.println("jsonGetTaskResponse="+getPrettyJSON(jsonGetTaskResponse));
	    Task task = new Gson().fromJson(jsonGetTaskResponse, Task.class);
	    Assert.assertTrue(task!=null);
	    Assert.assertTrue(task.getId()==firstTask.getId());
	    Assert.assertTrue(task.getName().equals(firstTask.getName()));
	    Assert.assertTrue(task.getStatus().equals(firstTask.getStatus()));
	}
	
	@Test
	public void testGetTaskResult() throws URISyntaxException{
		Long workflowId = this.testWorkFlow.getId();
		List<Task> tasks = this.getWorkflowTasks(workflowId);
		Task firstTask = tasks.get(0);
		long taskId = firstTask.getId();
		this.saveTaskEvent(firstTask);
		MockHttpRequest request = MockHttpRequest.get(String.format("/ws/task/workflow/%s/task/%s/result", workflowId, taskId));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    Assert.assertEquals(response.getStatus(), 200);
	    
	    String jsonGetTaskResultResponse = response.getContentAsString();
	    System.out.println("jsonGetTaskResultResponse="+getPrettyJSON(jsonGetTaskResultResponse));
	    
	    GetTaskResultResponse getTaskResultResponse = GetTaskResultResponse.fromJson(jsonGetTaskResultResponse);
	    TaskResult taskResult = getTaskResultResponse.getTaskResult();
	    Assert.assertTrue(taskResult!=null);
	    Assert.assertTrue(taskResult.getWorkflowId()==workflowId);
	    Assert.assertTrue(taskResult.getResult().indexOf("1000")!=-1);
	}
	
	private void saveTaskEvent(Task task) throws URISyntaxException{
		MockHttpRequest request = MockHttpRequest.post("/ws/taskevent");
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
	    
	    SaveTaskEventRequest saveTaskEventRequest = this.getTestSaveTaskEventRequest(task);
		String jsonSaveTaskEventRequest = saveTaskEventRequest.toJson();
		System.out.println(getPrettyJSON(jsonSaveTaskEventRequest));
			    
	    request.content(jsonSaveTaskEventRequest.getBytes());
	    
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
		
	}
	
	private SaveTaskEventRequest getTestSaveTaskEventRequest(Task task){
		SaveTaskEventRequest saveTaskEventRequest = new SaveTaskEventRequest();
		
		VoTaskEvent voTaskEvent = new VoTaskEvent();
		long taskWorkFlowId = task.getWorkflowId();
		long taskId = task.getId();
		String taskEventName = "Done";
		String taskEventData = "{'result':1000}";
		long taskEventTime = System.currentTimeMillis();
		voTaskEvent.setTaskWorkFlowId(taskWorkFlowId);
		voTaskEvent.setTaskId(taskId);
		voTaskEvent.setTaskEventName(taskEventName);
		voTaskEvent.setTaskEventData(taskEventData);
		voTaskEvent.setTaskEventTime(taskEventTime);
		
		saveTaskEventRequest.setVoTaskEvent(voTaskEvent);
		return saveTaskEventRequest;
	}
	
	private List<Task> getWorkflowTasks(long workflowId) throws URISyntaxException{
		MockHttpRequest request = MockHttpRequest.get(String.format("/ws/task/workflow/%s", workflowId));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    String jsonGetTasksResponse = response.getContentAsString();
	    System.out.println("jsonGetTasksResponse="+getPrettyJSON(jsonGetTasksResponse));
	    GetTasksResponse getTasksResponse = GetTasksResponse.fromJson(jsonGetTasksResponse);
	    List<Task> tasks = getTasksResponse.getTasks();
	    Assert.assertTrue(tasks!=null && tasks.size()==this.getTestCreateWorkflowRequest().getVoWorkflow().getVoTasks().size());
	    return tasks;
	}
	
	private void deleteWorkflow(Workflow workflow) throws URISyntaxException{
		MockHttpRequest request = MockHttpRequest.delete(String.format("/ws/workflow/%s", workflow.getId()));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    String jsonDeleteWorkflowResponse = response.getContentAsString();
	    System.out.println("jsonDeleteWorkflowResponse="+getPrettyJSON(jsonDeleteWorkflowResponse));
	    Assert.assertEquals(response.getStatus(), 200);
	    assertTrue(jsonDeleteWorkflowResponse.indexOf("successfully")!=-1);
	}
	
	private CreateWorkflowRequest getTestCreateWorkflowRequest(){
		CreateWorkflowRequest createWorkFlowRequest = new CreateWorkflowRequest();
		
		VoWorkflow voWorkflow = new VoWorkflow();
		
		List<VoTask> voTasks = new ArrayList<VoTask>();
		VoTask voTask1 = new VoTask();
		voTask1.setName("upgrade-10.194.132.247");
		voTask1.setData("{'event':'Upgrade','scope':'10.194.132.247','components':'webconsole'}");
		voTask1.setType("Service");
		voTask1.setTimeout(60);
		
		VoTask voTask2 = new VoTask();
		voTask2.setName("upgrade-10.194.132.248");
		voTask2.setData("{'event':'Upgrade','scope':'10.194.132.248','components':'webconsole'}");
		voTask2.setType("Service");
		voTask2.setTimeout(60);
		
		VoTask voTask3 = new VoTask();
		voTask3.setName("upgrade-10.194.132.249");
		voTask3.setData("{'event':'Upgrade','scope':'10.194.132.249','components':'webconsole'}");
		voTask3.setType("Service");
		voTask3.setTimeout(60);
		
		voTasks.add(voTask1);
		voTasks.add(voTask2);
		voTasks.add(voTask3);
		
		List<VoSequenceFlow> voSequenceFlows = new ArrayList<VoSequenceFlow>();
		
		VoSequenceFlow voSequenceFlow = new VoSequenceFlow();
		String fromTask = "upgrade-10.194.132.247";
		String toTask = "upgrade-10.194.132.248";
		long intervalTime = 60 ;
		voSequenceFlow.setFromTask(fromTask);
		voSequenceFlow.setToTask(toTask);
		voSequenceFlow.setIntervalTime(intervalTime);
		
		VoSequenceFlow voSequenceFlow2 = new VoSequenceFlow();
		String fromTask2 = "upgrade-10.194.132.248";
		String toTask2 = "upgrade-10.194.132.249";
		long intervalTime2 = 60 ;
		voSequenceFlow2.setFromTask(fromTask2);
		voSequenceFlow2.setToTask(toTask2);
		voSequenceFlow2.setIntervalTime(intervalTime2);
		
		voSequenceFlows.add(voSequenceFlow);
		voSequenceFlows.add(voSequenceFlow2);
		
		voWorkflow.setName("upgrade-env-dev1");
		voWorkflow.setScheduledStartTime(System.currentTimeMillis());
		voWorkflow.setVoTasks(voTasks);
		voWorkflow.setVoSequenceFlows(voSequenceFlows);
		voWorkflow.setAppId("admin.devopsplatform.org");
		String appTaskQueue = "{'provider':'rabbitmq', 'username':'guest', 'password':'guest','vhost':'/','exchange':'devops-platform-task-exchange','queue':'devops.platform.taskqueue'}";
		voWorkflow.setAppTaskQueue(appTaskQueue);
		
		createWorkFlowRequest.setVoWorkflow(voWorkflow);
		
		return createWorkFlowRequest;
	}
	
	private Workflow createWorkFlow() throws Exception {
		MockHttpRequest request = MockHttpRequest.post("/ws/workflow");
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
	    
	    CreateWorkflowRequest createWorkFlowRequest = this.getTestCreateWorkflowRequest();
		String jsonCreateWorkFlowRequest = createWorkFlowRequest.toJson();
		System.out.println(getPrettyJSON(jsonCreateWorkFlowRequest));
			    
	    request.content(jsonCreateWorkFlowRequest.getBytes());
	    
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    
	    String jsonCreateWorkflowResponse = response.getContentAsString();
	    System.out.println("jsonCreateWorkflowResponse="+getPrettyJSON(jsonCreateWorkflowResponse));
	    
	    CreateWorkflowResponse createWorkflowResponse = new Gson().fromJson(jsonCreateWorkflowResponse, CreateWorkflowResponse.class);
	    Long workflowId = createWorkflowResponse.getId();
	    assertTrue(workflowId>0) ;
	    Assert.assertEquals(response.getStatus(), 200);
	    
	    return createWorkflowResponse.toWorkflow();
	}
	
	private Workflow getWorkflow(long workflowId) throws URISyntaxException{
		MockHttpRequest request = MockHttpRequest.get(String.format("/ws/workflow/%s", workflowId));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    String jsonGetWorkflowResponse = response.getContentAsString();
	    System.out.println("jsonGetWorkflowResponse="+getPrettyJSON(jsonGetWorkflowResponse));
	    GetWorkflowResponse getWorkflowResponse = GetWorkflowResponse.fromJson(jsonGetWorkflowResponse);
	    Workflow workflow = getWorkflowResponse.toWorkflow();
	    return workflow;
	}
	
	public static void trace(StackTraceElement e[]) {
		boolean doNext = false;
		for (StackTraceElement s : e) {
			if (doNext) {
				logger.info("");
				logger.info("-------------------------------------");
				logger.info(s.getMethodName());
				logger.info("-------------------------------------");
				return;
			}
			doNext = s.getMethodName().equals("getStackTrace");
		}
	}
	
	private static String getPrettyJSON(String jsonStr){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonStr);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}

}
