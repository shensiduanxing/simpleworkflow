package org.eason.workflowengine.domain.common.model;

/**
 * This is framework level task event name, app developer can extend this class to 
 * add more app level event names.
 * @author shensiduanxing
 *
 */
public class TaskEventName {
    public static final String START = "Start";
    public static final String DONE = "Done";
    public static final String FAILED = "Failed";
    public static final String TIMEOUT = "Timeout";
}
