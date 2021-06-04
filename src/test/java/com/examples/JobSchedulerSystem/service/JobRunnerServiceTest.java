package com.examples.JobSchedulerSystem.service;

import com.examples.JobSchedulerSystem.common.TestUtility;
import com.examples.JobSchedulerSystem.enums.JobStatus;
import com.examples.JobSchedulerSystem.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class JobRunnerServiceTest
{
  @InjectMocks
  private JobRunnerService jobRunnerService;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private RestTemplate restTemplate;

  @Test
  void testShortLivedJobRunSuccess() throws Exception {
    Mockito
        .doReturn(ResponseEntity.ok("Success"))
        .when(restTemplate)
        .getForEntity(Mockito.anyString(), Mockito.any());

    jobRunnerService.runJob(TestUtility.getImmediateTestJob());
    verify(jobRepository, times(1)).updateJobStatus(1, JobStatus.RUNNING);
    verify(jobRepository, times(1)).updateJobStatus(1, JobStatus.SUCCESS);
  }

  @Test
  void testShortLivedJobRunFailure() throws Exception {
    Mockito
        .doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
        .when(restTemplate)
        .getForEntity(Mockito.anyString(), Mockito.any());

    jobRunnerService.runJob(TestUtility.getImmediateTestJob());
    verify(jobRepository, times(1)).updateJobStatus(1, JobStatus.RUNNING);
    verify(jobRepository, times(1)).updateJobStatus(1, JobStatus.FAILURE);
  }
}
