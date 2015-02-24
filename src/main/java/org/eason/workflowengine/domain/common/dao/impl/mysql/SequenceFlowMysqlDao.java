package org.eason.workflowengine.domain.common.dao.impl.mysql;

import java.util.List;

import org.eason.workflowengine.domain.common.dao.ISequenceFlowDao;
import org.eason.workflowengine.domain.common.model.SequenceFlow;
import org.eason.workflowengine.domain.common.model.SequenceFlowExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceFlowMysqlDao implements ISequenceFlowDao {
    
	@Autowired
	SequenceFlowMapper sequenceFlowMapper;

	public SequenceFlow createSequenceFlow(SequenceFlow sequenceFlow) {
		sequenceFlowMapper.insert(sequenceFlow);
		return sequenceFlow;
	}

	public void deleteSequenceFlow(long sequenceFlowId) {
		sequenceFlowMapper.deleteByPrimaryKey(sequenceFlowId);
	}

	public List<SequenceFlow> getSequenceFlows(long workflowId) {
		SequenceFlowExample example = new SequenceFlowExample();
		SequenceFlowExample.Criteria criteria = example.createCriteria();
		criteria.andWorkflowIdEqualTo(workflowId);
		List<SequenceFlow> sequenceFlows = sequenceFlowMapper.selectByExample(example);
		return sequenceFlows;
	}
	
	
}
