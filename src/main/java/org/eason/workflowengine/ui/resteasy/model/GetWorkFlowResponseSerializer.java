package org.eason.workflowengine.ui.resteasy.model;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GetWorkFlowResponseSerializer implements JsonSerializer<GetWorkflowResponse> {

	public JsonElement serialize(GetWorkflowResponse getWorkFlowResponse, Type typeOfSrc,
			JsonSerializationContext context) {
		
		JsonObject jsonObject=new JsonObject();
        
        jsonObject.addProperty("id", getWorkFlowResponse.getId());
        jsonObject.addProperty("name", getWorkFlowResponse.getName());
        jsonObject.addProperty("status", getWorkFlowResponse.getStatus());
        jsonObject.addProperty("createTime", getWorkFlowResponse.getCreateTime());
        jsonObject.addProperty("scheduledStartTime", getWorkFlowResponse.getScheduledStartTime());
        jsonObject.addProperty("doneTime", getWorkFlowResponse.getDoneTime());
        jsonObject.addProperty("timeoutTIme", getWorkFlowResponse.getTimeoutTime());
    	
        JsonElement tasks=context.serialize(getWorkFlowResponse.getTasks());
         
        //write the final json array to jsonObject
        jsonObject.add("tasks", tasks);
         
        return jsonObject;
	}

}
