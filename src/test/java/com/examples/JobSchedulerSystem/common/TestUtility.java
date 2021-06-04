package com.examples.JobSchedulerSystem.common;

import com.examples.JobSchedulerSystem.enums.JobExecutionType;
import com.examples.JobSchedulerSystem.enums.JobStatus;
import com.examples.JobSchedulerSystem.enums.JobType;
import com.examples.JobSchedulerSystem.model.Job;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

public class TestUtility
{
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static Job getImmediateTestJob() {
    return Job.builder()
        .id(1)
        .name("Test Job")
        .status(JobStatus.SUCCESS)
        .type(JobType.SHORT_LIVED)
        .executionType(JobExecutionType.IMMEDIATE)
        .requestParams(new String[] {"test"})
        .build();
  }

  public static Job getScheduledTestJob() {
    return Job.builder()
        .id(1)
        .name("Test Job")
        .status(JobStatus.SUCCESS)
        .type(JobType.SHORT_LIVED)
        .executionType(JobExecutionType.SCHEDULED)
        .requestParams(new String[] {"test"})
        .scheduleInterval("5000")
        .build();
  }

  public static Job getResponseAsObject(ResultActions resultActions) throws UnsupportedEncodingException, JsonProcessingException
  {
    MvcResult result = resultActions.andReturn();
    String contentAsString = result.getResponse().getContentAsString();

    Job response = objectMapper.readValue(contentAsString, Job.class);

    return response;
  }

  public static String getRequestAsJson(Object requestBody) throws JsonProcessingException
  {
    ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
    String requestJson=ow.writeValueAsString(requestBody);

    return requestJson;
  }
}
