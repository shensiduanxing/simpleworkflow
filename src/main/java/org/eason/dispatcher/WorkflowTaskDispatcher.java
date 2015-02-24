package org.eason.dispatcher;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eason.workflowengine.domain.common.bl.impl.TaskService;
import org.eason.workflowengine.domain.common.dao.impl.mysql.WorkflowMysqlDao;
import org.eason.workflowengine.domain.common.model.CallbackEndpoint;
import org.eason.workflowengine.domain.common.model.TaskWithBLOBs;
import org.eason.workflowengine.domain.common.model.Workflow;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Dispatcher get task's target dispatch app taskqueue and send task to target app taskqueue
 * @author shensiduanxing
 *
 */
@Controller
public class WorkflowTaskDispatcher {
	
	private Logger logger = Logger.getLogger(WorkflowTaskDispatcher.class);
	
	@Autowired
	private WorkflowMysqlDao workflowMysqlDao;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CallbackEndpoint callbackEndpoint;
		
    public void dispatch(String strMessage){
    	logger.info("Received: " + strMessage);
    	//Dispatcher get task's target dispatch app taskqueue
    	Gson gson = new Gson();
    	TaskWithBLOBs task = gson.fromJson(strMessage, TaskWithBLOBs.class);
    	task.setCallbackEndpoint(this.callbackEndpoint.toJson());
    	long workflowId = task.getWorkflowId();
    	//To improve, put workflow's target app task queue to memcache
    	Workflow workflow = workflowMysqlDao.getWorkFlow(workflowId);
    	String jsonAppTaskQueue = workflow.getAppTaskQueue();
    	logger.info("send to jsonAppTaskQueue: " + jsonAppTaskQueue);
    	this.sendMessageToRabbitMQ(new Gson().toJson(task), jsonAppTaskQueue);
    	taskService.setTaskDispatched(task);
    }
    
    private void sendMessageToRabbitMQ(String strMessage, String jsonAppTaskQueue){
		Map<String, String> appTaskQueuePropertiesMap = new Gson().fromJson(jsonAppTaskQueue,  
                new TypeToken<Map<String, String>>() {  
                }.getType()); 
	    String provider = appTaskQueuePropertiesMap.get("provider");
		if(provider.equals("rabbitmq")){
			String host = appTaskQueuePropertiesMap.get("host");
			String username = appTaskQueuePropertiesMap.get("username");
			String password = appTaskQueuePropertiesMap.get("password");
			String vhost = appTaskQueuePropertiesMap.get("vhost");
			String exchange = appTaskQueuePropertiesMap.get("exchange");
			String queue = appTaskQueuePropertiesMap.get("queue");
	    	CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);  
	        connectionFactory.setUsername(username);  
	        connectionFactory.setPassword(password); 
	        connectionFactory.setVirtualHost(vhost);
	        RabbitTemplate template = new RabbitTemplate(connectionFactory);  
	        // The routing key is set to the name of the queue by the broker for the  
	        // default exchange.  
	        //template.setRoutingKey(queue);
	        // Where we will synchronously receive messages from  
	        template.setQueue(queue);
	        template.setExchange(exchange);
	        //String routingKey = queue;
	        template.convertAndSend(strMessage);
		}else if (provider.equals("restapi")){
			
		}
    }
    
    
}
