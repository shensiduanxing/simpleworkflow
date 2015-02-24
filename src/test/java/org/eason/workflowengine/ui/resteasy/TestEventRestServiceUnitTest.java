package org.eason.workflowengine.ui.resteasy;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.model.TaskEvent;
import org.eason.workflowengine.ui.resteasy.model.GetTaskEventResponse;
import org.eason.workflowengine.ui.resteasy.model.GetTaskEventsResponse;
import org.eason.workflowengine.ui.resteasy.model.SaveTaskEventRequest;
import org.eason.workflowengine.ui.resteasy.model.SaveTaskEventResponse;
import org.eason.workflowengine.ui.resteasy.model.VoTaskEvent;
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
public class TestEventRestServiceUnitTest {
	
	@Autowired
	TaskEventResource taskEventResource;
	
	private Dispatcher dispatcher;
	
	private static Logger logger = Logger.getLogger(WorkflowRestServiceUnitTest.class);
	
	private TaskEvent testTaskEvent;

	@Before
	public void setUp() throws Exception {
		this.dispatcher = MockDispatcherFactory.createDispatcher();
	    dispatcher.getRegistry().addSingletonResource(this.taskEventResource);
	    this.testTaskEvent = this.createTaskEvent();
	}

	@After
	public void tearDown() throws Exception {
		this.deleteTaskEvent(this.testTaskEvent);
	}
	
	@Test
	public void testCreateWorkflow(){
		Long taskEventId = this.testTaskEvent.getId();
		System.out.println("taskEvent id is "+taskEventId);
		assertTrue(this.testTaskEvent!=null);
	}
	
	@Test
	public void testGetTaskEvent() throws URISyntaxException{
		Long taskEventId = this.testTaskEvent.getId();
		MockHttpRequest request = MockHttpRequest.get(String.format("/ws/taskevent/%s", taskEventId));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    String jsonGetTaskEventResponse = response.getContentAsString();
	    System.out.println("jsonGetTaskEventResponse="+getPrettyJSON(jsonGetTaskEventResponse));
	    GetTaskEventResponse getTaskEventResponse = GetTaskEventResponse.fromJson(jsonGetTaskEventResponse);
	    Assert.assertEquals(response.getStatus(), 200);
	    Assert.assertEquals(this.testTaskEvent.getName(),  getTaskEventResponse.getName());
	}
	
	@Test
	public void testGetTaskEvents() throws URISyntaxException{
		Long taskId = this.testTaskEvent.getTaskId();
		long workflowId = testTaskEvent.getWorkflowId();
		MockHttpRequest request = MockHttpRequest.get(String.format("/ws/taskevent/workflow/%s/task/%s", workflowId, taskId));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    String jsonGetTaskEventsResponse = response.getContentAsString();
	    System.out.println("jsonGetTaskEventsResponse="+getPrettyJSON(jsonGetTaskEventsResponse));
	    GetTaskEventsResponse getTaskEventsResponse = GetTaskEventsResponse.fromJson(jsonGetTaskEventsResponse);
	    Assert.assertEquals(response.getStatus(), 200);
	    List<TaskEvent> taskEvents = getTaskEventsResponse.getTaskEvents();
	    Assert.assertTrue(taskEvents!=null);
	    for(TaskEvent taskEvent : taskEvents){
	    	System.out.println(String.format("%s-%s-%s-%s", taskEvent.getId(), taskEvent.getName(), 
	    			taskEvent.getWorkflowId(), taskEvent.getTaskId()));
	    	Assert.assertTrue(taskEvent.getWorkflowId()==workflowId);
	    	Assert.assertTrue(taskEvent.getTaskId()==taskId);
	    }
	    Assert.assertTrue(taskEvents.size()>0);
	}
	
	@Test
	public void testGetWorkflowTaskEvents() throws URISyntaxException{
		long workflowId = testTaskEvent.getWorkflowId();
		MockHttpRequest request = MockHttpRequest.get(String.format("/ws/taskevent/workflow/%s", workflowId));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    String jsonGetTaskEventsResponse = response.getContentAsString();
	    System.out.println("jsonGetTaskEventsResponse="+getPrettyJSON(jsonGetTaskEventsResponse));
	    GetTaskEventsResponse getTaskEventsResponse = GetTaskEventsResponse.fromJson(jsonGetTaskEventsResponse);
	    Assert.assertEquals(response.getStatus(), 200);
	    List<TaskEvent> taskEvents = getTaskEventsResponse.getTaskEvents();
	    Assert.assertTrue(taskEvents!=null);
	    for(TaskEvent taskEvent : taskEvents){
	    	System.out.println(String.format("%s-%s-%s-%s", taskEvent.getId(), taskEvent.getName(), 
	    			taskEvent.getWorkflowId(), taskEvent.getTaskId()));
	    	Assert.assertTrue(taskEvent.getWorkflowId()==workflowId);
	    }
	    Assert.assertTrue(taskEvents.size()>0);
	}
	
	private void deleteTaskEvent(TaskEvent taskEvent) throws URISyntaxException{
		MockHttpRequest request = MockHttpRequest.delete(String.format("/ws/taskevent/%s", taskEvent.getId()));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    String jsonDeleteTaskEventResponse = response.getContentAsString();
	    System.out.println("jsonDeleteTaskEventResponse="+getPrettyJSON(jsonDeleteTaskEventResponse));
	    Assert.assertEquals(response.getStatus(), 200);
	    assertTrue(jsonDeleteTaskEventResponse.indexOf("successfully")!=-1);
	}
	
	private SaveTaskEventRequest getTestSaveTaskEventRequest(){
		SaveTaskEventRequest saveTaskEventRequest = new SaveTaskEventRequest();
		
		VoTaskEvent voTaskEvent = new VoTaskEvent();
		long taskWorkFlowId = 1;
		long taskId = 1;
		String taskEventName = "Start";
		String taskEventData = "test data";
		long taskEventTime = System.currentTimeMillis();
		voTaskEvent.setTaskWorkFlowId(taskWorkFlowId);
		voTaskEvent.setTaskId(taskId);
		voTaskEvent.setTaskEventName(taskEventName);
		voTaskEvent.setTaskEventData(taskEventData);
		voTaskEvent.setTaskEventTime(taskEventTime);
		
		saveTaskEventRequest.setVoTaskEvent(voTaskEvent);
		return saveTaskEventRequest;
	}
	
	private TaskEvent createTaskEvent() throws Exception {
		MockHttpRequest request = MockHttpRequest.post("/ws/taskevent");
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
	    
	    SaveTaskEventRequest saveTaskEventRequest = this.getTestSaveTaskEventRequest();
		String jsonSaveTaskEventRequest = saveTaskEventRequest.toJson();
		System.out.println(getPrettyJSON(jsonSaveTaskEventRequest));
			    
	    request.content(jsonSaveTaskEventRequest.getBytes());
	    
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    
	    String jsonSaveTaskEventResponse = response.getContentAsString();
	    System.out.println("jsonSaveTaskEventResponse="+getPrettyJSON(jsonSaveTaskEventResponse));
	    
	    SaveTaskEventResponse saveTaskEventResponse = new Gson().fromJson(jsonSaveTaskEventResponse, SaveTaskEventResponse.class);
	    Long taskEventId = saveTaskEventResponse.getId();
	    assertTrue(taskEventId>0) ;
	    Assert.assertEquals(response.getStatus(), 200);
	    
	    return saveTaskEventResponse.toTaskEvent();
	}
	
	private TaskEvent getTaskEvent(long taskEventId) throws URISyntaxException{
		MockHttpRequest request = MockHttpRequest.get(String.format("/ws/taskevent/%s", taskEventId));
	    request.accept(MediaType.APPLICATION_JSON);
	    request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
	    
	    dispatcher.invoke(request, response);
	    String jsonGetTaskEventResponse = response.getContentAsString();
	    System.out.println("jsonGetTaskEventResponse="+getPrettyJSON(jsonGetTaskEventResponse));
	    GetTaskEventResponse getTaskEventResponse = GetTaskEventResponse.fromJson(jsonGetTaskEventResponse);
	    TaskEvent taskEvent = getTaskEventResponse.toTaskEvent();
	    return taskEvent;
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
