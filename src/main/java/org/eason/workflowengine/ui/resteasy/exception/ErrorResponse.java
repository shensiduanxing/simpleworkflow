package org.eason.workflowengine.ui.resteasy.exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ErrorResponse {
    private int code;
    private String message;
    
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
