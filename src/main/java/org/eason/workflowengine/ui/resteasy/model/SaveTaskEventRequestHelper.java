package org.eason.workflowengine.ui.resteasy.model;

import org.eason.workflowengine.domain.common.model.Task;
import org.eason.workflowengine.domain.common.model.TaskEventName;

public class SaveTaskEventRequestHelper {
    public static SaveTaskEventRequest createSaveStartEventRequest(Task task){
    	SaveTaskEventRequest saveTaskEventRequest = new SaveTaskEventRequest();
    	
    	VoTaskEvent voTaskEvent = new VoTaskEvent();
    	voTaskEvent.setTaskId(task.getId());
    	voTaskEvent.setTaskWorkFlowId(task.getWorkflowId());
    	voTaskEvent.setTaskEventName(TaskEventName.START);
    	
    	saveTaskEventRequest.setVoTaskEvent(voTaskEvent);
		return saveTaskEventRequest;
    }
    
    public static SaveTaskEventRequest createSaveDoneEventRequest(Task task){
    	SaveTaskEventRequest saveTaskEventRequest = new SaveTaskEventRequest();
    	
    	VoTaskEvent voTaskEvent = new VoTaskEvent();
    	voTaskEvent.setTaskId(task.getId());
    	voTaskEvent.setTaskWorkFlowId(task.getWorkflowId());
    	voTaskEvent.setTaskEventName(TaskEventName.DONE);
    	
    	saveTaskEventRequest.setVoTaskEvent(voTaskEvent);
		return saveTaskEventRequest;
    }
}
