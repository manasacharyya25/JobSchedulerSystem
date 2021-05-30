package com.examples.JobSchedulerSystem.service;

import com.examples.JobSchedulerSystem.model.Job;
import org.springframework.data.domain.PageRequest;

public class RunnableJob implements Runnable
{
  private Job job;

  public RunnableJob(Job job) {
    this.job = job;
  }

  @Override
  public void run()
  {
      System.out.println("Running Scheduled Job : "+this.job.getId());
  }
}
