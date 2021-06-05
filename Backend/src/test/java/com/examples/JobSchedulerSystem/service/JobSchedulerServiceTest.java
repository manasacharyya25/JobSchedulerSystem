package com.examples.JobSchedulerSystem.service;

import com.examples.JobSchedulerSystem.common.TestUtility;
import com.examples.JobSchedulerSystem.model.Job;
import com.examples.JobSchedulerSystem.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JobSchedulerServiceTest
{
  @InjectMocks
  private JobSchedulerService jobSchedulerService;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private KafkaTemplate<String, Object> kafkaTemplate;

  @Mock
  ListenableFuture<SendResult<String, Object>> future;

  @Mock
  private ThreadPoolTaskScheduler taskScheduler;

  @Mock
  private JobRunnerService jobRunnerService;

  @BeforeEach
  void setUp() {
    Mockito.doReturn(future).when(kafkaTemplate).send(Mockito.anyString(), Mockito.any());
  }

  @Test
  void testGetJobs() {
    List<Job> expectedResponse = new ArrayList<Job>();
    expectedResponse.add(TestUtility.getImmediateTestJob());

    Mockito
        .doReturn(expectedResponse)
        .when(jobRepository)
        .findAll();

    List<Job> actualResponse = jobSchedulerService.getJobs();

    assertIterableEquals(expectedResponse, actualResponse);
  }

  @Test
  void testGetJobByIdReturnJobWhenJobExists() {
    Job testJob = TestUtility.getImmediateTestJob();
    Optional<Job> expectedResponse = Optional.of(testJob);

    Mockito
        .doReturn(expectedResponse)
        .when(jobRepository)
        .findById(Mockito.any());

    Job actualResponse = jobSchedulerService.getJobById(Mockito.any());

    assertEquals(testJob.getId(), actualResponse.getId());
  }

  @Test
  void testGetJobByIdReturnsNullWhenJobDoesNotExist() {
    Mockito
        .doReturn(Optional.empty())
        .when(jobRepository)
        .findById(Mockito.any());

    Job actualResponse = jobSchedulerService.getJobById(Mockito.any());

    assertNull(actualResponse);
  }

  @Test
  void testAddJobQueuesImmediateJobExecution() {
    Mockito
        .doReturn(TestUtility.getImmediateTestJob())
        .when(jobRepository)
        .saveAndFlush(TestUtility.getImmediateTestJob());

    jobSchedulerService.addJob(TestUtility.getImmediateTestJob());

    Mockito
        .verify(kafkaTemplate, Mockito.times(1))
        .send(Mockito.any(), Mockito.any());
  }

  @Test
  void testAddJobSchedulesJobExecution() {
    Job scheduledJob = TestUtility.getScheduledTestJob();

    Mockito
        .doReturn(scheduledJob)
        .when(jobRepository)
        .saveAndFlush(scheduledJob);

    jobSchedulerService.addJob(scheduledJob);

    Mockito
        .verify(taskScheduler, Mockito.times(1))
        .scheduleAtFixedRate(Mockito.any(), Mockito.anyLong());
  }

}
