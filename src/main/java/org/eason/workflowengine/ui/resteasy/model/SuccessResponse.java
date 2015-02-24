package org.eason.workflowengine.ui.resteasy.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SuccessResponse {
	private int code;
    private String message;
    
    public SuccessResponse(){
    }
    
    public SuccessResponse(String message){
    	this.code = 0;
    	this.message = message;
    }
    
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toJson(){
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String jsonErrorResponse= gson.toJson(this);
		return jsonErrorResponse;
	}
}
