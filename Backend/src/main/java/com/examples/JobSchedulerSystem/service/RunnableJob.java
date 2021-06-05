package com.examples.JobSchedulerSystem.service;

import com.examples.JobSchedulerSystem.model.Job;

public class RunnableJob implements Runnable
{
  private JobRunnerService jobRunnerService;

  private Job job;

  public RunnableJob(Job job, JobRunnerService jobRunnerService) {
    this.job = job;
    this.jobRunnerService = jobRunnerService;
  }

  @Override
  public void run()
  {
      this.jobRunnerService.runJob(job);
  }
}
