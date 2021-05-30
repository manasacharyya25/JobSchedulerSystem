package com.examples.JobSchedulerSystem.controller;

import com.examples.JobSchedulerSystem.model.Job;
import com.examples.JobSchedulerSystem.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/job")
public class JobController
{
  @Autowired
  private JobService jobService;

  @GetMapping
  public List<Job> getJobs() {
    return jobService.getJobs();
  }

  @GetMapping("/{id}")
  public Optional<Job> getJobById(@PathVariable("id") Long id) {
    return jobService.getJobById(id);
  }

  @PostMapping
  public Long scheduleJob(@RequestBody Job job) {
    return jobService.scheduleJob(job);
  }
}
