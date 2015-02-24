package org.eason.testapp.taskexecutor;

import org.eason.workflowengine.domain.common.model.Task;

public interface ITaskExecutor {
    public void execute(Task task);
}
