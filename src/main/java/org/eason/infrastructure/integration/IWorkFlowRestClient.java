package org.eason.infrastructure.integration;

import org.eason.workflowengine.ui.resteasy.model.VoTaskEvent;

public interface IWorkFlowRestClient {
	public void saveTaskEvent(VoTaskEvent voTaskEvent);
}
