package com.examples.JobSchedulerSystem.service;

import com.examples.JobSchedulerSystem.common.ApplicationConstants;
import com.examples.JobSchedulerSystem.model.Job;
import com.examples.JobSchedulerSystem.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService
{
  @Autowired
  private JobRepository jobRepo;

  @Autowired JobRunnerService jobRunnerService;

  @KafkaListener(topics = ApplicationConstants.SHORT_LIVED_HIGH_PRIORITY_JOB, groupId = "job_scheduler", containerFactory = "jobKafkaListenerContainerFactory")
  public void executeShortLivedHighPriorityTask(String jobId) {
    System.out.println("Processing Job : "+jobId);
    Job job = jobRepo.findById(Integer.parseInt(jobId)).get();

    //  CHECK IF JOB HAS BEEN CANCELLED ALREADY..
    if (job.getStatus().name().equals(ApplicationConstants.QUEUED_JOB_STATUS)) {
      jobRunnerService.runJob(job);
    }
  }

  @KafkaListener(topics = ApplicationConstants.SHORT_LIVED_LOW_PRIORITY_JOB, groupId = "job_scheduler")
  public void executeShortLivedLowPriorityTask(String jobId) {
    System.out.println("Processing Job : "+jobId);
    Job job = jobRepo.findById(Integer.parseInt(jobId)).get();

    //  CHECK IF JOB HAS BEEN QUEUED/CANCELLED ALREADY..
    if (job.getStatus().name().equals(ApplicationConstants.QUEUED_JOB_STATUS)) {
      jobRunnerService.runJob(job);
    }
  }

  @KafkaListener(topics = ApplicationConstants.LONG_RUNNING_JOB, groupId = "job_scheduler")
  public void executeLongRunningTask(String jobId) {
    System.out.println("Processing Job : "+jobId);
    Job job = jobRepo.findById(Integer.parseInt(jobId)).get();

    //  CHECK IF JOB HAS BEEN QUEUED/CANCELLED ALREADY..
    if (job.getStatus().name().equals(ApplicationConstants.QUEUED_JOB_STATUS)) {
      jobRunnerService.runJob(job);
    }
  }
}
