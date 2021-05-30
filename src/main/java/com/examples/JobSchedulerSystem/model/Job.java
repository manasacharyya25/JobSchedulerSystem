package com.examples.JobSchedulerSystem.model;

import com.examples.JobSchedulerSystem.enums.JobExecutionType;
import com.examples.JobSchedulerSystem.enums.JobPriority;
import com.examples.JobSchedulerSystem.enums.JobStatus;
import com.examples.JobSchedulerSystem.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Job
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String requestUrl;
  private String[] requestParams;
  private JobType type;
  private JobExecutionType executionType;
  private JobPriority priority;
  private JobStatus status;
  private String scheduleInterval;
}
