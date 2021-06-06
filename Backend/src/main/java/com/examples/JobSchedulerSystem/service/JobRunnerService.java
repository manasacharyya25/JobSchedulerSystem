package com.examples.JobSchedulerSystem.service;

import com.examples.JobSchedulerSystem.common.ApplicationConstants;
import com.examples.JobSchedulerSystem.enums.JobStatus;
import com.examples.JobSchedulerSystem.model.Job;
import com.examples.JobSchedulerSystem.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobRunnerService
{
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private JobRepository jobRepo;

  public void runJob(Job job)
  {
    jobRepo.updateJobStatus(job.getId(), JobStatus.RUNNING);

    try {
      if (job.getType().name().equals(ApplicationConstants.LONG_RUNNING_JOB))
      {
        String resourceUrl = String.format("%s/%s", job.getRequestUrl(), job.getRequestParams()[0]);

        AsyncBatchJobService.execute(restTemplate, resourceUrl);

      } else
      {
        String resourceUrl = String.format("%s/%s", job.getRequestUrl(), job.getRequestParams()[0]);
        System.out.println(resourceUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        jobRepo.updateJobStatus(job.getId(), JobStatus.SUCCESS);
      }
    }
    catch(Exception ex) {
      System.out.println(ex.getMessage());
      jobRepo.updateJobStatus(job.getId(), JobStatus.FAILURE);
    }
  }
}
