package com.examples.JobSchedulerSystem.service;

import com.examples.JobSchedulerSystem.common.ApplicationConstants;
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
public class JobService
{
  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  @Autowired
  private ThreadPoolTaskScheduler taskScheduler;

  public List<Job> getJobs() {
    return jobRepository.findAll();
  }

  public Optional<Job> getJobById(Long id) {
    Optional<Job> res =  jobRepository.findById(id);
    return res;
  }

  public Long scheduleJob(Job job) {
    Job savedJob = jobRepository.saveAndFlush(job);
    Long savedJobId = savedJob.getId();

    String jobExecutionType = savedJob.getExecutionType().name();

    if (jobExecutionType.equals(ApplicationConstants.SCHEDULED_JOB)) {
      runJobScheduler(job);
    }
    else {
      runImmediateJob(job);
    }
    return savedJobId;
  }

  private void runImmediateJob(Job job)
  {
    String jobType = job.getType().name();

    ListenableFuture<SendResult<String, Object>> future = this.kafkaTemplate.send(jobType, job.getId());
    future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

      @Override
      public void onSuccess(SendResult<String, Object> result) {
        System.out.println("Success");
      }

      @Override
      public void onFailure(Throwable ex) {
        System.out.println("Failed");
      }
    });

    // TODO: Deterrminne Priority Queue Implementtionn inn Kafka
  }

  private void runJobScheduler(Job job)
  {
    taskScheduler.scheduleAtFixedRate(new RunnableJob(job),Long.parseLong(job.getScheduleInterval()));
  }
}
