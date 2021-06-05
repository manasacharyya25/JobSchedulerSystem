package com.examples.JobSchedulerSystem.service;

import com.examples.JobSchedulerSystem.common.ApplicationConstants;
import com.examples.JobSchedulerSystem.enums.JobStatus;
import com.examples.JobSchedulerSystem.model.Job;
import com.examples.JobSchedulerSystem.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.Optional;

@Service
public class JobSchedulerService
{
  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  @Autowired
  private ThreadPoolTaskScheduler taskScheduler;

  @Autowired
  private JobRunnerService jobRunnerService;

  public List<Job> getJobs()
  {
    return jobRepository.findAll();
  }

  public Job getJobById(Integer id)
  {
    Optional<Job> res = jobRepository.findById(id);
    return res.orElse(null);
  }

  public Integer addJob(Job job)
  {
    Job savedJob = jobRepository.saveAndFlush(job);
    Integer savedJobId = savedJob.getId();

    String jobExecutionType = savedJob.getExecutionType().name();

    if (jobExecutionType.equals(ApplicationConstants.SCHEDULED_JOB))
    {
      runScheduledJob(job);
    } else
    {
      runImmediateJob(job);
    }
    return savedJobId;
  }

  private void runImmediateJob(Job job)
  {
    String jobType = job.getType().name();

    ListenableFuture<SendResult<String, Object>> future = this.kafkaTemplate.send(jobType, job.getId());
    future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>()
    {
      @Override
      public void onSuccess(SendResult<String, Object> result)
      {
        jobRepository.updateJobStatus(job.getId(), JobStatus.QUEUED);
        System.out.println("Job Queued : "+ job.getId());
      }

      @Override
      public void onFailure(Throwable ex)
      {
        System.out.println("Failed to Queue Job");
      }
    });

    // TODO: Deterrminne Priority Queue Implementtionn inn Kafka
  }

  private void runScheduledJob(Job job)
  {
    taskScheduler.scheduleAtFixedRate(new RunnableJob(job, jobRunnerService), Long.parseLong(job.getScheduleInterval()));
  }
}
