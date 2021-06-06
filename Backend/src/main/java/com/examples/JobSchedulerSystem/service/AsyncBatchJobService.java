package com.examples.JobSchedulerSystem.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

public class AsyncBatchJobService
{
  @Async
  public static void execute(RestTemplate restTemplate, String resourceUrl)
  {
    restTemplate.exchange(resourceUrl, HttpMethod.GET, HttpEntity.EMPTY, String.class);
  }
}
