package org.eason.workflowengine.ui.resteasy.exception;

import java.io.Serializable;

public class WorkflowServiceException extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 7786141544419367058L;
	
	public WorkflowServiceException(){
		super();
	}
	
	public WorkflowServiceException(String message, Throwable cause){
		super(message, cause);
	}
	public WorkflowServiceException(Throwable cause){
		super(cause);
	}
	public WorkflowServiceException(String msg){
		super(msg);
	}

}
