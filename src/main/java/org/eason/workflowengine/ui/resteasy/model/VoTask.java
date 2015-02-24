package org.eason.workflowengine.ui.resteasy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.TaskWithBLOBs;

@XmlRootElement
public class VoTask {
    
	private String name;
	private String type;
	private String data;
	private Integer timeout;
	
	@XmlElement
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@XmlElement
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public TaskWithBLOBs toTask(){
		TaskWithBLOBs task = new TaskWithBLOBs();
		task.setName(name);
		task.setData(data);
		task.setType(type);
		task.setTimeout(timeout);
		return task;
	}
	
	
}
