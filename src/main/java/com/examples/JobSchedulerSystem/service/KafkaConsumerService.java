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

  @KafkaListener(topics = ApplicationConstants.SHORT_LIVED_JOB, groupId = "job_scheduler")
  public void executeShortLivedTask(String jobId) {
    Job job = jobRepo.findById(Long.parseLong(jobId)).get();

    //  CHECK IF JOB HAS BEEN CANCELLED ALREADY..
    if (job.getStatus().name().equals(ApplicationConstants.QUEUED_JOB_STATUS)) {
      System.out.println("Executing Job : "+job.getName());
    }
  }
}
