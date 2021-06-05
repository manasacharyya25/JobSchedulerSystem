package com.examples.JobSchedulerSystem.controller;

import com.examples.JobSchedulerSystem.common.TestUtility;
import com.examples.JobSchedulerSystem.model.Job;
import com.examples.JobSchedulerSystem.service.JobSchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class JobSchedulerControllerTest
{
  @Autowired
  WebApplicationContext wac;

  @MockBean
  private JobSchedulerService jobSchedulerService;

  private MockMvc mvc;

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void testGetAllJobs() throws Exception {
    Mockito.doReturn(new ArrayList()).when(jobSchedulerService).getJobs();
    mvc.perform(get("/scheduler")).andExpect(status().isOk());
  }

  @Test
  void testGetJobById() throws Exception {
    Mockito
        .doReturn(TestUtility.getImmediateTestJob())
        .when(jobSchedulerService)
        .getJobById(1);

    ResultActions resultActions = mvc
                                    .perform(get("/scheduler/1"))
                                    .andExpect(status().isOk());

    Job response = TestUtility.getResponseAsObject(resultActions);
    assertEquals(response.getId(), 1);
  }

  @Test
  void testAddJob() throws Exception {
    Job requestPayload = TestUtility.getImmediateTestJob();

    Mockito
        .doReturn(1)
        .when(jobSchedulerService)
        .addJob(requestPayload);

    String requestJson = TestUtility.getRequestAsJson(requestPayload);

    ResultActions resultActions = mvc
                                    .perform(post("/scheduler")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(requestJson))
                                    .andExpect(status().isOk())
                                    .andExpect(content().string("1"));

  }
}

