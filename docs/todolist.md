
To Do List:

#1. Rest APIs 

  1) Create WorkFlow   Done.
  
  2) Save Task Event

save task event
update task status
update workflow status

  3) Execute WorkFlow
  
for some case that workflow is request to start in on-demand case.
implement by just set workflow as runnable

  4) Execute Task
  
for some case that add task to workflow and start the task on-demand or on schedule.
such as when do load test, can add a test task and execute on-demand or on schedule.

  5) Get WorkFlow.   Done.

limitation:

a. use Spring, Resteasy to implement
b. must have unit test, use resteasy unit test framework, can run test without start
web server

#2. Web
  
  1) Create WorkFlow
  
  2) Save Task Event(can only save Dispatched Task's Event on Web, Emulate the Task Executor)
  
  3) list WorkFlows
  
  4) Execute WorkFlow 
  
  5) Execute Task 
  
     only task that is below status can be executed:
     a. on schedule initiated status can be triggered to execute from web
  
  6) View WorkFlow Details


limitation:

a. use Spring WebMVC
b. use ace
c. use AngularJS
d. use backbone

#3. Implement OpsAgentEmulator

emulate env upgrade and validate workflow framework, this OpsAgentEmulator works in internal process
of simpleworkflow engine.

OpsAgentEmulator will emulate OpsAgent @ each VM of env to handle the tasks.

#4. Implement WorkFlow,Task MySQL DB DAO


#5. Implement WorkFlow,Task MongoDB DAO


#6. Add OAuth for Rest APIs

#7. Add HTTPS to secure Rest APIs

#8. Add User Authentication for Web

#9. Implement Load Test Plugin

#10.Implement Cloud Upgrade Env Plugin

#11. Implement WorkerManager|TaskDispatcher

  1) Register Worker
  
  2) Get Workers

  3) Receive Task from RabbitMQ and Dispatch Task to Worker according to task metadata
    
     for app, it is required to implement customized IDispatcher for special scenario

#12. Implement TaskRabbitMQDispatcher
  
  1) Dispatch Task to queue in RabbitMQ, task including worker info 
  
  
For a workflow creation:

1. create by human in WEb

2. create by app by RestAPI

   base on event
   create on-demand customization








 



