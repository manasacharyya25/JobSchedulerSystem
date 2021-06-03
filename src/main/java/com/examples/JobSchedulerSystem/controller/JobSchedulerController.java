package com.examples.JobSchedulerSystem.controller;

import com.examples.JobSchedulerSystem.model.Job;
import com.examples.JobSchedulerSystem.service.JobSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduler")
public class JobSchedulerController
{
  @Autowired
  private JobSchedulerService jobSchedulerService;

  @GetMapping
  public List<Job> getAlJobs() {
    return jobSchedulerService.getJobs();
  }

  @GetMapping("/{id}")
  public Job getJobById(@PathVariable("id") Integer id) {
    return jobSchedulerService.getJobById(id);
  }

  @PostMapping
  public Integer addJob(@RequestBody Job job) {
    return jobSchedulerService.addJob(job);
  }
}
