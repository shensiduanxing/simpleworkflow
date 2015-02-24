package org.eason.workflowengine.ui.resteasy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

@XmlRootElement
public class SaveTaskEventRequest {
	
	private VoTaskEvent voTaskEvent;

	@XmlElement
	public String toJson(){
		return new Gson().toJson(this);
	}

	public VoTaskEvent getVoTaskEvent() {
		return voTaskEvent;
	}

	public void setVoTaskEvent(VoTaskEvent voTaskEvent) {
		this.voTaskEvent = voTaskEvent;
	}
    
}
