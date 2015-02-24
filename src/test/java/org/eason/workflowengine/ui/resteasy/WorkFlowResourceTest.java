package org.eason.workflowengine.ui.resteasy;


import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.model.Workflow;
import org.eason.workflowengine.ui.resteasy.model.GetWorkflowResponse;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:workflow-test-context.xml")
public class WorkFlowResourceTest {
	private Logger logger = Logger.getLogger(WorkFlowResourceTest.class);
	
	private final static String END_POINT = "http://localhost:9080";

	@Autowired
	WorkflowResource workFlowResourceRef;
	
	@Autowired
	Workflow testUpgradeFarmWorkFlow;
	
	private static TJWSEmbeddedJaxrsServer tjws;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tjws = new TJWSEmbeddedJaxrsServer();
	    tjws.setPort(9080);
	    tjws.start();
	}
	
	@Before
	public void setUp() throws Exception {
		tjws.getDeployment().getRegistry().addSingletonResource(workFlowResourceRef);
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void testGetWorkFlow() {
	    String strGetWorkFlowURL = String.format("%s/ws/workflow/1", END_POINT);
	    try {
			ClientRequest request = new ClientRequest(strGetWorkFlowURL);
			request.accept(MediaType.APPLICATION_JSON);
			ClientResponse<GetWorkflowResponse> response = request.get(GetWorkflowResponse.class);
 
			assert response.getStatus() == Status.OK.ordinal();
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}
 
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//				new ByteArrayInputStream(response.getEntity().getBytes())));
// 
//			StringBuilder sb = new StringBuilder();
//			String line;
//			logger.info("Output from Server .... \n");
//			while ((line = br.readLine()) != null) {
//				sb.append(line).append("\n");
//			}
//			String jsonGetWorkFlowResponse = sb.toString();
//			logger.info(jsonGetWorkFlowResponse);
//			GetWorkFlowResponse getWorkFlowResponse = GetWorkFlowResponse.fromJson(jsonGetWorkFlowResponse);
			GetWorkflowResponse getWorkFlowResponse = response.getEntity();
			logger.info("getWorkFlowResponse.toJson()="+getWorkFlowResponse.toJson());
			assert getWorkFlowResponse!=null;
			//assert getWorkFlowResponse.getId() == testUpgradeFarmWorkFlow.getId();
			//assert getWorkFlowResponse.indexOf(testUpgradeFarmWorkFlow.getName())!=-1;
		} catch (IOException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	

	//@Test
	public void testCreateWorkFlow(){
//	    String strCreateWorkFlowURL = String.format("%s/ws/workflow", END_POINT);
//	    CreateWorkflowRequest createWorkFlowRequest = new CreateWorkflowRequest();
//	    createWorkFlowRequest.setName("upgradeFarm97");
//	    createWorkFlowRequest.setData("blurblurblurblur");
//	    createWorkFlowRequest.setScheduledStartTime(1402840162638L);
//	    createWorkFlowRequest.setTimeoutTime(10000);
//	    
//	    ClientRequest request = new ClientRequest(strCreateWorkFlowURL);
//	    request.accept(MediaType.APPLICATION_JSON);
//		request.body(MediaType.APPLICATION_JSON, createWorkFlowRequest);
//		
//		try {
//			ClientResponse<CreateWorkflowResponse> response = request.post(CreateWorkflowResponse.class);
//			//String jsonCreateWorkFlowResponse = response.getEntity();
//            assert response.getStatus() == Status.OK.ordinal();
//			CreateWorkflowResponse createWorkFlowResponse = response.getEntity();
//			assert createWorkFlowResponse!=null;
//            assert createWorkFlowResponse.getCreateTime() > 0;
//            assert createWorkFlowResponse.getStatus().equals(WorkflowStatus.CREATED);
//			logger.info("jsonCreateWorkFlowResponse=\n" + createWorkFlowResponse.toJson());
//			//logger.info("workFlowId=" + createWorkFlowResponse.getId());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	


}
