package org.eason.workflowengine.domain.common.bl;

import org.eason.workflowengine.domain.common.model.TaskEvent;

public interface ITaskEventService {
	public void onEvent(TaskEvent taskEvent);
}
