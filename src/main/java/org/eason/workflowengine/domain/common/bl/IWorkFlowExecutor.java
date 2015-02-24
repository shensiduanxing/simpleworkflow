package org.eason.workflowengine.domain.common.bl;

import org.eason.workflowengine.domain.common.model.Workflow;

public interface IWorkFlowExecutor {
    public void execute(Workflow workFlow);
}
