SimpleWorkflow
==============

Simple work flow is simple workflow framework, used for building simple full automation works orchestration by sync works, execute works in parallel.

There are lots of workflow open source, but lots of them are designed for enterprise's workflow that with human involved, such as activiti etc. they are a little complicated for cases that are simple such as just need to make several works execute in parallel, after these works done, then execute another patch works or work.

These scenarios for example:

##1. Build Load Test System

Can generate load in batch and by interval, such as first make 5 load agent to generate load together, after 5 mins, start another 5 load agents to generate load, etc.

##2. Build a continuous deployment system

Make upgrade an env as a workflow, then orchestrate the env upgrade by the workflow way.
for example, for an env, there are 1 nginx server, 3 web servers, 2 api servers, 1 db server,
to make graceful upgrade, minimize the service unavailable time during upgrade, we make make a workflow to 

  1) first upgrade db server db schema

  2) then upgrade 1 api server

  3) then upgrade 1 api server

  4) then upgrade 2 web servers

  5) then upgrade 1 webserver 2 mins later after the 2 web servers upgrade done.



#Get Started

##1. Install Required runtimes

  This framework built by Java, use maven, jdk1.6 and maven is required.

##2. Download

  git clone git@github.com:shensiduanxing/simpleworkflow.git

##3. Import to Eclipse

  File -> Import -> Existing Maven Projects -> <simpleworkflow dir>

##4. Examples

  You can get started by run example unit test @
  src/test/java/org/eason/workflowengine/WorkFlowServiceBasicTest.java

  This example demos you an exmaple plugin that emulating upgrade workflow of an env composed of
  several VMs, you do not need to install database for this demo.

##5. Dev workflow Plugin 

###5.1 Work Mechanism

  1) TaskDispatcher

  2) TaskExecutor

     can be internal and external, if external require to implement TaskDispatcher that can
     send task the external service.

  3) TaskHandler

  4) ForkJoinTask

  5) ServiceTask

###5.2 Upgrade Env Emulator Plugin Example

    demo Task dispatched to Internal TaskHandler.


###5.3 Upgrade Env by External TaskExecutor Example



Plan:

1. Implement create workflow, tasks, sequenceflows

2. Implement execute workflow schedulely

1) get Runnable tasks
2) send runnable tasks to taskqueue

3. Implement dispatch app tasks to app task queue

4. Implement test use app worker to get task from app task queue and update task's status/events  (put this test use app worker to simpleworkflow project)


Register App

Workflow Part:
Create WorkFlow       Done

Get WorkFlow          Done

Create SequenceFlows  Done

Delete Workflow       Done

Execute WorkFlow      Done

   schedule execute   Done
   
   get runnable tasks Done
   
   send runnable tasks to taskqueue  Done
   
   Dispatch tasks to app taskqueue   Done
   
   Implement from to tasks' interval Pending
   
Suspend WorkFlow      Done

Resume WorkFlow       Done

Cancel WorkFlow       Done

Save WorkFlow Event   Pending P2

Get App Workflows     

Task Part:
Create Tasks(Rest)          Done

Execute WorkFlow      Done
   get runnable tasks Done
   send runnable tasks to taskqueue  Done
   Dispatch tasks to app taskqueue   Done
   Implement from to tasks' interval Pending
   
Save Task Result      Done

   Task result will be saved in taskEvent done, failed event data
   
   save by TaskEventService call TaskService to save the result, TaskEventService can choose to save the data or not   

Set Task Timeout(Rest)
   2 ways, one way by Workflow engine, one way by app worker report timeout task event 
   
Get WorkFlow Tasks(Rest)       Done

Get Tasks Result(Rest) (Workflow id, task name)  Done

Set workflow Done when all tasks done  On-going


Task Events Part:

Save Task Event | Update Task Status (Rest)   Done

Get Task Events(Workflow id, task id) (Rest)  Done

Get Workflow Task Events(Workflow id) (Rest)  Done


1. Implement WorkFlowRepository to create Workflow entity

Workflow entity is able to get Tasks and SequenceFlows

2. Implement from to tasks' interval

3. Implement OAuth

4. System will keep appÕs latest 5000 workflows

5. System support 10K concurrent workflows execution

6. System can work normally when there are large 

7. Implement app can only get itself's workflows and tasks

8. Implement multiple tasks groups' dependency
































