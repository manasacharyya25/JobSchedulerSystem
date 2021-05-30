package com.examples.JobSchedulerSystem.service;

import com.examples.JobSchedulerSystem.model.Job;
import com.examples.JobSchedulerSystem.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
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

    String jobType = savedJob.getType().name();

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


    return savedJobId;
  }
}
