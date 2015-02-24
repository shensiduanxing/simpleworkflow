package org.eason.workflowengine.ui.resteasy.model;

import org.eason.workflowengine.ui.resteasy.exception.ErrorResponse;

public class ResponseHelper {

	public static String getJsonResponse(boolean success, String message){
    	if(success){
    		SuccessResponse successResponse = new SuccessResponse();
    		successResponse.setMessage(message);
    		return successResponse.toJson();
    	}else{
    	    ErrorResponse errorResponse = new ErrorResponse();
    	    errorResponse.setCode(500);
    	    errorResponse.setMessage(message);
    	    return errorResponse.toJson();
    	}
    }
}
