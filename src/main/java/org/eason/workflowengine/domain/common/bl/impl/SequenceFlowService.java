package org.eason.workflowengine.domain.common.bl.impl;

import org.eason.workflowengine.domain.common.dao.ISequenceFlowDao;
import org.eason.workflowengine.domain.common.model.SequenceFlow;


public class SequenceFlowService {
	
	private ISequenceFlowDao sequenceFlowDao;
	
	public void setSequenceFlowDao(ISequenceFlowDao sequenceFlowDao){
		this.sequenceFlowDao = sequenceFlowDao;
	}
    
	public SequenceFlow createSequenceFlow(SequenceFlow sequenceFlow){
		SequenceFlow sequenceFlowEntity = sequenceFlowDao.createSequenceFlow(sequenceFlow);
		return sequenceFlowEntity;
	}
}
