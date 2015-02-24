package org.eason.workflowengine.ui.resteasy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eason.workflowengine.domain.common.model.SequenceFlow;

@XmlRootElement
public class VoSequenceFlow {
    
	private String fromTask;
	private String toTask;
	private long intervalTime;
	
	@XmlElement
	public String getFromTask() {
		return fromTask;
	}
	public void setFromTask(String fromTask) {
		this.fromTask = fromTask;
	}
	@XmlElement
	public String getToTask() {
		return toTask;
	}
	public void setToTask(String toTask) {
		this.toTask = toTask;
	}
	@XmlElement
	public long getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}
	
	public SequenceFlow toSequenceFlow(){
		SequenceFlow sequenceFlow = new SequenceFlow();
		sequenceFlow.setFromTask(fromTask);
		sequenceFlow.setToTask(toTask);
		sequenceFlow.setIntervalTime(intervalTime);
		return sequenceFlow;
	}

}
