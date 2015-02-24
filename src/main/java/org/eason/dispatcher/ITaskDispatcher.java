package org.eason.dispatcher;

import org.eason.workflowengine.domain.common.model.Task;

public interface ITaskDispatcher {
    public void dispatch(Task task);
}
