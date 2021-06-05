package com.examples.JobSchedulerSystem.repository;

import com.examples.JobSchedulerSystem.enums.JobStatus;
import com.examples.JobSchedulerSystem.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer>
{
  @Transactional
  @Modifying
  @Query("update Job j set j.status = :status where j.id = :id")
  public void updateJobStatus(@Param("id") Integer id, @Param("status")
      JobStatus status);
}
