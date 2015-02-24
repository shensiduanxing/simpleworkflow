
##Initiated:

  task is created, not runnable, such as it is waiting for dependent tasks done then because runnable.

##Runnable:
	
  task is runnable, can be dispatched to internal or external TaskExecutor to execute.

##Dispatched:
        
  task is dispatched to TaskExecutor after became Runnable.

#Running:
        
  TaskExecutor is executing task, task is not finished

#Done:
  
  Task execution done

#Fail: 
  
  Task failed.

#Timeout:
  
  Task execution timeout.
