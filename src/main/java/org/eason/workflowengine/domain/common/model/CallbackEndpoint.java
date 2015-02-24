package org.eason.workflowengine.domain.common.model;

import com.google.gson.Gson;

public class CallbackEndpoint {
    
	private String taskEventApiUrl;
	private String username;
	private String password;
	
	public String getTaskEventApiUrl() {
		return taskEventApiUrl;
	}
	public void setTaskEventApiUrl(String taskEventApiUrl) {
		this.taskEventApiUrl = taskEventApiUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
