package org.eason.infrastructure.integration;

import org.eason.workflowengine.ui.resteasy.TaskEventResource;
import org.eason.workflowengine.ui.resteasy.model.VoTaskEvent;


public class LocalFakeWorkFlowRestClient implements IWorkFlowRestClient {
	
	private TaskEventResource taskEventResource;
	
	public void setTaskEventResource(TaskEventResource taskEventResource){
		this.taskEventResource = taskEventResource;
	}

	public void saveTaskEvent(VoTaskEvent voTaskEvent) {
		this.taskEventResource.saveTaskEvent(voTaskEvent);
	}
    
}
