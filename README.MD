# Job Scheduler System

![image](https://user-images.githubusercontent.com/61826031/120964878-a05ba000-c781-11eb-9dd1-5e9d6b5504c7.png)

# About

A Job Management Service is one that can handle scheduling and execution of multiple types of Jobs. 

System is:
<ul>
  <li><strong>Flexible</strong>: System is not aware of the Jobs. New Jobs should be supported without re-developing the System.</li>
  <li><strong>Reliable</strong>: Each job should either complete successfully or leave the system unaffected, in case of failure.</li>
  <li><strong>Internal Consistent</stron>:	Jobs can have four states: QUEDED, RUNNING, SUCCESS, FAILED. Following execution of Job, it should be left in appropriate state.</li>
  <li><strong>Scheduling</strong>: System should allow jobs to be performed immediately or according to a schedule.</li>
  <li><strong>Priority</strong>: Jobs should be executed based on its priority relative to other jobs.</li>
</ul>

# Usage

## Prerequisites
<ul>
  <li>Docker</li>
</ul>

## Build and Deploy Project
In Root Directory of the Project, execute following command to spin up all Services 

```
  docker-compose up
```

## Test Appliction From Browser

<ul>
  <li>In a browser open http://localhost:4200. This is where the angular app is running. You should see a screen like this</li>

  ![image](https://user-images.githubusercontent.com/61826031/120966124-6be8e380-c783-11eb-8c43-61783be0bb9f.png)
  
  <li>Click on Add A Job at top right corner. Fill in details of the Job as shown below:</li>

  ![image](https://user-images.githubusercontent.com/61826031/120966185-815e0d80-c783-11eb-837e-ee91a446f1b5.png)

  Request URL: http://sample-job:8085/job/short-success   points to Sample Job Application developed for testing the Job Scheduler System

  <li>Click on Add Job. Once Job is submitted you should see the Job List with your newly added Job. Add a few more jobs. You should see something like this after adding a few Jobs</li>
  
![image](https://user-images.githubusercontent.com/61826031/120966229-95097400-c783-11eb-8efb-570321680a3f.png)

  <li>5.	Give it some time and Refresh to see updated Status of Jobs.</li>
  
![image](https://user-images.githubusercontent.com/61826031/120966279-a3579000-c783-11eb-87ea-31ac83d08d22.png)

  The Jobs List Dashboard, provides information on Job Id, Job Name, Job Execution Type, Job Priority and Job Status.
</ul>

## Author

<strong>Manas Acharyya</strong>

  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://www.linkedin.com/in/manasacharyya25" target="_blank">linkedin.com/in/manasacharyya25</a></p>


