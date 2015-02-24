package org.eason.workflowengine.domain.common.dao;

import java.util.List;

import org.eason.workflowengine.domain.common.model.SequenceFlow;

public interface ISequenceFlowDao {
    
	public SequenceFlow createSequenceFlow(SequenceFlow sequenceFlow);
	
	public void deleteSequenceFlow(long sequenceFlowId);
	
	public List<SequenceFlow> getSequenceFlows(long workflowId);
}
